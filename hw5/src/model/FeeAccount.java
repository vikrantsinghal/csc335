package model;

import java.text.NumberFormat;

/*
 * This class inherits BankAccount.
 * Allows deposits and withdrawals up to the balance, except that there is a
 * $2.00 fee to make a withdrawal. It adds the $2.00 fee to the amount being withdrawn.
 * If there is not enough to allow the balance plus this $2.00 fee, it does not allow
 * the transaction.
 * 
 * @author Vikrant Singhal
 */
public class FeeAccount extends BankAccount {

	/*
	 * Constructor
	 * 
	 * @param initID which is used to set the userID initBalance which is used
	 * to set the initial balance
	 */
	public FeeAccount(String initID, double initBalance) {
		super(initID, initBalance);
	}

	/*
	 * This function checks if withdrawalAmount is positive. If it is positive
	 * it goes ahead else it return false. Every time a request is made to
	 * withdraw an amount it charges $2 fee which is deducted from the account.
	 * If the balance in the account is less than $(2 + withdrawalAmount), it
	 * refuses the transaction and returns false. It returns true in case of
	 * successful withdrawal.
	 * 
	 * @param withdrawalAmount which is the amount requested to withdraw
	 * 
	 * @return returns true if the amount has been successfully withdrawn else
	 * it returns false
	 */
	@Override
	public boolean withdraw(double withdrawalAmount) {
		if (withdrawalAmount <= 0)
			return false;
		if (getBalance() >= (2.00 + withdrawalAmount)) {
			subtractFromBalance(withdrawalAmount + 2.00);
			return true;
		}
		return false;
	}

	/*
	 * @return returns the string version of account details.
	 */
	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return getID() + " " + nf.format(getBalance());
	}

}
