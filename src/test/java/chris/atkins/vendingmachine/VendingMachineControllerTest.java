package chris.atkins.vendingmachine;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import chris.atkins.vendingmachine.display.Display;
import chris.atkins.vendingmachine.items.Item;


@RunWith(MockitoJUnitRunner.class)
public class VendingMachineControllerTest {

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
		this.vendingMachine.colaSelected();
		verifyZeroInteractions(this.dispensor);
	}
}
