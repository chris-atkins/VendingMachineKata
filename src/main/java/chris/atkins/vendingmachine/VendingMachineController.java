package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.StartingInventory.STARTING_DIME_INVENTORY;
import static chris.atkins.vendingmachine.StartingInventory.STARTING_NICKEL_INVENTORY;
import static chris.atkins.vendingmachine.StartingInventory.STARTING_QUARTER_INVENTORY;
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
import chris.atkins.vendingmachine.money.UserBalance;


public class VendingMachineController {

	private final ItemDispensor itemDispensor;
	private final DisplayManager display;
	final UserBalance userBalance;
	final ItemManager inventory;
	private final CoinManager moneyHandler;

	public VendingMachineController(final ItemDispensor itemDispensor, final Display display, final CoinReturn coinReturn) {
		this.itemDispensor = itemDispensor;
		this.userBalance = new UserBalance();
		this.display = new DisplayManager(display);
		this.inventory = new ItemManager();
		this.moneyHandler = new CoinManager(this.userBalance, coinReturn, STARTING_QUARTER_INVENTORY, STARTING_DIME_INVENTORY,
				STARTING_NICKEL_INVENTORY);
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

		this.inventory.dispenseItemTo(item, this.itemDispensor);
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
