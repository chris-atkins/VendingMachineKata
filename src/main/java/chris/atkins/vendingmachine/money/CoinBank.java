package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import chris.atkins.vendingmachine.CoinReturn;


public class CoinBank {

	public void returnChange(final double changeToMake, final CoinReturn coinReturn) {
		double changeLeftToReturn = changeToMake;
		while (changeLeftToReturn >= DIME.value()) {
			coinReturn.returnCoin(DIME);
			changeLeftToReturn -= DIME.value();
		}

		while (changeLeftToReturn >= NICKEL.value()) {
			coinReturn.returnCoin(NICKEL);
			changeLeftToReturn -= NICKEL.value();
		}
	}
}
