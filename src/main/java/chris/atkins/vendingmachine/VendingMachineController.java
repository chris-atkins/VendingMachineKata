package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.items.Item.CANDY;
import static chris.atkins.vendingmachine.items.Item.CHIPS;
import static chris.atkins.vendingmachine.items.Item.COLA;
import chris.atkins.vendingmachine.display.Display;
import chris.atkins.vendingmachine.display.DisplayManager;
import chris.atkins.vendingmachine.items.Item;
import chris.atkins.vendingmachine.items.ItemDispensor;
import chris.atkins.vendingmachine.items.ItemManager;
import chris.atkins.vendingmachine.money.Coin;
import chris.atkins.vendingmachine.money.CoinBank;
import chris.atkins.vendingmachine.money.CoinTypeIdentifier;
import chris.atkins.vendingmachine.money.InsertedCoin;
import chris.atkins.vendingmachine.money.UserBalance;


public class VendingMachineController {

	private final ItemDispensor itemDispensor;
	private final DisplayManager display;
	private final CoinTypeIdentifier coinIdentifier;
	private final CoinReturn coinReturn;
	private final CoinBank coinBank;
	final UserBalance userBalance;
	final ItemManager inventory;

	public VendingMachineController(final ItemDispensor itemDispensor, final Display display, final CoinReturn coinReturn) {
		this.itemDispensor = itemDispensor;
		this.coinReturn = coinReturn;
		this.userBalance = new UserBalance();
		this.coinIdentifier = new CoinTypeIdentifier();
		this.coinBank = new CoinBank();
		this.display = new DisplayManager(display, this.userBalance);
		this.inventory = new ItemManager(2, 2, 2);
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

		if (this.userBalance.currentBalance() < item.price()) {
			this.display.notifyPrice(item.price());
			return;
		}

		this.inventory.dispenseItemTo(item, this.itemDispensor);

		this.userBalance.pay(item.price());
		this.coinBank.returnChange(this.userBalance.currentBalance(), this.coinReturn);
		this.userBalance.reset();

		this.display.thanksForThePurchase();
	}

	public void coinInserted(final InsertedCoin insertedCoin) {
		final Coin coin = this.coinIdentifier.identify(insertedCoin.sizeInMM, insertedCoin.weightInMg);
		if (coin == Coin.INVALID_COIN) {
			this.coinReturn.returnCoin(insertedCoin);
			return;
		}

		this.userBalance.add(coin.value());
		updateBalanceToDisplay();
	}

	public void updateBalanceToDisplay() {
		this.display.updateBalanceStatus();
	}

	public void returnCoinBalance() {
		this.coinBank.returnChange(this.userBalance.currentBalance(), this.coinReturn);
		this.userBalance.reset();
		updateBalanceToDisplay();
	}
}
