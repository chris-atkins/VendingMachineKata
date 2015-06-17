package chris.atkins.vendingmachine.items;

import static chris.atkins.vendingmachine.StartingInventory.STARTING_CANDY_INVENTORY;
import static chris.atkins.vendingmachine.StartingInventory.STARTING_CHIPS_INVENTORY;
import static chris.atkins.vendingmachine.StartingInventory.STARTING_COLA_INVENTORY;

import java.util.HashMap;
import java.util.Map;


public class ItemManager {

	private final Map<Item, Integer> inventory = new HashMap<>();

	public ItemManager() {
		this(STARTING_COLA_INVENTORY, STARTING_CANDY_INVENTORY, STARTING_CHIPS_INVENTORY);
	}

	public ItemManager(final int numberOfColas, final int numberOfCandies, final int numberOfChips) {
		this.inventory.put(Item.COLA, numberOfColas);
		this.inventory.put(Item.CANDY, numberOfCandies);
		this.inventory.put(Item.CHIPS, numberOfChips);
	}

	public boolean isOutOfStockFor(final Item item) {
		return this.inventory.get(item).equals(Integer.valueOf(0));
	}

	public void dispenseItemTo(final Item item, final ItemDispensor itemDispensor) {
		this.inventory.put(item, this.inventory.get(item) - 1);
		itemDispensor.dispenseItem(item);
	}

	public int numberOf(final Item item) {
		return this.inventory.get(item);
	}

	public void setInventory(final Item item, final int count) {
		this.inventory.put(item, count);
	}
}
