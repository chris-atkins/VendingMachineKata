package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import chris.atkins.vendingmachine.display.Display;
import chris.atkins.vendingmachine.items.Item;
import chris.atkins.vendingmachine.money.Coin;


@RunWith(Enclosed.class)
public class VendingMachineControllerTest {

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
			this.vendingMachine.colaSelected();
			verify(this.display).update("PRICE $1.00");
		}

		@Test
		public void userPaysForColaFromUserBalance() throws Exception {
			this.vendingMachine.userBalance.add(1.00);
			this.vendingMachine.colaSelected();
			assertThat(this.vendingMachine.userBalance.currentBalance(), equalTo(0.0));
		}
	}

	@RunWith(MockitoJUnitRunner.class)
	public static class VendingMachineCoinInsertedTest {

		@InjectMocks
		private VendingMachineController vendingMachine;

		@Mock
		private Display display;

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

		private void addCoin(final Coin coin) {
			this.vendingMachine.insertCoin(coin.sizeInMM(), coin.weightInMg());
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
}