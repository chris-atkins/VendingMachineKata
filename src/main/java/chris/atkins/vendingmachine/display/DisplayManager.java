package chris.atkins.vendingmachine.display;

import chris.atkins.vendingmachine.money.UserBalance;


public class DisplayManager {

	private final Display display;
	private final UserBalance userBalance;

	public DisplayManager(final Display display, final UserBalance userBalance) {
		this.display = display;
		this.userBalance = userBalance;
	}

	public void updateStatus() {
		if (this.userBalance.currentBalance() == 0.0) {
			this.display.update("INSERT COIN");
		} else {
			this.display.update(String.format("BALANCE: $%1.2f", this.userBalance.currentBalance()));
		}
	}

	public void notifyPrice(final double price) {
		this.display.update("PRICE $1.00");

	}

	public void thanksForThePurchase() {
		this.display.update("THANK YOU");
	}

}
