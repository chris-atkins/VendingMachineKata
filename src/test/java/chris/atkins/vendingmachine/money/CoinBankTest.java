package chris.atkins.vendingmachine.money;

import static chris.atkins.vendingmachine.money.Coin.DIME;
import static chris.atkins.vendingmachine.money.Coin.NICKEL;
import static chris.atkins.vendingmachine.money.Coin.QUARTER;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import chris.atkins.vendingmachine.CoinReturn;


@RunWith(MockitoJUnitRunner.class)
public class CoinBankTest {

	@InjectMocks
	private CoinBank bank;

	@Mock
	private CoinReturn coinReturn;

	@Test
	public void returnsANickel() throws Exception {
		this.bank.returnChange(0.05, this.coinReturn);
		Mockito.verify(this.coinReturn).returnCoin(NICKEL);
	}

	@Test
	public void returnsADime() throws Exception {
		this.bank.returnChange(0.10, this.coinReturn);
		Mockito.verify(this.coinReturn).returnCoin(DIME);
	}

	@Test
	public void returnsAQuarter() throws Exception {
		this.bank.returnChange(0.25, this.coinReturn);
		Mockito.verify(this.coinReturn).returnCoin(QUARTER);
	}
}
