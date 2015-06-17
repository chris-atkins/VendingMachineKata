package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.items.Item.COLA;
import chris.atkins.vendingmachine.display.Display;
import chris.atkins.vendingmachine.money.UserBank;


public class VendingMachineController {

	private final ProductDispensor productDispensor;
	private final Display display;
	final UserBank userBank;

	public VendingMachineController(final ProductDispensor productDispensor, final Display display) {
		this.productDispensor = productDispensor;
		this.display = display;
		this.userBank = new UserBank();
	}

	public void colaSelected() {
		if (this.userBank.currentBalance() < 1.00) {
			this.display.update("PRICE $1.00");
			return;
		}

		this.productDispensor.dispenseItem(COLA);
		this.display.update("THANK YOU");
	}

	public void insertCoin(final int sizeInMM, final int weightInMg) {
		this.userBank.add(0.25);
	}
}
