package chris.atkins.vendingmachine.items;

import static chris.atkins.vendingmachine.StartingInventory.STARTING_CANDY_INVENTORY;
import static chris.atkins.vendingmachine.StartingInventory.STARTING_CHIPS_INVENTORY;
import static chris.atkins.vendingmachine.StartingInventory.STARTING_COLA_INVENTORY;

import java.util.HashMap;
import java.util.Map;


public class ItemManager {

	private final Map<Item, Integer> inventory = new HashMap<>();
	private final ItemDispensor itemDispensor;

	public ItemManager(final ItemDispensor itemDispensor) {
		this(itemDispensor, STARTING_COLA_INVENTORY, STARTING_CANDY_INVENTORY, STARTING_CHIPS_INVENTORY);
	}

	public ItemManager(final ItemDispensor itemDispensor, final int numberOfColas, final int numberOfCandies, final int numberOfChips) {
		this.itemDispensor = itemDispensor;
		setInventory(Item.COLA, numberOfColas);
		setInventory(Item.CANDY, numberOfCandies);
		setInventory(Item.CHIPS, numberOfChips);
	}

	public boolean isOutOfStockFor(final Item item) {
		return this.inventory.get(item).equals(Integer.valueOf(0));
	}

	public void dispenseItem(final Item item) {
		setInventory(item, numberOf(item) - 1);
		this.itemDispensor.dispenseItem(item);
	}

	int numberOf(final Item item) {
		return this.inventory.get(item);
	}

	public void setInventory(final Item item, final int count) {
		this.inventory.put(item, count);
	}
}
