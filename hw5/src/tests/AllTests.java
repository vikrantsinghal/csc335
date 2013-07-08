package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 * It runs all the tests together.
 * 
 * @author Vikrant Singhal
 */

@RunWith(Suite.class)
@SuiteClasses({ BankAccountCollectionTest.class, RegularAccountTest.class,
		FeeAccountTest.class, SafeAccountTest.class, TransactionListTest.class,
		TransactionTest.class, TransactionTypeTest.class })
public class AllTests {

}
