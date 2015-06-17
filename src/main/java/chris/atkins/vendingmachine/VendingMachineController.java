package chris.atkins.vendingmachine;

import static chris.atkins.vendingmachine.items.Item.COLA;
import chris.atkins.vendingmachine.display.Display;
import chris.atkins.vendingmachine.money.Coin;
import chris.atkins.vendingmachine.money.CoinBank;
import chris.atkins.vendingmachine.money.CoinTypeIdentifier;
import chris.atkins.vendingmachine.money.UserBank;


public class VendingMachineController {

	private final ProductDispensor productDispensor;
	private final Display display;
	final UserBank userBank;
	private final CoinTypeIdentifier coinIdentifier;
	private final CoinReturn coinReturn;
	private final CoinBank coinBank;

	public VendingMachineController(final ProductDispensor productDispensor, final Display display, final CoinReturn coinReturn) {
		this.productDispensor = productDispensor;
		this.display = display;
		this.coinReturn = coinReturn;
		this.userBank = new UserBank();
		this.coinIdentifier = new CoinTypeIdentifier();
		this.coinBank = new CoinBank();
	}

	public void colaSelected() {
		if (this.userBank.currentBalance() < 1.00) {
			this.display.update("PRICE $1.00");
			return;
		}

		this.productDispensor.dispenseItem(COLA);
		this.userBank.pay(1.0);
		this.coinBank.returnChange(this.userBank.currentBalance(), this.coinReturn);
		this.display.update("THANK YOU");
	}

	public void insertCoin(final int sizeInMM, final int weightInMg) {
		final Coin coin = this.coinIdentifier.identify(sizeInMM, weightInMg);
		this.userBank.add(coin.value());
		this.display.update(String.format("BALANCE: $%1.2f", this.userBank.currentBalance()));
	}
}
