package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.items.Item.CANDY;
import static chris.atkins.vendingmachine.items.Item.CHIPS;
import static chris.atkins.vendingmachine.items.Item.COLA;
import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;
import static chris.atkins.vendingmachine.testutils.TestHelper.getFieldValueFromObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import chris.atkins.vendingmachine.display.Display;
import chris.atkins.vendingmachine.items.Item;
import chris.atkins.vendingmachine.items.ItemDispensor;
import chris.atkins.vendingmachine.items.ItemManager;
import chris.atkins.vendingmachine.money.Coin;
import chris.atkins.vendingmachine.money.CoinManager;
import chris.atkins.vendingmachine.money.CoinReturn;
import chris.atkins.vendingmachine.money.InsertedCoin;


@RunWith(MockitoJUnitRunner.class)
public class VendingMachineControllerTest {

	@InjectMocks
	private VendingMachineController vendingMachine;

	@Mock
	private ItemDispensor dispensor;

	@Mock
	private Display display;

	@Mock
	CoinReturn coinReturn;

	@Test
	public void insertCoinDisplayedOnInitialization() throws Exception {
		verify(this.display).update("INSERT COIN");
	}

	@Test
	public void insertCoinsDisplayedWhenNoBalanceExistsWithEnoughChange() throws Exception {
		getMoneyHandler().resetUserBalance();
		this.vendingMachine.displayBalance();
		verify(this.display, atLeastOnce()).update("INSERT COIN");
	}

	@Test
	public void balanceDisplayedWhenBalanceExists() throws Exception {
		getMoneyHandler().addToUserBalance(1.25);
		this.vendingMachine.displayBalance();
		verify(this.display).update("BALANCE $1.25");
	}

	@Test
	public void displaysCorrectTextWhenExactChangeIsRequired() throws Exception {
		addToUserBalance(80);
		this.vendingMachine.returnCoinsSelected();
		this.vendingMachine.displayBalance();
		verify(this.display, atLeastOnce()).update("EXACT CHANGE ONLY");
	}

	@Test
	public void whenColaIsSelectedItIsDispensedWithExactChange() throws Exception {
		addToUserBalance(1.00);
		this.vendingMachine.colaSelected();
		verify(this.dispensor).dispenseItem(Item.COLA);
	}

	@Test
	public void whenColaIsDispensedTheDisplayReadsThankYou() throws Exception {
		addToUserBalance(1.00);
		this.vendingMachine.colaSelected();
		verify(this.display).update("THANK YOU");
	}

	@Test
	public void doesNotDispenseColaIfNotEnoughMoneyExists() throws Exception {
		addToUserBalance(0.99);
		this.vendingMachine.colaSelected();
		verifyZeroInteractions(this.dispensor);
	}

	@Test
	public void displaysPriceOfColaIfColaIsSelectedWithNotEnoughMoney() throws Exception {
		resetUserBalance();
		this.vendingMachine.colaSelected();
		verify(this.display).update("PRICE $1.00");
	}

	@Test
	public void userPaysForColaFromUserBalance() throws Exception {
		addToUserBalance(1.00);
		this.vendingMachine.colaSelected();
		assertThat(getMoneyHandler().currentUserBalance(), equalTo(0.0));
	}

	@Test
	public void displaysSoldOutIfNoInventoryExists() throws Exception {
		purchaseAllTheColas();
		addToUserBalance(1.00);
		this.vendingMachine.colaSelected();
		verify(this.display).update("SOLD OUT");
	}

	@Test
	public void displaysSoldOutEvenIfUserHasNotEnteredMoney() throws Exception {
		purchaseAllTheColas();
		resetUserBalance();
		this.vendingMachine.colaSelected();
		verify(this.display).update("SOLD OUT");
	}

	@Test
	public void noItemIsDispensedIfSoldOut() throws Exception {
		purchaseAllTheColas();
		addToUserBalance(1.00);
		this.vendingMachine.colaSelected();
		verifyZeroInteractions(this.dispensor);
	}

	@Test
	public void sameBalanceIfSoldOut() throws Exception {
		purchaseAllTheColas();
		addToUserBalance(1.10);
		this.vendingMachine.colaSelected();
		assertThat(getMoneyHandler().currentUserBalance(), equalTo(1.1));
	}

	@Test
	public void whenCandyIsSelectedItIsDispensedWithExactChange() throws Exception {
		addToUserBalance(0.65);
		this.vendingMachine.candySelected();
		verify(this.dispensor).dispenseItem(CANDY);
	}

	@Test
	public void whenCandyIsDispensedTheDisplayReadsThankYou() throws Exception {
		addToUserBalance(0.65);
		this.vendingMachine.candySelected();
		verify(this.display).update("THANK YOU");
	}

	@Test
	public void doesNotDispenseCandyIfNotEnoughMoneyExists() throws Exception {
		addToUserBalance(0.64);
		this.vendingMachine.candySelected();
		verifyZeroInteractions(this.dispensor);
	}

	@Test
	public void displaysPriceOfCandyIfCandyIsSelectedWithNotEnoughMoney() throws Exception {
		resetUserBalance();
		this.vendingMachine.candySelected();
		verify(this.display).update("PRICE $0.65");
	}

	@Test
	public void userPaysForCandyFromUserBalance() throws Exception {
		addToUserBalance(0.65);
		this.vendingMachine.candySelected();
		assertThat(getMoneyHandler().currentUserBalance(), equalTo(0.0));
	}

	@Test
	public void whenChipsIsSelectedItIsDispensedWithExactChange() throws Exception {
		addToUserBalance(0.5);
		this.vendingMachine.chipsSelected();
		verify(this.dispensor).dispenseItem(CHIPS);
	}

	@Test
	public void whenChipsIsDispensedTheDisplayReadsThankYou() throws Exception {
		addToUserBalance(0.5);
		this.vendingMachine.chipsSelected();
		verify(this.display).update("THANK YOU");
	}

	@Test
	public void doesNotDispenseChipsIfNotEnoughMoneyExists() throws Exception {
		addToUserBalance(0.49);
		this.vendingMachine.chipsSelected();
		verifyZeroInteractions(this.dispensor);
	}

	@Test
	public void displaysPriceOfChipsIfChipsIsSelectedWithNotEnoughMoney() throws Exception {
		resetUserBalance();
		this.vendingMachine.chipsSelected();
		verify(this.display).update("PRICE $0.50");
	}

	@Test
	public void userPaysForChipsFromUserBalance() throws Exception {
		addToUserBalance(0.50);
		this.vendingMachine.chipsSelected();
		assertThat(getMoneyHandler().currentUserBalance(), equalTo(0.0));
	}

	@Test
	public void quarterInserted() throws Exception {
		addCoin(QUARTER);
		assertThat(getMoneyHandler().currentUserBalance(), equalTo(0.25));
	}

	@Test
	public void dimeInserted() throws Exception {
		addCoin(DIME);
		assertThat(getMoneyHandler().currentUserBalance(), equalTo(0.1));
	}

	@Test
	public void nickelInserted() throws Exception {
		addCoin(NICKEL);
		assertThat(getMoneyHandler().currentUserBalance(), equalTo(0.05));
	}

	@Test
	public void onCoinInsertDisplayIsUpdatedToShowBalance() throws Exception {
		addCoin(QUARTER);
		verify(this.display).update("BALANCE $0.25");
	}

	@Test
	public void displayUpdatedToShowBalanceWithMultipleCoins() throws Exception {
		addCoin(NICKEL);
		verify(this.display).update("BALANCE $0.05");
		addCoin(DIME);
		verify(this.display).update("BALANCE $0.15");
		addCoin(QUARTER);
		verify(this.display).update("BALANCE $0.40");
	}

	@Test
	public void invalidCoinsRedispensed() throws Exception {
		final InsertedCoin penny = new InsertedCoin(5, 300);

		this.vendingMachine.coinInserted(penny);
		verify(this.coinReturn).returnCoin(penny);
	}

	@Test
	public void returnsNickelAsChange() throws Exception {
		addToUserBalance(1.05);
		this.vendingMachine.colaSelected();
		verify(this.coinReturn).returnCoin(NICKEL);
	}

	@Test
	public void returnsDimeAsChange() throws Exception {
		addToUserBalance(1.10);
		this.vendingMachine.colaSelected();
		verify(this.coinReturn).returnCoin(DIME);
	}

	@Test
	public void returnsQuarterAsChange() throws Exception {
		addToUserBalance(1.25);
		this.vendingMachine.colaSelected();
		verify(this.coinReturn).returnCoin(QUARTER);
	}

	@Test
	public void returnsMultipleCoinsAsChange() throws Exception {
		addToUserBalance(1.40);
		this.vendingMachine.colaSelected();
		verify(this.coinReturn).returnCoin(QUARTER);
		verify(this.coinReturn).returnCoin(DIME);
		verify(this.coinReturn).returnCoin(NICKEL);
	}

	@Test
	public void userBalanceIsZeroAfterChangeIsGiven() throws Exception {
		addToUserBalance(1.40);
		this.vendingMachine.colaSelected();
		assertThat(getMoneyHandler().currentUserBalance(), equalTo(0.0));
	}

	@Test
	public void returnsAllBalanceWhenRequested() throws Exception {
		addToUserBalance(0.4);
		this.vendingMachine.returnCoinsSelected();
		verify(this.coinReturn).returnCoin(QUARTER);
		verify(this.coinReturn).returnCoin(DIME);
		verify(this.coinReturn).returnCoin(NICKEL);
	}

	@Test
	public void returnCoinsDisplaysStatusMessageWhenFinished() throws Exception {
		addToUserBalance(0.4);
		this.vendingMachine.returnCoinsSelected();
		verify(this.display, atLeastOnce()).update("EXACT CHANGE ONLY");
	}

	private void purchaseAllTheColas() throws Exception {
		getInventory().setInventory(COLA, 0);
	}

	private void addCoin(final Coin coin) {
		this.vendingMachine.coinInserted(new InsertedCoin(coin.sizeInMM(), coin.weightInMg()));
	}

	private void addToUserBalance(final double amount) throws Exception {
		getMoneyHandler().addToUserBalance(amount);
	}

	private void resetUserBalance() throws Exception {
		getMoneyHandler().resetUserBalance();
	}

	private CoinManager getMoneyHandler() throws Exception {
		return getFieldValueFromObject(this.vendingMachine, "moneyHandler", CoinManager.class);
	}

	private ItemManager getInventory() throws Exception {
		return getFieldValueFromObject(this.vendingMachine, "inventory", ItemManager.class);
	}
}