package ATM;

public class BankAccount {
    private static int accountCount = 0;
    private String customerName;
    private int pin;
    private double saldo;

    public BankAccount(String customerName, double saldo) {
        this.customerName = customerName;
        this.saldo = saldo;
        accountCount++;
    }

    public static int getAccountCount() { return accountCount; }
    public String getCustomerName() { return customerName; }
    public int getPin() { return pin; }
    public double getSaldo() { return saldo; }

    public void deposit(double amount) { saldo += amount; }
    public void withdraw(double amount) { saldo -= amount; }
    
    public void createPin(int pin) throws Exception {
        if(String.valueOf(pin).length() != 4) {
            throw new Exception("PIN must be 4 digits.");
        }
        this.pin = pin;
    }
}