package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.INVALID_COIN;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
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
		assertThat(QUARTER.sizeInMM(), equalTo(24));
	}

	@Test
	public void quarterWeight() throws Exception {
		assertThat(QUARTER.weightInMg(), equalTo(567));
	}

	@Test
	public void dimeHasCorrectValue() throws Exception {
		assertThat(DIME.value(), equalTo(0.1));
	}

	@Test
	public void dimeSize() throws Exception {
		assertThat(DIME.sizeInMM(), equalTo(18));
	}

	@Test
	public void dimeWeight() throws Exception {
		assertThat(DIME.weightInMg(), equalTo(227));
	}

	@Test
	public void nickelHasCorrectValue() throws Exception {
		assertThat(NICKEL.value(), equalTo(0.05));
	}

	@Test
	public void nickelSize() throws Exception {
		assertThat(NICKEL.sizeInMM(), equalTo(21));
	}

	@Test
	public void nickelWeight() throws Exception {
		assertThat(NICKEL.weightInMg(), equalTo(500));
	}

	@Test
	public void invalidCoinHasNoValue() throws Exception {
		assertThat(INVALID_COIN.value(), equalTo(0.0));
	}

	@Test
	public void invalidCoinHasNullSize() throws Exception {
		assertThat(INVALID_COIN.sizeInMM(), nullValue());
	}

	@Test
	public void invalidCoinHasNullWeight() throws Exception {
		assertThat(INVALID_COIN.weightInMg(), nullValue());
	}

	@Test
	public void quarterMatchesSpecs() throws Exception {
		assertThat(QUARTER.matchesSpecs(QUARTER.sizeInMM(), QUARTER.weightInMg()), is(true));
	}

	@Test
	public void dimeMatchesSpecs() throws Exception {
		assertThat(DIME.matchesSpecs(DIME.sizeInMM(), DIME.weightInMg()), is(true));
	}

	@Test
	public void nickelMatchesSpecs() throws Exception {
		assertThat(NICKEL.matchesSpecs(NICKEL.sizeInMM(), NICKEL.weightInMg()), is(true));
	}
}
