package chris.atkins.vendingmachine.money;

import chris.atkins.vendingmachine.items.Item;


public class CoinManager {

	private final UserBalance userBalance;
	private final CoinBank coinBank;
	private final CoinReturn coinReturn;

	public CoinManager(final UserBalance userBalance, final CoinBank coinBank, final CoinReturn coinReturn) {
		this.userBalance = userBalance;
		this.coinBank = coinBank;
		this.coinReturn = coinReturn;
	}

	public void processPurchase(final Item item) {
		this.userBalance.pay(item.price());
		this.coinBank.returnChange(this.userBalance.currentBalance(), this.coinReturn);
		this.userBalance.reset();
	}

}
