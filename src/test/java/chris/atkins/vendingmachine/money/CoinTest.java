package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
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
	public void quarterSize5() throws Exception {
		assertThat(QUARTER.sizeInMM(), equalTo(5));
	}

	@Test
	public void quarterWeighs3() throws Exception {
		assertThat(QUARTER.weightInMg(), equalTo(3));
	}

	@Test
	public void dimeHasCorrectValue() throws Exception {
		assertThat(DIME.value(), equalTo(0.1));
	}

	@Test
	public void dimeSize3() throws Exception {
		assertThat(Coin.DIME.sizeInMM(), equalTo(3));
	}

	@Test
	public void dimeWeighs1() throws Exception {
		assertThat(Coin.DIME.weightInMg(), equalTo(1));
	}
}
