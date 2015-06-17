package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.items.Item.CANDY;
import static chris.atkins.vendingmachine.items.Item.CHIPS;
import static chris.atkins.vendingmachine.items.Item.COLA;
import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import chris.atkins.vendingmachine.display.Display;
import chris.atkins.vendingmachine.items.Item;
import chris.atkins.vendingmachine.money.Coin;
import chris.atkins.vendingmachine.money.InsertedCoin;


@RunWith(Enclosed.class)
public class VendingMachineControllerTest {

	@RunWith(MockitoJUnitRunner.class)
	public static class VendingMachineInitializationTest {

		@InjectMocks
		private VendingMachineController vendingMachine;

		@Mock
		private Display display;

		@Test
		public void insertCoinsDisplayedOnInitialization() throws Exception {
			verify(this.display).update("INSERT COIN");
		}
	}

	@RunWith(MockitoJUnitRunner.class)
	public static class DisplayPollingTest {

		@InjectMocks
		private VendingMachineController vendingMachine;

		@Mock
		private Display display;

		@Test
		public void insertCoinsDisplayedWhenNoBalanceExists() throws Exception {
			this.vendingMachine.userBalance.reset();
			this.vendingMachine.updateBalanceToDisplay();
			verify(this.display, Mockito.atLeastOnce()).update("INSERT COIN");
		}

		@Test
		public void balanceDisplayedWhenBalanceExists() throws Exception {
			this.vendingMachine.userBalance.add(1.25);
			this.vendingMachine.updateBalanceToDisplay();
			verify(this.display, Mockito.atLeastOnce()).update("BALANCE: $1.25");
		}

	}

	@RunWith(MockitoJUnitRunner.class)
	public static class VendingMachineColaSelectedTest {

		@InjectMocks
		private VendingMachineController vendingMachine;

		@Mock
		private ProductDispensor dispensor;

		@Mock
		private Display display;

		@Mock
		private CoinReturn coinReturn;

		@Test
		public void whenColaIsSelectedItIsDispensedWithExactChange() throws Exception {
			this.vendingMachine.userBalance.add(1.00);
			this.vendingMachine.colaSelected();
			verify(this.dispensor).dispenseItem(Item.COLA);
		}

		@Test
		public void whenColaIsDispensedTheDisplayReadsThankYou() throws Exception {
			this.vendingMachine.userBalance.add(1.00);
			this.vendingMachine.colaSelected();
			verify(this.display).update("THANK YOU");
		}

		@Test
		public void doesNotDispenseColaIfNotEnoughMoneyExists() throws Exception {
			this.vendingMachine.userBalance.add(0.99);
			this.vendingMachine.colaSelected();
			verifyZeroInteractions(this.dispensor);
		}

		@Test
		public void displaysPriceOfColaIfColaIsSelectedWithNotEnoughMoney() throws Exception {
			this.vendingMachine.userBalance.reset();
			this.vendingMachine.colaSelected();
			verify(this.display).update("PRICE $1.00");
		}

		@Test
		public void userPaysForColaFromUserBalance() throws Exception {
			this.vendingMachine.userBalance.add(1.00);
			this.vendingMachine.colaSelected();
			assertThat(this.vendingMachine.userBalance.currentBalance(), equalTo(0.0));
		}

		@Test
		public void displaysSoldOutIfNoInventoryExists() throws Exception {
			purchaseAllTheColas();
			this.vendingMachine.userBalance.add(1.00);
			this.vendingMachine.colaSelected();
			verify(this.display).update("SOLD OUT");
		}

		@Test
		public void displaysSoldOutEvenIfUserHasNotEnteredMoney() throws Exception {
			purchaseAllTheColas();
			this.vendingMachine.userBalance.reset();
			this.vendingMachine.colaSelected();
			verify(this.display).update("SOLD OUT");
		}

		@Test
		public void noItemIsDispensedIfSoldOut() throws Exception {
			purchaseAllTheColas();
			this.vendingMachine.userBalance.add(1.00);
			this.vendingMachine.colaSelected();
			verifyZeroInteractions(this.dispensor);
		}

		@Test
		public void sameBalanceIfSoldOut() throws Exception {
			purchaseAllTheColas();
			this.vendingMachine.userBalance.add(1.10);
			this.vendingMachine.colaSelected();
			assertThat(this.vendingMachine.userBalance.currentBalance(), equalTo(1.1));
		}

		private void purchaseAllTheColas() {
			this.vendingMachine.inventory.setInventory(COLA, 0);
		}
	}

	@RunWith(MockitoJUnitRunner.class)
	public static class VendingMachineCandySelectedTest {

		@InjectMocks
		private VendingMachineController vendingMachine;

		@Mock
		private ProductDispensor dispensor;

		@Mock
		private Display display;

		@Mock
		private CoinReturn coinReturn;

		@Test
		public void whenCandyIsSelectedItIsDispensedWithExactChange() throws Exception {
			this.vendingMachine.userBalance.add(0.65);
			this.vendingMachine.candySelected();
			verify(this.dispensor).dispenseItem(CANDY);
		}

		@Test
		public void whenCandyIsDispensedTheDisplayReadsThankYou() throws Exception {
			this.vendingMachine.userBalance.add(0.65);
			this.vendingMachine.candySelected();
			verify(this.display).update("THANK YOU");
		}

		@Test
		public void doesNotDispenseCandyIfNotEnoughMoneyExists() throws Exception {
			this.vendingMachine.userBalance.add(0.64);
			this.vendingMachine.candySelected();
			verifyZeroInteractions(this.dispensor);
		}

		@Test
		public void displaysPriceOfCandyIfCandyIsSelectedWithNotEnoughMoney() throws Exception {
			this.vendingMachine.userBalance.reset();
			this.vendingMachine.candySelected();
			verify(this.display).update("PRICE $0.65");
		}

		@Test
		public void userPaysForCandyFromUserBalance() throws Exception {
			this.vendingMachine.userBalance.add(0.65);
			this.vendingMachine.candySelected();
			assertThat(this.vendingMachine.userBalance.currentBalance(), equalTo(0.0));
		}
	}

	@RunWith(MockitoJUnitRunner.class)
	public static class VendingMachineChipsSelectedTest {

		@InjectMocks
		private VendingMachineController vendingMachine;

		@Mock
		private ProductDispensor dispensor;

		@Mock
		private Display display;

		@Mock
		private CoinReturn coinReturn;

		@Test
		public void whenChipsIsSelectedItIsDispensedWithExactChange() throws Exception {
			this.vendingMachine.userBalance.add(0.5);
			this.vendingMachine.chipsSelected();
			verify(this.dispensor).dispenseItem(CHIPS);
		}

		@Test
		public void whenChipsIsDispensedTheDisplayReadsThankYou() throws Exception {
			this.vendingMachine.userBalance.add(0.5);
			this.vendingMachine.chipsSelected();
			verify(this.display).update("THANK YOU");
		}

		@Test
		public void doesNotDispenseChipsIfNotEnoughMoneyExists() throws Exception {
			this.vendingMachine.userBalance.add(0.49);
			this.vendingMachine.chipsSelected();
			verifyZeroInteractions(this.dispensor);
		}

		@Test
		public void displaysPriceOfChipsIfChipsIsSelectedWithNotEnoughMoney() throws Exception {
			this.vendingMachine.userBalance.reset();
			this.vendingMachine.chipsSelected();
			verify(this.display).update("PRICE $0.50");
		}

		@Test
		public void userPaysForChipsFromUserBalance() throws Exception {
			this.vendingMachine.userBalance.add(0.50);
			this.vendingMachine.chipsSelected();
			assertThat(this.vendingMachine.userBalance.currentBalance(), equalTo(0.0));
		}
	}

	@RunWith(MockitoJUnitRunner.class)
	public static class VendingMachineCoinInsertedTest {

		@InjectMocks
		private VendingMachineController vendingMachine;

		@Mock
		private Display display;

		@Mock
		private CoinReturn coinReturn;

		@Test
		public void quarterInserted() throws Exception {
			addCoin(QUARTER);
			assertThat(this.vendingMachine.userBalance.currentBalance(), equalTo(0.25));
		}

		@Test
		public void dimeInserted() throws Exception {
			addCoin(DIME);
			assertThat(this.vendingMachine.userBalance.currentBalance(), equalTo(0.1));
		}

		@Test
		public void nickelInserted() throws Exception {
			addCoin(NICKEL);
			assertThat(this.vendingMachine.userBalance.currentBalance(), equalTo(0.05));
		}

		@Test
		public void displayUpdatedToShowBalance() throws Exception {
			addCoin(QUARTER);
			verify(this.display).update("BALANCE: $0.25");
		}

		@Test
		public void displayUpdatedToShowBalanceWithMultipleCoins() throws Exception {
			addCoin(NICKEL);
			verify(this.display).update("BALANCE: $0.05");
			addCoin(DIME);
			verify(this.display).update("BALANCE: $0.15");
			addCoin(QUARTER);
			verify(this.display).update("BALANCE: $0.40");
		}

		@Test
		public void invalidCoinsRedispensed() throws Exception {
			final InsertedCoin penny = new InsertedCoin(5, 300);

			this.vendingMachine.coinInserted(penny);
			verify(this.coinReturn).returnCoin(penny);
		}

		private void addCoin(final Coin coin) {
			this.vendingMachine.coinInserted(new InsertedCoin(coin.sizeInMM(), coin.weightInMg()));
		}
	}

	@RunWith(MockitoJUnitRunner.class)
	public static class ChangeReturnedTest {

		@InjectMocks
		private VendingMachineController vendingMachine;

		@Mock
		private ProductDispensor dispensor;

		@Mock
		private CoinReturn coinReturn;

		@Mock
		private Display display;

		@Test
		public void returnsNickelAsChange() throws Exception {
			this.vendingMachine.userBalance.add(1.05);
			this.vendingMachine.colaSelected();
			verify(this.coinReturn).returnCoin(NICKEL);
		}

		@Test
		public void returnsDimeAsChange() throws Exception {
			this.vendingMachine.userBalance.add(1.10);
			this.vendingMachine.colaSelected();
			verify(this.coinReturn).returnCoin(DIME);
		}

		@Test
		public void returnsQuarterAsChange() throws Exception {
			this.vendingMachine.userBalance.add(1.25);
			this.vendingMachine.colaSelected();
			verify(this.coinReturn).returnCoin(QUARTER);
		}

		@Test
		public void returnsMultipleCoinsAsChange() throws Exception {
			this.vendingMachine.userBalance.add(1.40);
			this.vendingMachine.colaSelected();
			verify(this.coinReturn).returnCoin(QUARTER);
			verify(this.coinReturn).returnCoin(DIME);
			verify(this.coinReturn).returnCoin(NICKEL);
		}

		@Test
		public void userBalanceIsZeroAfterChangeIsGiven() throws Exception {
			this.vendingMachine.userBalance.add(1.40);
			this.vendingMachine.colaSelected();
			assertThat(this.vendingMachine.userBalance.currentBalance(), equalTo(0.0));
		}
	}

	@RunWith(MockitoJUnitRunner.class)
	public static class BalanceReturnRequestedTest {

		@InjectMocks
		private VendingMachineController vendingMachine;

		@Mock
		private CoinReturn coinReturn;

		@Mock
		private Display display;

		@Test
		public void returnsAllBalanceWhenRequested() throws Exception {
			this.vendingMachine.userBalance.add(0.4);
			this.vendingMachine.returnCoinBalance();
			verify(this.coinReturn).returnCoin(QUARTER);
			verify(this.coinReturn).returnCoin(DIME);
			verify(this.coinReturn).returnCoin(NICKEL);
		}

		@Test
		public void returnCoinsDisplaysInsertCoinWhenFinished() throws Exception {
			this.vendingMachine.userBalance.add(0.4);
			this.vendingMachine.returnCoinBalance();
			verify(this.display, times(2)).update("INSERT COIN");  // once for initialization, once for balance return
		}
	}
}