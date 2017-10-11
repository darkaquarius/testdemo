package enumtest.test2;

/**
 * Created by huishen on 17/10/9.
 * 特定于常量的方法实现
 * 缺点是：在枚举变量中共享代码更加困难
 */

public enum Operation {
    PLUS("+") {
        @Override
        double apply(double x, double y) {return x + y;}
    },
    MINUS("-") {
        @Override
        double apply(double x, double y) { return x - y;}
    },
    TIMES("*") {
        @Override
        double apply(double x, double y) { return x * y;}
    },
    DIVIDE("/") {
        @Override
        double apply(double x, double y) {return x / y;}
    },
    ;

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    abstract double apply(double x, double y);

}
