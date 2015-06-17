package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.items.Item.COLA;
import chris.atkins.vendingmachine.display.Display;
import chris.atkins.vendingmachine.display.DisplayManager;
import chris.atkins.vendingmachine.money.Coin;
import chris.atkins.vendingmachine.money.CoinBank;
import chris.atkins.vendingmachine.money.CoinTypeIdentifier;
import chris.atkins.vendingmachine.money.InsertedCoin;
import chris.atkins.vendingmachine.money.UserBalance;


public class VendingMachineController {

	private final ProductDispensor productDispensor;
	private final DisplayManager display;
	final UserBalance userBalance;
	private final CoinTypeIdentifier coinIdentifier;
	private final CoinReturn coinReturn;
	private final CoinBank coinBank;

	public VendingMachineController(final ProductDispensor productDispensor, final Display display, final CoinReturn coinReturn) {
		this.productDispensor = productDispensor;
		this.coinReturn = coinReturn;
		this.userBalance = new UserBalance();
		this.coinIdentifier = new CoinTypeIdentifier();
		this.coinBank = new CoinBank();
		this.display = new DisplayManager(display, this.userBalance);
		initializeDisplay();
	}

	private void initializeDisplay() {
		updateBalanceToDisplay();
	}

	public void colaSelected() {
		if (this.userBalance.currentBalance() < 1.00) {
			this.display.notifyPrice(1.0);
			return;
		}

		this.productDispensor.dispenseItem(COLA);
		this.userBalance.pay(1.0);
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
	}
}
