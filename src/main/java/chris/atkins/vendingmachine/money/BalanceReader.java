package chris.atkins.vendingmachine.money;

public interface BalanceReader {

	double currentBalance();

	boolean isEmpty();
}
