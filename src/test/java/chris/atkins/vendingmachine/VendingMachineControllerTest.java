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

		@Test
		public void whenColaIsSelectedItIsDispensedWithExactChange() throws Exception {
			this.vendingMachine.userBank.add(1.00);
			this.vendingMachine.colaSelected();
			verify(this.dispensor).dispenseItem(Item.COLA);
		}

		@Test
		public void whenColaIsDispensedTheDisplayReadsThankYou() throws Exception {
			this.vendingMachine.userBank.add(1.00);
			this.vendingMachine.colaSelected();
			verify(this.display).update("THANK YOU");
		}

		@Test
		public void doesNotDispenseColaIfNotEnoughMoneyExists() throws Exception {
			this.vendingMachine.userBank.add(0.99);
			this.vendingMachine.colaSelected();
			verifyZeroInteractions(this.dispensor);
		}

		@Test
		public void displaysPriceOfColaIfColaIsSelectedWithNotEnoughMoney() throws Exception {
			this.vendingMachine.colaSelected();
			verify(this.display).update("PRICE $1.00");
		}

		@Test
		public void userPaysForColaFromUserBank() throws Exception {
			this.vendingMachine.userBank.add(1.00);
			this.vendingMachine.colaSelected();
			assertThat(this.vendingMachine.userBank.currentBalance(), equalTo(0.0));
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
			this.vendingMachine.insertCoin(QUARTER.sizeInMM(), QUARTER.weightInMg());
			assertThat(this.vendingMachine.userBank.currentBalance(), equalTo(0.25));
		}

		@Test
		public void dimeInserted() throws Exception {
			this.vendingMachine.insertCoin(DIME.sizeInMM(), DIME.weightInMg());
			assertThat(this.vendingMachine.userBank.currentBalance(), equalTo(0.1));
		}

		@Test
		public void nickelInserted() throws Exception {
			this.vendingMachine.insertCoin(NICKEL.sizeInMM(), NICKEL.weightInMg());
			assertThat(this.vendingMachine.userBank.currentBalance(), equalTo(0.05));
		}

		@Test
		public void displayUpdatedToShowBalance() throws Exception {
			this.vendingMachine.insertCoin(QUARTER.sizeInMM(), QUARTER.weightInMg());
			verify(this.display).update("BALANCE: $0.25");
		}
	}

}