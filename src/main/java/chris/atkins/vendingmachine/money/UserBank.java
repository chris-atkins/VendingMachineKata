package chris.atkins.vendingmachine.money;

public class UserBank {

	private double currentAmount = 0.0;

	public void add(final double amount) {
		this.currentAmount += amount;
	}

	public void pay(final double amountToPay) {
		this.currentAmount -= amountToPay;
	}

	public double currentBalance() {
		return round(this.currentAmount);

	}

	private double round(final double a) {
		return (int) ((this.currentAmount + .00500001) * 100) / 100d;
	}
}
