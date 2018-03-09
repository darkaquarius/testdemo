package demo;

/**
 * Created by huishen on 18/1/16.
 *
 * 特定于常量的方法实现
 *
 */

public enum EnumTest02 {

    PLUS {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVICE {
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    }
    ;

    abstract public double apply(double x, double y);

    public static void main(String[] args) {
        double apply = EnumTest02.PLUS.apply(2, 3);
        System.out.println(apply);
    }

}
