package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.QUARTER;


public class CoinAcceptor {

	public Coin determineCoinType(final int sizeInMM, final int weightInMg) {
		return QUARTER;
	}

}
