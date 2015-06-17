package chris.atkins.vendingmachine;

import chris.atkins.vendingmachine.money.Coin;
import chris.atkins.vendingmachine.money.InsertedCoin;


public interface CoinReturn {

	void returnCoin(final Coin coin);

	void returnCoin(final InsertedCoin insertedCoin);
}
