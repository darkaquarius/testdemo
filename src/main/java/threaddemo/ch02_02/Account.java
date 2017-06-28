package threaddemo.ch02_02;

import lombok.Data;

/**
 * Created by huishen on 17/6/27.
 *
 */

@Data
public class Account {

    private double balance;

    public synchronized void addAmount(double amount) {
        double tmp = balance;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tmp += amount;
        balance = tmp;
    }

    public synchronized void subtractAmount(double amount) {
        double tmp = balance;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tmp -= amount;
        balance = tmp;
    }

}
