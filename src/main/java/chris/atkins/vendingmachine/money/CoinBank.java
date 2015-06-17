package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;

import java.util.HashMap;
import java.util.Map;


public class CoinBank {

	private double changeLeftToReturn;
	private final Map<Coin, Integer> coins = new HashMap<>();

	CoinBank(final int startingQuarterInventory, final int startingDimeInventory, final int startingNickelInventory) {
		this.coins.put(QUARTER, startingQuarterInventory);
		this.coins.put(DIME, startingDimeInventory);
		this.coins.put(NICKEL, startingNickelInventory);
	}

	public void returnChange(final double changeToMake, final CoinReturn coinReturn) {
		this.changeLeftToReturn = changeToMake;
		makeChangeWith(QUARTER, coinReturn);
		makeChangeWith(DIME, coinReturn);
		makeChangeWith(NICKEL, coinReturn);
	}

	private void makeChangeWith(final Coin coinToUse, final CoinReturn coinReturn) {
		while (this.changeLeftToReturn >= coinToUse.value() && this.hasCoin(coinToUse)) {
			coinReturn.returnCoin(coinToUse);
			this.remove(coinToUse);
			this.changeLeftToReturn -= coinToUse.value();
		}
	}

	public int numberOf(final Coin coin) {
		return this.coins.get(coin);
	}

	public void add(final Coin coin) {
		this.coins.put(coin, this.coins.get(coin) + 1);
	}

	private void remove(final Coin coin) {
		this.coins.put(coin, this.coins.get(coin) - 1);
	}

	private boolean hasCoin(final Coin coin) {
		return numberOf(coin) > 0;
	}
}
