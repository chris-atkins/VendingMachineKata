package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.QUARTER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class CoinAcceptorTest {

	private CoinAcceptor coinAcceptor = new CoinAcceptor();

	@Test
	public void acceptsQuarters() throws Exception {
		assertThat(this.coinAcceptor.determineCoinType(QUARTER.sizeInMM(), QUARTER.weightInMg()), equalTo(QUARTER));
	}
}
