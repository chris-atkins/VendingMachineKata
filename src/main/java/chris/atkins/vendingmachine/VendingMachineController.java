package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.items.Item.COLA;
import chris.atkins.vendingmachine.display.Display;
import chris.atkins.vendingmachine.money.Coin;
import chris.atkins.vendingmachine.money.CoinBank;
import chris.atkins.vendingmachine.money.CoinTypeIdentifier;
import chris.atkins.vendingmachine.money.InsertedCoin;
import chris.atkins.vendingmachine.money.UserBalance;


public class VendingMachineController {

	private final ProductDispensor productDispensor;
	private final Display display;
	final UserBalance userBalance;
	private final CoinTypeIdentifier coinIdentifier;
	private final CoinReturn coinReturn;
	private final CoinBank coinBank;

	public VendingMachineController(final ProductDispensor productDispensor, final Display display, final CoinReturn coinReturn) {
		this.productDispensor = productDispensor;
		this.display = display;
		this.coinReturn = coinReturn;
		this.userBalance = new UserBalance();
		this.coinIdentifier = new CoinTypeIdentifier();
		this.coinBank = new CoinBank();
		initializeDisplay();
	}

	private void initializeDisplay() {
		this.display.update("INSERT COIN");
	}

	public void colaSelected() {
		if (this.userBalance.currentBalance() < 1.00) {
			this.display.update("PRICE $1.00");
			return;
		}

		this.productDispensor.dispenseItem(COLA);
		this.userBalance.pay(1.0);
		this.coinBank.returnChange(this.userBalance.currentBalance(), this.coinReturn);
		this.userBalance.reset();
		this.display.update("THANK YOU");
	}

	public void coinInserted(final InsertedCoin insertedCoin) {
		final Coin coin = this.coinIdentifier.identify(insertedCoin.sizeInMM, insertedCoin.weightInMg);
		if (coin == Coin.INVALID_COIN) {
			this.coinReturn.returnCoin(insertedCoin);
			return;
		}

		this.userBalance.add(coin.value());
		this.display.update(String.format("BALANCE: $%1.2f", this.userBalance.currentBalance()));
	}

	public void updateStatusToDisplay() {
		if (this.userBalance.currentBalance() == 0.0) {
			this.display.update("INSERT COIN");
		} else {
			this.display.update(String.format("BALANCE: $%1.2f", this.userBalance.currentBalance()));
		}
	}
}
