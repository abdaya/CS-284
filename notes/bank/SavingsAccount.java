package notes.bank;

public class SavingsAccount extends BankAccount {

    private double rate;

    SavingsAccount(double rate) {
        super();
        this.rate = rate;
    }

    public void addIntrest() {
        super.deposit(rate * super.getBalance());
    }

}