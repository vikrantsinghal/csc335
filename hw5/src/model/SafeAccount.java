package model;

import java.text.NumberFormat;

/*
 * This class allows deposits and withdrawals, except SafeAccount objects have a
 * $1000.00 credit line meaning withdrawals can go up to $1000.00 over the balance.
 * The loan amount is maintained and accessible to programmers. Any deposit
 * amount first goes towards loan amount. Any additional funds can then go to
 * the balance. A SafeAccount object is also able to tell you the loan amount.
 * 
 * @author Vikrant Singhal
 */
public class SafeAccount extends BankAccount {

	// variable to keep track of the loan amount.
	private double loanAmount;

	/*
	 * Constructor
	 * 
	 * @param initID which is used to set the userID. initBalance which is used
	 * to set the initial balance. it automatically sets the loan amount to 0.
	 */
	public SafeAccount(String initID, double initBalance) {
		super(initID, initBalance);
		loanAmount = 0.0;
	}

	/*
	 * It checks if there is any pending loan amount that is to be covered. It
	 * adds to the loan amount first (if it has to be covered) and then adds to
	 * the balance.
	 * 
	 * @param the amount that is to be deposited to the account.
	 */
	@Override
	public void deposit(double depositAmount) {
		if (depositAmount <= 0)
			return;
		if (loanAmount > 0) {
			if (loanAmount >= depositAmount)
				loanAmount = loanAmount - depositAmount;
			else {
				addToBalance(depositAmount - loanAmount);
				loanAmount = 0.00;
			}
		} else
			addToBalance(depositAmount);
	}

	/*
	 * It checks if the requested amount is greater than 0. In the other case it
	 * returns false. Then it checks if the requested amount exceeds the balance
	 * or the limit to the loanAmount, which is $1000. If it does, it returns
	 * false. If it doesn't it subtracts the amount or adds some amount of money
	 * to the loan amount if required, and returns true.
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
		if (getBalance() <= 0) {
			if (loanAmount < 1000) {
				if (withdrawalAmount + loanAmount > 1000)
					return false;
				else {
					loanAmount += withdrawalAmount;
					return true;
				}
			} else
				return false;
		} else {
			if (withdrawalAmount <= getBalance()) {
				subtractFromBalance(withdrawalAmount);
				return true;
			} else {
				if (withdrawalAmount > (1000.00 + getBalance()))
					return false;
				else {
					loanAmount = withdrawalAmount - getBalance();
					subtractFromBalance(getBalance());
					return true;
				}
			}
		}
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	/*
	 * @return returns the string version of account details.
	 */
	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return getID() + " " + nf.format(getBalance()) + " "
				+ nf.format(getLoanAmount());
	}

}
