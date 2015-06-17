package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.StartingInventory.STARTING_DIME_INVENTORY;
import static chris.atkins.vendingmachine.StartingInventory.STARTING_NICKEL_INVENTORY;
import static chris.atkins.vendingmachine.StartingInventory.STARTING_QUARTER_INVENTORY;
import chris.atkins.vendingmachine.items.Item;


public class CoinManager {

	private final UserBalance userBalance;
	private final CoinBank coinBank;
	private final CoinReturn coinReturn;
	private final CoinTypeIdentifier coinIdentifier;

	public CoinManager(final CoinReturn coinReturn) {
		this.coinReturn = coinReturn;
		this.userBalance = new UserBalance();
		this.coinIdentifier = new CoinTypeIdentifier();
		this.coinBank = new CoinBank(STARTING_QUARTER_INVENTORY, STARTING_DIME_INVENTORY, STARTING_NICKEL_INVENTORY);
	}

	public void purchaseItemAndReturnChange(final Item item) {
		this.userBalance.pay(item.price());
		this.coinBank.returnChange(getCurrentBalance(), this.coinReturn);
		this.userBalance.reset();
	}

	public void coinInserted(final InsertedCoin insertedCoin) {
		final Coin coin = this.coinIdentifier.identify(insertedCoin);
		if (coin == Coin.INVALID_COIN) {
			this.coinReturn.returnCoin(insertedCoin);
			return;
		}

		this.coinBank.add(coin);
		addToUserBalance(coin.value());
	}

	public void returnCoins() {
		this.coinBank.returnChange(getCurrentBalance(), this.coinReturn);
		resetUserBalance();
	}

	private double getCurrentBalance() {
		return this.userBalance.currentBalance();
	}

	public boolean userDoesNotHaveEnoughMoneyToPurchase(final Item item) {
		return getCurrentBalance() < item.price();
	}

	public double currentUserBalance() {
		return getCurrentBalance();
	}

	public boolean hasChangeForAQuarter() {
		return this.coinBank.numberOf(Coin.NICKEL) >= 1 && this.coinBank.numberOf(Coin.DIME) >= 2;
	}

	public void addToUserBalance(final double amount) {
		this.userBalance.add(amount);
	}

	public void resetUserBalance() {
		this.userBalance.reset();
	}
}
