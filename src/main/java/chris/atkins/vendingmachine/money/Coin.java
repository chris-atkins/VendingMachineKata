package chris.atkins.vendingmachine.money;

public enum Coin {
	QUARTER(0.25, 24, 567), DIME(0.1, 18, 227), NICKEL(0.05, 21, 500), INVALID_COIN(0, null, null);

	private double value;
	private Integer sizeInMM;
	private Integer weightInMg;

	private Coin(final double value, final Integer sizeInMM, final Integer weightInMg) {
		this.value = value;
		this.sizeInMM = sizeInMM;
		this.weightInMg = weightInMg;
	}

	public double value() {
		return this.value;
	}

	public Integer sizeInMM() {
		return this.sizeInMM;
	}

	public Integer weightInMg() {
		return this.weightInMg;
	}

	public boolean matchesSpecs(final int sizeInMM, final int weightInMg) {
		return this.sizeInMM == sizeInMM && this.weightInMg == weightInMg;
	}
}
