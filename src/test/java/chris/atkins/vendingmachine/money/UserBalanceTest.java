package chris.atkins.vendingmachine.money;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class UserBalanceTest {

	private final UserBalance bank = new UserBalance();

	@Test
	public void startsWithZeroBalance() throws Exception {
		assertThat(this.bank.currentBalance(), equalTo(0.0));
	}

	@Test
	public void whenAmountIsAddedTheCurrentBalanceEqualsTheAmountAdded() throws Exception {
		this.bank.add(0.42);
		assertThat(this.bank.currentBalance(), equalTo(0.42));
	}

	@Test
	public void whenMultipleAmountsAreAddedTheTotalIsTheSumOfThem() throws Exception {
		this.bank.add(0.42);
		this.bank.add(1.00);
		this.bank.add(0.58);
		assertThat(this.bank.currentBalance(), equalTo(2.0));
	}

	@Test
	public void payRemovesCorrectAmountOfMoneyFromTotal() throws Exception {
		this.bank.add(2.0);
		this.bank.pay(1.0);
		this.bank.pay(0.42);
		assertThat(this.bank.currentBalance(), equalTo(0.58));
	}

	@Test
	public void currentBalanceRoundsDownToTwoDecimalPlacesWithReasonablePrecision() throws Exception {
		this.bank.add(1.0049999);
		assertThat(this.bank.currentBalance(), equalTo(1.0));
	}

	@Test
	public void currentBalanceRoundsUpToTwoDecimalPlacesWithReasonablePrecision() throws Exception {
		this.bank.add(.995);
		assertThat(this.bank.currentBalance(), equalTo(1.0));
	}
}
