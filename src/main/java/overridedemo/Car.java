package overridedemo;

/**
 * @author huishen
 * @date 2019-07-24 11:06
 */
public class Car extends AbstractCar implements ICar {

    @Override
    public void driver() {
        System.out.println("car driver");
    }

    public static void main(String[] args) {
        ICar car = new Car();
        car.run();
        car.driver();
    }

}
