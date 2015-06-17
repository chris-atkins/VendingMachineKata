package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.items.Item.COLA;


public class VendingMachineController {

	private final ProductDispensor productDispensor;

	public VendingMachineController(final ProductDispensor productDispensor) {
		this.productDispensor = productDispensor;
	}

	public void colaSelected() {
		this.productDispensor.dispenseItem(COLA);
	}

}
