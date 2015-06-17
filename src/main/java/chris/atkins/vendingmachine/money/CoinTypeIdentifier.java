package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;


public class CoinTypeIdentifier {

	private static Coin[] validCoins = new Coin[] { QUARTER, DIME, NICKEL };

	public Coin identify(final int sizeInMM, final int weightInMg) {
		for (final Coin coin : validCoins) {
			if (coin.matchesSpecs(sizeInMM, weightInMg)) {
				return coin;
			}
		}
		return Coin.INVALID_COIN;
	}
}
