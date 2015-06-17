package chris.atkins.vendingmachine.display;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
		Mockito.verify(this.display).update("PRICE $1.00");
	}

	@Test
	public void priceDisplaysCorrectlyFor65Cents() throws Exception {
		this.displayManager.notifyPrice(0.65);
		Mockito.verify(this.display).update("PRICE $0.65");
	}

	@Test
	public void priceDisplaysCorrectlyFor50Cents() throws Exception {
		this.displayManager.notifyPrice(0.5);
		Mockito.verify(this.display).update("PRICE $0.50");
	}
}
