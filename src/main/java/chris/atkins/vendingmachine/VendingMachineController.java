package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.items.Item.CANDY;
import static chris.atkins.vendingmachine.items.Item.CHIPS;
import static chris.atkins.vendingmachine.items.Item.COLA;
import chris.atkins.vendingmachine.display.Display;
import chris.atkins.vendingmachine.display.DisplayManager;
import chris.atkins.vendingmachine.items.Item;
import chris.atkins.vendingmachine.items.ItemDispensor;
import chris.atkins.vendingmachine.items.ItemManager;
import chris.atkins.vendingmachine.money.CoinManager;
import chris.atkins.vendingmachine.money.CoinReturn;
import chris.atkins.vendingmachine.money.InsertedCoin;


public final class VendingMachineController {

	private final DisplayManager display;
	final ItemManager inventory;
	final CoinManager moneyHandler;

	public VendingMachineController(final ItemDispensor itemDispensor, final Display display, final CoinReturn coinReturn) {
		this.display = new DisplayManager(display);
		this.inventory = new ItemManager(itemDispensor);
		this.moneyHandler = new CoinManager(coinReturn);
		initializeDisplay();
	}

	private void initializeDisplay() {
		displayBalance();
	}

	public void colaSelected() {
		itemSelected(COLA);
	}

	public void candySelected() {
		itemSelected(CANDY);
	}

	public void chipsSelected() {
		itemSelected(CHIPS);
	}

	private void itemSelected(final Item item) {
		if (this.inventory.isOutOfStockFor(item)) {
			this.display.outOfStock();
			return;
		}
		if (this.moneyHandler.userDoesNotHaveEnoughMoneyToPurchase(item)) {
			this.display.notifyPrice(item.price());
			return;
		}

		this.inventory.dispenseItem(item);
		this.moneyHandler.processPurchase(item);
		this.display.thanksForThePurchase();
	}

	public void coinInserted(final InsertedCoin insertedCoin) {
		this.moneyHandler.coinInserted(insertedCoin);
		displayBalance();
	}

	public void returnCoinBalance() {
		this.moneyHandler.returnCoins();
		displayBalance();
	}

	public void displayBalance() {
		this.display.updateBalanceStatus(this.moneyHandler.currentUserBalance(), this.moneyHandler.hasChangeForAQuarter());
	}
}
