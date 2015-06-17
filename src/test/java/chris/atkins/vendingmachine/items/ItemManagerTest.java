package chris.atkins.vendingmachine.items;

import static chris.atkins.vendingmachine.StartingInventory.STARTING_CANDY_INVENTORY;
import static chris.atkins.vendingmachine.StartingInventory.STARTING_CHIPS_INVENTORY;
import static chris.atkins.vendingmachine.StartingInventory.STARTING_COLA_INVENTORY;
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
public class ItemManagerTest {

	@Mock
	private ItemDispensor dispensor;

	@Test
	public void defaultConstructorStartsWithStartingInventoryConstants() throws Exception {
		final ItemManager inventory = new ItemManager(this.dispensor);
		assertThat(inventory.numberOf(COLA), equalTo(STARTING_COLA_INVENTORY));
		assertThat(inventory.numberOf(CANDY), equalTo(STARTING_CANDY_INVENTORY));
		assertThat(inventory.numberOf(CHIPS), equalTo(STARTING_CHIPS_INVENTORY));
	}

	@Test
	public void initializesWithPassedValues() throws Exception {
		final ItemManager inventory = new ItemManager(this.dispensor, 1, 2, 3);
		assertThat(inventory.numberOf(COLA), equalTo(1));
		assertThat(inventory.numberOf(CANDY), equalTo(2));
		assertThat(inventory.numberOf(CHIPS), equalTo(3));
	}

	@Test
	public void outOfStockTrue() throws Exception {
		final ItemManager inventory = new ItemManager(this.dispensor, 0, 2, 3);
		assertThat(inventory.isOutOfStockFor(COLA), is(true));
	}

	@Test
	public void outOfStockFalse() throws Exception {
		final ItemManager inventory = new ItemManager(this.dispensor, 0, 2, 3);
		assertThat(inventory.isOutOfStockFor(CHIPS), is(false));
	}

	@Test
	public void dispenseDecrementsSingleItem() throws Exception {
		final ItemManager inventory = new ItemManager(this.dispensor, 1, 2, 3);
		inventory.dispenseItem(CHIPS);
		assertThat(inventory.numberOf(COLA), equalTo(1));
		assertThat(inventory.numberOf(CANDY), equalTo(2));
		assertThat(inventory.numberOf(CHIPS), equalTo(2));
	}

	@Test
	public void dispensorIsCalledOnDispense() throws Exception {
		final ItemManager inventory = new ItemManager(this.dispensor, 1, 2, 3);
		inventory.dispenseItem(CANDY);
		Mockito.verify(this.dispensor).dispenseItem(CANDY);
	}

	@Test
	public void setInventoryWorks() throws Exception {
		final ItemManager inventory = new ItemManager(this.dispensor, 1, 2, 3);
		inventory.setInventory(COLA, 5);
		assertThat(inventory.numberOf(COLA), equalTo(5));
		assertThat(inventory.numberOf(CANDY), equalTo(2));
		assertThat(inventory.numberOf(CHIPS), equalTo(3));
	}
}
