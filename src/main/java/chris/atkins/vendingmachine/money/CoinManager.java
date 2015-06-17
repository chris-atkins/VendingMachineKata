package chris.atkins.vendingmachine.money;

import chris.atkins.vendingmachine.items.Item;


public class CoinManager {

	private final UserBalance userBalance;
	private final CoinBank coinBank;
	private final CoinReturn coinReturn;
	private final CoinTypeIdentifier coinIdentifier;

	public CoinManager(final UserBalance userBalance, final CoinReturn coinReturn) {
		this.userBalance = userBalance;
		this.coinReturn = coinReturn;
		this.coinBank = new CoinBank();
		this.coinIdentifier = new CoinTypeIdentifier();
	}

	public void processPurchase(final Item item) {
		this.userBalance.pay(item.price());
		this.coinBank.returnChange(this.userBalance.currentBalance(), this.coinReturn);
		this.userBalance.reset();
	}

	public void coinInserted(final InsertedCoin insertedCoin) {
		final Coin coin = this.coinIdentifier.identify(insertedCoin);
		if (coin == Coin.INVALID_COIN) {
			this.coinReturn.returnCoin(insertedCoin);
			return;
		}

		this.userBalance.add(coin.value());
	}

	public void returnUsersBalance() {
		this.coinBank.returnChange(this.userBalance.currentBalance(), this.coinReturn);
		this.userBalance.reset();
	}

	public boolean userDoesNotHaveEnoughMoneyToPurchase(final Item item) {
		return this.userBalance.currentBalance() < item.price();
	}
}
