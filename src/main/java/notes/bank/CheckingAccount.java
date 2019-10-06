package notes.bank;

public class CheckingAccount extends BankAccount {

    private int operations;

    CheckingAccount(double initialBalance) {
        super(initialBalance);
        operations = 0;
    }

}