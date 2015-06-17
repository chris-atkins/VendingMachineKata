package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.INVALID_COIN;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import chris.atkins.vendingmachine.items.Item;


@RunWith(MockitoJUnitRunner.class)
public class CoinManagerTest {

	@InjectMocks
	private CoinManager coinManager;

	@Mock
	private CoinReturn coinReturn;

	@Mock
	private UserBalance userBalance;

	@Mock
	private CoinBank coinBank;

	@Mock
	private CoinTypeIdentifier coinIdentifier;

	@Mock
	private InsertedCoin coin;

	private final double startingUserBalance = 0.85;

	@Before
	public void init() {
		this.coinManager.addToUserBalance(this.startingUserBalance);
	}

	@Test
	public void purchaseItemsDelegatesChangeWithCorrectAmount() throws Exception {
		injectMockCoinBank();
		this.coinManager.purchaseItemAndReturnChange(Item.CHIPS); // chips cost .50
		verify(this.coinBank).returnChange(0.35, this.coinReturn);
	}

	@Test
	public void purchaseItemsResetsUserBalance() throws Exception {
		this.coinManager.purchaseItemAndReturnChange(Item.CANDY);
		assertThat(this.coinManager.currentUserBalance(), equalTo(0.0));
	}

	@Test
	public void coinInsertedRejectsCoinsThatAreInvalid() throws Exception {
		setupInvalidCoinCase();
		this.coinManager.coinInserted(this.coin);
		verify(this.coinReturn).returnCoin(this.coin);
	}

	@Test
	public void rejectedCoinsDontChangeTheUserBalance() throws Exception {
		setupInvalidCoinCase();
		this.coinManager.coinInserted(this.coin);
		assertThat(this.coinManager.currentUserBalance(), equalTo(this.startingUserBalance));
	}

	@Test
	public void rejectedCoinsAreNotAddedToTheCoinBank() throws Exception {
		setupInvalidCoinCase();
		this.coinManager.coinInserted(this.coin);
		verifyZeroInteractions(this.coinBank);
	}

	@Test
	public void validCoinsIncreaseTheUserBalance() throws Exception {
		setupValidCoinCase();
		this.coinManager.coinInserted(this.coin);
		assertThat(this.coinManager.currentUserBalance(), equalTo(1.1));
	}

	@Test
	public void validCoinsAreAddedToTheCoinBank() throws Exception {
		setupValidCoinCase();
		this.coinManager.coinInserted(this.coin);
		verify(this.coinBank).add(QUARTER);
	}

	@Test
	public void returnCoinsReturnsTheWholeCurrentUserBalance() throws Exception {
		injectMockCoinBank();
		this.coinManager.returnCoins();
		verify(this.coinBank).returnChange(this.startingUserBalance, this.coinReturn);
	}

	@Test
	public void returnCoinsResetsUserBalanceToZero() throws Exception {
		injectMockCoinBank();
		this.coinManager.returnCoins();
		assertThat(this.coinManager.currentUserBalance(), equalTo(0.0));
	}

	@Test
	public void userHasEnoughMoneyWithExactChange() throws Exception {
		setUserBalance(Item.CANDY.price());
		final boolean result = this.coinManager.userDoesNotHaveEnoughMoneyToPurchase(Item.CANDY);
		assertThat(result, is(false));
	}

	@Test
	public void userHasEnoughMoneyIsTrueForExtraMoney() throws Exception {
		setUserBalance(Item.CHIPS.price() + .01);
		final boolean result = this.coinManager.userDoesNotHaveEnoughMoneyToPurchase(Item.CHIPS);
		assertThat(result, is(false));
	}

	@Test
	public void userHasEnoughMoneyIsFalseForLessMoney() throws Exception {
		setUserBalance(Item.COLA.price() - .01);
		final boolean result = this.coinManager.userDoesNotHaveEnoughMoneyToPurchase(Item.COLA);
		assertThat(result, is(true));
	}

	private void setupInvalidCoinCase() throws Exception {
		injectMockCoinIdentifier();
		injectMockCoinBank();
		when(this.coinIdentifier.identify(this.coin)).thenReturn(INVALID_COIN);
	}

	private void setupValidCoinCase() throws Exception {
		injectMockCoinIdentifier();
		injectMockCoinBank();
		when(this.coinIdentifier.identify(this.coin)).thenReturn(QUARTER);
	}

	private void setUserBalance(final double amount) {
		this.coinManager.resetUserBalance();
		this.coinManager.addToUserBalance(amount);
	}

	private void injectMockCoinBank() throws Exception {
		injectIntoClassWithObjectForFieldName(this.coinManager, this.coinBank, "coinBank");
	}

	private void injectMockCoinIdentifier() throws Exception {
		injectIntoClassWithObjectForFieldName(this.coinManager, this.coinIdentifier, "coinIdentifier");
	}

	private void injectIntoClassWithObjectForFieldName(final Object target, final Object value, final String fieldName) throws Exception {
		final Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(target, value);
	}
}
