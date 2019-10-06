package notes.bank;

public class BankAccount {

    private double balance;

    BankAccount() {
        this.balance = 0;
    }

    BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.err.println("Insufficient funds");
        }
    }

    public double getBalance() {
        return balance;
    }

    public void transfer(double amount, BankAccount destination) {
        if (balance >= amount) {
            this.withdraw(amount);
            destination.deposit(amount);
        } else {
            System.err.println("Insufficient funds");
        }
    }

}