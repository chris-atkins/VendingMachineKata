package chris.atkins.vendingmachine;

import static org.mockito.Mockito.verify;

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
	public void whenColaIsSelectedItIsDispensed() throws Exception {
		this.vendingMachine.colaSelected();
		verify(this.dispensor).dispenseItem(Item.COLA);
	}

	@Test
	public void whenColaIsDispensedTheDisplayReadsThankYou() throws Exception {
		this.vendingMachine.colaSelected();
		verify(this.display).update("THANK YOU");
	}
}
