package chris.atkins.vendingmachine.display;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class DisplayManagerTest {

	@InjectMocks
	private DisplayManager displayManager;

	@Mock
	private Display display;

	@Test
	public void priceDisplaysCorrectlyForOneDollar() throws Exception {
		this.displayManager.notifyPrice(1.0);
		verify(this.display).update("PRICE $1.00");
	}

	@Test
	public void priceDisplaysCorrectlyFor65Cents() throws Exception {
		this.displayManager.notifyPrice(0.65);
		verify(this.display).update("PRICE $0.65");
	}

	@Test
	public void priceDisplaysCorrectlyFor50Cents() throws Exception {
		this.displayManager.notifyPrice(0.5);
		verify(this.display).update("PRICE $0.50");
	}

	@Test
	public void amountDisplaysCorrectlyForOneDollar() throws Exception {
		this.displayManager.updateBalanceStatus(1.0, true);
		verify(this.display).update("BALANCE $1.00");
	}

	@Test
	public void amountDisplaysCorrectlyFor45Cents() throws Exception {
		this.displayManager.updateBalanceStatus(0.45, true);
		verify(this.display).update("BALANCE $0.45");
	}

	@Test
	public void priceDisplaysCorrectlyFor30Cents() throws Exception {
		this.displayManager.updateBalanceStatus(0.3, true);
		verify(this.display).update("BALANCE $0.30");
	}

	@Test
	public void showsExactChangeWhenNoBalanceAndNoChange() throws Exception {
		this.displayManager.updateBalanceStatus(0.0, false);
		verify(this.display).update("EXACT CHANGE ONLY");
	}

	@Test
	public void showsInsertCoinWhenNoBalanceAndHasChange() throws Exception {
		this.displayManager.updateBalanceStatus(0.0, true);
		verify(this.display).update("INSERT COIN");
	}

	@Test
	public void thanksForThePurchaseDisplays() throws Exception {
		this.displayManager.thanksForThePurchase();
		verify(this.display).update("THANK YOU");
	}

	@Test
	public void outOfStock() throws Exception {
		this.displayManager.outOfStock();
		verify(this.display).update("SOLD OUT");
	}
}
