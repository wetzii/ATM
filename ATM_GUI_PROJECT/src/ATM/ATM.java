package ATM;

public class ATM {
    private String serialNumber;
    private String location;
    private boolean status;
    private static int transactionCount;

    public ATM (String name, String location, String serialNumber, Saboteur sab) {
        this.serialNumber = serialNumber;
        this.location = location;
        this.status = true;
        sab.pushAtmToArray(this);
    }

    public boolean checkPin(BankAccount acc, int pin) {
        return acc.getPin() == pin;
    }

    public boolean drawMoney (BankAccount acc, double sum) throws Exception {
        if(sum > acc.getSaldo()) {
            throw new Exception("Insufficient funds.");
        }
        return true;
    }

    public void depositMoney(BankAccount acc, double amount) throws Exception {
        if (amount <= 0) throw new Exception("Invalid amount.");
        transactionCount++;
        acc.deposit(amount);
    }

    public void changeStatus() { status = !status; }
    public boolean getStatus() { return status; }
    public String getLocation() { return location; }
    public String getSerialNumber() { return serialNumber; }
    public static int getTransactionCount() { return transactionCount; }
}