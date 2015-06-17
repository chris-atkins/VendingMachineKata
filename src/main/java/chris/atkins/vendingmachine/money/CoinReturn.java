package chris.atkins.vendingmachine.money;

public interface CoinReturn {

	void returnCoin(final Coin coin);

	void returnCoin(final InsertedCoin insertedCoin);
}
