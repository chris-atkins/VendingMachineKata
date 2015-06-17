package chris.atkins.vendingmachine.display;

import static java.lang.String.format;
import chris.atkins.vendingmachine.money.UserBalance;


public class DisplayManager {

	private static final String THANKS = "THANK YOU";
	private static final String NO_BALANCE_MESSAGE = "INSERT COIN";
	private static final String BALNCE_MESSAGE = "BALANCE: $%1.2f";
	private static final String PRICE_MESSAGE = "PRICE $%1.2f";

	private final Display display;
	private final UserBalance userBalance;

	public DisplayManager(final Display display, final UserBalance userBalance) {
		this.display = display;
		this.userBalance = userBalance;
	}

	public void notifyPrice(final double price) {
		this.display.update(format(PRICE_MESSAGE, price));
	}

	public void thanksForThePurchase() {
		this.display.update(THANKS);
	}

	public void updateBalanceStatus() {
		if (this.userBalance.currentBalance() == 0.0) {
			this.display.update(NO_BALANCE_MESSAGE);
		} else {
			this.display.update(balance());
		}
	}

	private String balance() {
		return format(BALNCE_MESSAGE, this.userBalance.currentBalance());
	}
}
