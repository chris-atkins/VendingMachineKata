package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class CoinTypeIdentifierTest {

	private final CoinTypeIdentifier coinIdentifier = new CoinTypeIdentifier();
	private final InsertedCoin quarterSpec = new InsertedCoin(QUARTER.sizeInMM(), QUARTER.weightInMg());
	private final InsertedCoin dimeSpec = new InsertedCoin(DIME.sizeInMM(), DIME.weightInMg());
	private final InsertedCoin nickelSpec = new InsertedCoin(NICKEL.sizeInMM(), NICKEL.weightInMg());
	private final InsertedCoin invalidSpec = new InsertedCoin(42, 23);

	@Test
	public void acceptsQuarters() throws Exception {
		assertThat(this.coinIdentifier.identify(this.quarterSpec), equalTo(QUARTER));
	}

	@Test
	public void acceptsDimes() throws Exception {
		assertThat(this.coinIdentifier.identify(this.dimeSpec), equalTo(DIME));
	}

	@Test
	public void acceptsNickels() throws Exception {
		assertThat(this.coinIdentifier.identify(this.nickelSpec), equalTo(NICKEL));
	}

	@Test
	public void unknownCoins() throws Exception {
		assertThat(this.coinIdentifier.identify(this.invalidSpec), equalTo(Coin.INVALID_COIN));
	}
}
