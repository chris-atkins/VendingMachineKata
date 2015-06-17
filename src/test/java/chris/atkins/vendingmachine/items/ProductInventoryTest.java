package chris.atkins.vendingmachine.items;

import static chris.atkins.vendingmachine.items.Item.CANDY;
import static chris.atkins.vendingmachine.items.Item.CHIPS;
import static chris.atkins.vendingmachine.items.Item.COLA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ProductInventoryTest {

	@Mock
	private ItemDispensor dispensor;

	@Test
	public void initializesWithPassedValues() throws Exception {
		final ProductInventory inventory = new ProductInventory(1, 2, 3);
		assertThat(inventory.numberOf(COLA), equalTo(1));
		assertThat(inventory.numberOf(CANDY), equalTo(2));
		assertThat(inventory.numberOf(CHIPS), equalTo(3));
	}

	@Test
	public void outOfStockTrue() throws Exception {
		final ProductInventory inventory = new ProductInventory(0, 2, 3);
		assertThat(inventory.isOutOfStockFor(COLA), is(true));
	}

	@Test
	public void outOfStockFalse() throws Exception {
		final ProductInventory inventory = new ProductInventory(0, 2, 3);
		assertThat(inventory.isOutOfStockFor(CHIPS), is(false));
	}

	@Test
	public void dispenseDecrementsSingleItem() throws Exception {
		final ProductInventory inventory = new ProductInventory(1, 2, 3);
		inventory.dispense(CHIPS, this.dispensor);
		assertThat(inventory.numberOf(COLA), equalTo(1));
		assertThat(inventory.numberOf(CANDY), equalTo(2));
		assertThat(inventory.numberOf(CHIPS), equalTo(2));
	}

	@Test
	public void dispensorIsCalledOnDispense() throws Exception {
		final ProductInventory inventory = new ProductInventory(1, 2, 3);
		inventory.dispense(CANDY, this.dispensor);
		Mockito.verify(this.dispensor).dispenseItem(CANDY);
	}

	@Test
	public void setInventoryWorks() throws Exception {
		final ProductInventory inventory = new ProductInventory(1, 2, 3);
		inventory.setInventory(COLA, 5);
		assertThat(inventory.numberOf(COLA), equalTo(5));
		assertThat(inventory.numberOf(CANDY), equalTo(2));
		assertThat(inventory.numberOf(CHIPS), equalTo(3));
	}
}
