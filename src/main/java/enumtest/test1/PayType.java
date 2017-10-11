package enumtest.test1;

/**
 * Created by huishen on 17/4/19.
 *
 */

public enum PayType {
    UNDEFINED(false),//0
    WEI_APP_PAY(true),//1 微信APP支付
    ALI_WAP_PAY(true),//2  支付宝 手机网站
    BALANCE_PAY(false),//3 余额支付
    SCORE_PAY(false),//4 积分支付
    OSB_PAY(false);//5夺宝币支付


    private Boolean idNeedNotify;

    public Boolean getIdNeedNotify() {
        return idNeedNotify;
    }

    public void setIdNeedNotify(Boolean idNeedNotify) {
        this.idNeedNotify = idNeedNotify;
    }

    PayType(Boolean idNeedNotify) {
        this.idNeedNotify = idNeedNotify;
    }
}
