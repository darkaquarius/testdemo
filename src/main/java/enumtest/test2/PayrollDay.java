package enumtest.test2;

/**
 * Created by huishen on 17/10/9.
 * 策略枚举
 * 可以使得多个枚举常量共享相同的行为
 */
public enum PayrollDay {
    MONDAY(PayType.WEEKDAY),
    THUSDAY(PayType.WEEKDAY),
    WEDENSDAY(PayType.WEEKDAY),
    THURSDAY(PayType.WEEKDAY),
    FIRDAY(PayType.WEEKDAY),
    SATURDAY(PayType.WEEKEND),
    SUNDAY(PayType.WEEKEND),
    ;

    private final PayType payType;

    PayrollDay(PayType payType) {
        this.payType = payType;
    }

    public PayType getPayType() {
        return payType;
    }

    private enum PayType {
        WEEKDAY {
            @Override
            double overtimePay(double hrs, double payRate) {
                return hrs <= HOURS_PER_SHIFT ? 0 :
                    (hrs - HOURS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND {
            @Override
            double overtimePay(double hrs, double payRate) {
                return hrs * payRate / 2;
            }
        },
        ;

        private static final int HOURS_PER_SHIFT = 8;

        abstract double overtimePay(double hrs, double payRate);

        double pay(double hoursWorked, double payRate) {
            double basePay = hoursWorked * payRate;
            return basePay + overtimePay(hoursWorked, payRate);
        }
    }

}
