package enumtest;

import static enumtest.PayType.ALI_WAP_PAY;
import static enumtest.PayType.BALANCE_PAY;
import static enumtest.PayType.OSB_PAY;
import static enumtest.PayType.SCORE_PAY;
import static enumtest.PayType.WEI_APP_PAY;

/**
 * Created by huishen on 17/4/19.
 */
public enum BusinessType {
    UNDEFINED(new PayType[]{}),
    ONESHOP(new PayType[]{WEI_APP_PAY, ALI_WAP_PAY, BALANCE_PAY, SCORE_PAY, OSB_PAY}),
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
