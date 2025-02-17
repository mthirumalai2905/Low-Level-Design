import java.util.Scanner;

class Account {
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return false;
        }
        balance -= amount;
        System.out.println("Withdrawn: " + amount);
        return true;
    }
}

public class ATM {
    public static void main(String[] args) {
        Account account = new Account(1000.0);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("ATM Menu: 1. Check Balance  2. Deposit  3. Withdraw  4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Balance: " + account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 4:
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
