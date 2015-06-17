package chris.atkins.vendingmachine.display;

import static java.lang.String.format;


public class DisplayManager {

	private static final String THANKS = "THANK YOU";
	private static final String NO_BALANCE = "INSERT COIN";
	private static final String OUT_OF_STOCK = "SOLD OUT";
	private static final String BALANCE = "BALANCE: $%1.2f";
	private static final String PRICE = "PRICE $%1.2f";

	private final Display display;

	public DisplayManager(final Display display) {
		this.display = display;
	}

	public void notifyPrice(final double price) {
		this.display.update(format(PRICE, price));
	}

	public void thanksForThePurchase() {
		this.display.update(THANKS);
	}

	public void outOfStock() {
		this.display.update(OUT_OF_STOCK);
	}

	public void updateBalanceStatus(final double balance) {
		this.display.update(balanceMessage(balance));
	}

	private String balanceMessage(final double balance) {
		return balanceIsEmpty(balance) ? NO_BALANCE : format(BALANCE, balance);
	}

	private boolean balanceIsEmpty(final double balance) {
		return balance == 0.0;
	}
}
