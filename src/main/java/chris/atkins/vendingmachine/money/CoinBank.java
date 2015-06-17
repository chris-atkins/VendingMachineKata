package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import chris.atkins.vendingmachine.CoinReturn;


public class CoinBank {

	public void returnChange(final double currentBalance, final CoinReturn coinReturn) {
		double tempBalance = currentBalance;
		while (tempBalance >= DIME.value()) {
			coinReturn.returnCoin(DIME);
			tempBalance -= DIME.value();
		}

		while (tempBalance >= NICKEL.value()) {
			coinReturn.returnCoin(NICKEL);
			tempBalance -= NICKEL.value();
		}
	}
}
