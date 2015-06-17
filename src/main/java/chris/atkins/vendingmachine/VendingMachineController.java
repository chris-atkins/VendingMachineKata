package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.items.Item.COLA;
import chris.atkins.vendingmachine.display.Display;


public class VendingMachineController {

	private final ProductDispensor productDispensor;
	private final Display display;

	public VendingMachineController(final ProductDispensor productDispensor, final Display display) {
		this.productDispensor = productDispensor;
		this.display = display;
	}

	public void colaSelected() {
		this.productDispensor.dispenseItem(COLA);
		this.display.update("THANK YOU");
	}

}
