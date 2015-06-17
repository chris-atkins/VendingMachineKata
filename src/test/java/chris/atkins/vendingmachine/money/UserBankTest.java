package chris.atkins.vendingmachine.money;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class UserBankTest {

	private final UserBank bank = new UserBank();

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
}
