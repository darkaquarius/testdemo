package threaddemo.ch02_06;

/**
 * Created by huishen on 17/6/28.
 *
 */
public class Main {

    public static void main(String[] args) {
        PricesInfo pricesInfo = new PricesInfo();

        for (int i = 0; i < 5; i++) {
            new Thread(new Reader(pricesInfo)).start();
        }

        new Thread(new Writer(pricesInfo)).start();
    }

}
