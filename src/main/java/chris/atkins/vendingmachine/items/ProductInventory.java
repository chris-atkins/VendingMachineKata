package chris.atkins.vendingmachine.items;

import java.util.HashMap;
import java.util.Map;


public class ProductInventory {

	private final Map<Item, Integer> inventory = new HashMap<>();

	public ProductInventory(final int numberOfColas, final int numberOfCandies, final int numberOfChips) {
		this.inventory.put(Item.COLA, numberOfColas);
		this.inventory.put(Item.CANDY, numberOfCandies);
		this.inventory.put(Item.CHIPS, numberOfChips);
	}

	public boolean isOutOfStockFor(final Item item) {
		return this.inventory.get(item).equals(Integer.valueOf(0));
	}

	public void dispense(final Item item, final ProductDispensor productDispensor) {
		this.inventory.put(item, this.inventory.get(item) - 1);
		productDispensor.dispenseItem(item);
	}

	public int numberOf(final Item item) {
		return this.inventory.get(item);
	}

	public void setInventory(final Item item, final int count) {
		this.inventory.put(item, count);
	}
}
