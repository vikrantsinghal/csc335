package model;

import java.text.NumberFormat;

/*
 * This class allows deposits and withdrawals up to the balance.
 * 
 * @author Vikrant Singhal
 */
public class RegularAccount extends BankAccount {

	/*
	 * Constructor
	 * 
	 * @param initID which is used to set the userID initBalance which is used
	 * to set the initial balance
	 */
	public RegularAccount(String initID, double initBalance) {
		super(initID, initBalance);
	}

	/*
	 * It checks if the requested amount is greater than 0. In the other case it
	 * returns false. It checks if withdrawalAmount is less than the balance in
	 * the account. if that check is true, it successfully subtracts that amount
	 * from the account and returns true. Else it returns false.
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
		if (getBalance() >= withdrawalAmount) {
			subtractFromBalance(withdrawalAmount);
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
