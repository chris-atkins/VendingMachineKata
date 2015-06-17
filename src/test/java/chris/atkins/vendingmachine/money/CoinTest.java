package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class CoinTest {

	@Test
	public void quarterHasCorrectValue() throws Exception {
		assertThat(QUARTER.value(), equalTo(0.25));
	}

	@Test
	public void quarterSize() throws Exception {
		assertThat(QUARTER.sizeInMM(), equalTo(5));
	}

	@Test
	public void quarterWeight() throws Exception {
		assertThat(QUARTER.weightInMg(), equalTo(3));
	}

	@Test
	public void dimeHasCorrectValue() throws Exception {
		assertThat(DIME.value(), equalTo(0.1));
	}

	@Test
	public void dimeSize() throws Exception {
		assertThat(DIME.sizeInMM(), equalTo(3));
	}

	@Test
	public void dimeWeight() throws Exception {
		assertThat(DIME.weightInMg(), equalTo(1));
	}

	@Test
	public void nickelHasCorrectValue() throws Exception {
		assertThat(NICKEL.value(), equalTo(0.05));
	}

	@Test
	public void nickelSize() throws Exception {
		assertThat(NICKEL.sizeInMM(), equalTo(4));
	}

	@Test
	public void nickelWeight() throws Exception {
		assertThat(NICKEL.weightInMg(), equalTo(3));
	}
}
