package enumtest.test2;

/**
 * Created by huishen on 17/10/9.
 *
 */

public enum Planet {

    EARTH(5.9E+24, 6.38E+6),
    MARS(6.4E+23, 3.39E+6),
    ;

    private final double mass;  // in kilograms
    private final double radius;    // in meters
    private final double surfaceGravity;    // in m / s^2

    private static final double G = 6.67E-11;

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        surfaceGravity = G * mass / (radius * radius);
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public double getSurfaceGravity() {
        return surfaceGravity;
    }

    public double surfaceWeight(double mass) {
        return mass * surfaceGravity;
    }

}
