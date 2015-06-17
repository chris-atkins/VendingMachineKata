package chris.atkins.vendingmachine.money;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

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

	@Test
	public void purchaseItemsDelegatesGivingChangeWithCorrectAmount() throws Exception {
		injectMockCoinBank();
		this.coinManager.addToUserBalance(0.85);
		this.coinManager.purchaseItemAndReturnChange(Item.CHIPS); // chips cost .50
		verify(this.coinBank).returnChange(0.35, this.coinReturn);
	}

	@Test
	public void purchaseItemsResetsUserBalance() throws Exception {
		this.coinManager.addToUserBalance(0.85);
		this.coinManager.purchaseItemAndReturnChange(Item.CANDY);
		assertThat(this.coinManager.currentUserBalance(), equalTo(0.0));
	}

	private void injectMockCoinBank() throws Exception {
		injectIntoClassWithObjectForFieldName(this.coinManager, this.coinBank, "coinBank");
	}

	private void injectIntoClassWithObjectForFieldName(final Object target, final Object value, final String fieldName) throws Exception {
		final Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(target, value);
	}
}
