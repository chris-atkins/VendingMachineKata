package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;


public class CoinBank {

	private double changeLeftToReturn;

	public void returnChange(final double changeToMake, final CoinReturn coinReturn) {
		this.changeLeftToReturn = changeToMake;

		makeChangeWith(QUARTER, coinReturn);
		makeChangeWith(DIME, coinReturn);
		makeChangeWith(NICKEL, coinReturn);
	}

	private void makeChangeWith(final Coin coinToUse, final CoinReturn coinReturn) {
		while (this.changeLeftToReturn >= coinToUse.value()) {
			coinReturn.returnCoin(coinToUse);
			this.changeLeftToReturn -= coinToUse.value();
		}
	}
}
