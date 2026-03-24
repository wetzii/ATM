package ATM;

public class Saboteur {
    private ATM[] atmList = new ATM[10];
    private int currentPos = 0;

    public void pushAtmToArray(ATM atm) {
        if (currentPos < atmList.length) {
            atmList[currentPos++] = atm;
        }
    }

    public void destroyRandAtm() {
        int index = (int)(Math.random() * currentPos);
        if (atmList[index] != null) {
            atmList[index].changeStatus();
        }
    }
}