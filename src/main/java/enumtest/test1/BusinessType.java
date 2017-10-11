package enumtest.test1;

/**
 * Created by huishen on 17/4/19.
 *
 */

public enum BusinessType {
    UNDEFINED(new PayType[]{}),
    ONESHOP(new PayType[]{PayType.WEI_APP_PAY, PayType.ALI_WAP_PAY, PayType.BALANCE_PAY, PayType.SCORE_PAY, PayType.OSB_PAY}),
    DSP(new PayType[]{}),
    ONERECHARGE(new PayType[]{PayType.ALI_WAP_PAY, PayType.WEI_APP_PAY});

    private PayType[] payTypes;

    public PayType[] getPayTypes() {
        return payTypes;
    }

    public void setPayTypes(PayType[] payTypes) {
        this.payTypes = payTypes;
    }

    BusinessType(PayType[] payTypes) {
        this.payTypes = payTypes;
    }
}
