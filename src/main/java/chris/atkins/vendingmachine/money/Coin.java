package chris.atkins.vendingmachine.money;

public enum Coin {
	QUARTER(0.25, 24, 567), DIME(0.1, 18, 227), NICKEL(0.05, 21, 500), INVALID_COIN(0, -1, -1);

	private double value;
	private int sizeInMM;
	private int weightInMg;

	private Coin(final double value, final int sizeInMM, final int weightInMg) {
		this.value = value;
		this.sizeInMM = sizeInMM;
		this.weightInMg = weightInMg;
	}

	public double value() {
		return this.value;
	}

	public int sizeInMM() {
		return this.sizeInMM;
	}

	public int weightInMg() {
		return this.weightInMg;
	}

}
