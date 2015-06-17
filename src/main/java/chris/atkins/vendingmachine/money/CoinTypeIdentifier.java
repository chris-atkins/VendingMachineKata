package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;


public class CoinTypeIdentifier {

	private static Coin[] validCoins = new Coin[] { QUARTER, DIME, NICKEL };

	public Coin identify(final InsertedCoin insertedCoin) {
		for (final Coin coin : validCoins) {
			if (coin.matchesSpecs(insertedCoin.sizeInMM, insertedCoin.weightInMg)) {
				return coin;
			}
		}
		return Coin.INVALID_COIN;
	}
}
