package chris.atkins.vendingmachine.money;



public class CoinAcceptor {

	private static Coin[] validCoins = new Coin[] { Coin.QUARTER, Coin.DIME };

	public Coin determineCoinType(final int sizeInMM, final int weightInMg) {
		for (final Coin coin : validCoins) {
			if (coin.sizeInMM() == sizeInMM && coin.weightInMg() == weightInMg) {
				return coin;
			}
		}
		return null;
	}

}
