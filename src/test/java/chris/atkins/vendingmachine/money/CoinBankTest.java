package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CoinBankTest {

	private CoinBank bank;

	@Mock
	private CoinReturn coinReturn;

	@Before
	public void init() {
		this.bank = new CoinBank(1, 2, 1);
	}

	@Test
	public void returnsANickel() throws Exception {
		this.bank.returnChange(0.05, this.coinReturn);
		verify(this.coinReturn).returnCoin(NICKEL);
	}

	@Test
	public void returnsADime() throws Exception {
		this.bank.returnChange(0.10, this.coinReturn);
		verify(this.coinReturn).returnCoin(DIME);
	}

	@Test
	public void returnsAQuarter() throws Exception {
		this.bank.returnChange(0.25, this.coinReturn);
		verify(this.coinReturn).returnCoin(QUARTER);
	}

	@Test
	public void returnsMultipleCoins() throws Exception {
		this.bank.returnChange(0.40, this.coinReturn);
		verify(this.coinReturn).returnCoin(QUARTER);
		verify(this.coinReturn).returnCoin(DIME);
		verify(this.coinReturn).returnCoin(NICKEL);
	}

	@Test
	public void returnsBestDenominations() throws Exception {
		this.bank.returnChange(0.45, this.coinReturn);
		verify(this.coinReturn).returnCoin(QUARTER);
		verify(this.coinReturn, times(2)).returnCoin(DIME);
	}

	@Test
	public void addIncreasesCountByOne() throws Exception {
		final int existingCount = this.bank.numberOf(NICKEL);
		this.bank.add(NICKEL);
		assertThat(this.bank.numberOf(NICKEL), equalTo(existingCount + 1));
	}

	@Test
	public void changeDecreasesTheCountCorrectly() throws Exception {
		this.bank.returnChange(0.45, this.coinReturn);
		verify(this.coinReturn).returnCoin(QUARTER);
		verify(this.coinReturn, times(2)).returnCoin(DIME);
		assertThat(this.bank.numberOf(QUARTER), equalTo(0));
		assertThat(this.bank.numberOf(DIME), equalTo(0));
		assertThat(this.bank.numberOf(NICKEL), equalTo(1));
	}

	@Test
	public void doesntUseCoinsItDoesntHaveForChange() throws Exception {
		final CoinBank dimeBank = bankWithAllDimes();
		dimeBank.returnChange(0.6, this.coinReturn);
		verify(this.coinReturn, times(6)).returnCoin(DIME);
	}

	private CoinBank bankWithAllDimes() {
		return new CoinBank(0, 6, 0);
	}
}
