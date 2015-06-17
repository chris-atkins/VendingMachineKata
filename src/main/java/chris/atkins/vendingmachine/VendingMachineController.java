package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.items.Item.CANDY;
import static chris.atkins.vendingmachine.items.Item.CHIPS;
import static chris.atkins.vendingmachine.items.Item.COLA;
import chris.atkins.vendingmachine.display.Display;
import chris.atkins.vendingmachine.display.DisplayManager;
import chris.atkins.vendingmachine.items.Item;
import chris.atkins.vendingmachine.items.ItemDispensor;
import chris.atkins.vendingmachine.items.ItemManager;
import chris.atkins.vendingmachine.money.CoinBank;
import chris.atkins.vendingmachine.money.CoinManager;
import chris.atkins.vendingmachine.money.CoinReturn;
import chris.atkins.vendingmachine.money.InsertedCoin;
import chris.atkins.vendingmachine.money.UserBalance;


public class VendingMachineController {

	private final ItemDispensor itemDispensor;
	private final DisplayManager display;
	private final CoinBank coinBank;
	final UserBalance userBalance;
	final ItemManager inventory;
	private final CoinManager moneyHandler;

	public VendingMachineController(final ItemDispensor itemDispensor, final Display display, final CoinReturn coinReturn) {
		this.itemDispensor = itemDispensor;
		this.userBalance = new UserBalance();
		this.coinBank = new CoinBank();
		this.display = new DisplayManager(display, this.userBalance);
		this.inventory = new ItemManager(2, 2, 2);
		this.moneyHandler = new CoinManager(this.userBalance, this.coinBank, coinReturn);
		initializeDisplay();
	}

	private void initializeDisplay() {
		updateBalanceToDisplay();
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
		updateBalanceToDisplay();
	}

	public void returnCoinBalance() {
		this.moneyHandler.returnUsersBalance();
		updateBalanceToDisplay();
	}

	public void updateBalanceToDisplay() {
		this.display.updateBalanceStatus();
	}
}
