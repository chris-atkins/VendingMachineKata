package chris.atkins.vendingmachine.money;

public enum Coin {
	QUARTER(0.25, 5, 3), DIME(0.1, 3, 1);

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
