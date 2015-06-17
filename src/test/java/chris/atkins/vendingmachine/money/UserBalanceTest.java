package chris.atkins.vendingmachine.money;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class UserBalanceTest {

	private final UserBalance balance = new UserBalance();

	@Test
	public void startsWithZeroBalance() throws Exception {
		assertThat(this.balance.currentBalance(), equalTo(0.0));
	}

	@Test
	public void whenAmountIsAddedTheCurrentBalanceEqualsTheAmountAdded() throws Exception {
		this.balance.add(0.42);
		assertThat(this.balance.currentBalance(), equalTo(0.42));
	}

	@Test
	public void whenMultipleAmountsAreAddedTheTotalIsTheSumOfThem() throws Exception {
		this.balance.add(0.42);
		this.balance.add(1.00);
		this.balance.add(0.58);
		assertThat(this.balance.currentBalance(), equalTo(2.0));
	}

	@Test
	public void payRemovesCorrectAmountOfMoneyFromTotal() throws Exception {
		this.balance.add(2.0);
		this.balance.pay(1.0);
		this.balance.pay(0.42);
		assertThat(this.balance.currentBalance(), equalTo(0.58));
	}

	@Test
	public void currentBalanceRoundsDownToTwoDecimalPlacesWithReasonablePrecision() throws Exception {
		this.balance.add(1.0049999);
		assertThat(this.balance.currentBalance(), equalTo(1.0));
	}

	@Test
	public void currentBalanceRoundsUpToTwoDecimalPlacesWithReasonablePrecision() throws Exception {
		this.balance.add(.995);
		assertThat(this.balance.currentBalance(), equalTo(1.0));
	}

	@Test
	public void resetSetsBalanceToZero() throws Exception {
		this.balance.add(12.42);
		this.balance.reset();
		assertThat(this.balance.currentBalance(), equalTo(0.0));
	}

	@Test
	public void reportsIsEmptyTrueCorrectly() throws Exception {
		this.balance.reset();
		assertThat(this.balance.isEmpty(), is(true));
	}

	@Test
	public void reportsIsEmptyFalseCorrectly() throws Exception {
		this.balance.add(.01);
		assertThat(this.balance.isEmpty(), is(false));
	}
}
