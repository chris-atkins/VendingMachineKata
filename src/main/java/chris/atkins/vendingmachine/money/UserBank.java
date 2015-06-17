package chris.atkins.vendingmachine.money;

public class UserBank {

	private double currentAmount = 0.0;

	public void add(final double amount) {
		this.currentAmount += amount;
	}

	public double currentAmount() {
		return this.currentAmount;
	}

}
