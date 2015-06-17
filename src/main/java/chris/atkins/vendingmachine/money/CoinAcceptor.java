package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;


public class CoinAcceptor {

	private static Coin[] validCoins = new Coin[] { QUARTER, DIME, NICKEL };

	public Coin determineCoinType(final int sizeInMM, final int weightInMg) {
		for (final Coin coin : validCoins) {
			if (coin.sizeInMM() == sizeInMM && coin.weightInMg() == weightInMg) {
				return coin;
			}
		}
		return Coin.INVALID_COIN;
	}

}
