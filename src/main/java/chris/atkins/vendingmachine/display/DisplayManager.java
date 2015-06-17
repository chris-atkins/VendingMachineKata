package chris.atkins.vendingmachine.display;

import static java.lang.String.format;
import chris.atkins.vendingmachine.money.BalanceReader;


public class DisplayManager {

	private static final String THANKS = "THANK YOU";
	private static final String NO_BALANCE = "INSERT COIN";
	private static final String BALANCE = "BALANCE: $%1.2f";
	private static final String PRICE = "PRICE $%1.2f";

	private final Display display;
	private final BalanceReader balance;

	public DisplayManager(final Display display, final BalanceReader balance) {
		this.display = display;
		this.balance = balance;
	}

	public void notifyPrice(final double price) {
		this.display.update(format(PRICE, price));
	}

	public void thanksForThePurchase() {
		this.display.update(THANKS);
	}

	public void updateBalanceStatus() {
		final String message = this.balance.isEmpty() ? NO_BALANCE : balance();
		this.display.update(message);
	}

	private String balance() {
		return format(BALANCE, this.balance.currentBalance());
	}
}
