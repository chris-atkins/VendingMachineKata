package chris.atkins.vendingmachine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import chris.atkins.vendingmachine.items.Item;


@RunWith(MockitoJUnitRunner.class)
public class VendingMachineControllerTest {

	@InjectMocks
	private VendingMachineController vendingMachine;

	@Mock
	private ProductDispensor dispensor;

	@Test
	public void whenColaIsSelectedItIsDispensed() throws Exception {
		this.vendingMachine.colaSelected();
		Mockito.verify(this.dispensor).dispenseItem(Item.COLA);
	}
}
