package chris.atkins.vendingmachine.items;

public enum Item {
	COLA(1.0), CANDY(0.65);

	private double price;

	private Item(final double price) {
		this.price = price;
	}

	public double price() {
		return this.price;
	}
}
