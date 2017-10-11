package enumtest.test2;

/**
 * Created by huishen on 17/10/9.
 * 枚举
 */

public class WeightTable {

    public static void main(String[] args) {
        double earthWeitht = 175;
        double mass = earthWeitht / Planet.EARTH.getSurfaceGravity();
        for (Planet p : Planet.values()) {
            System.out.printf("Weitht on %s is %f\n", p, p.surfaceWeight(mass));
        }
    }

}
