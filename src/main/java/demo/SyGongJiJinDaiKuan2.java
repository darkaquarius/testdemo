package demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huishen on 17/8/17.
 *
 */

@SuppressWarnings("Duplicates")
public class SyGongJiJinDaiKuan2 {

    // 等额本息
    public static CalRet getDengEBenXi(double benjin, double lilv, int months) {
        DecimalFormat df = new DecimalFormat("######0.00");

        double tem = benjin * (lilv / 12);
        double var = Math.pow((1 + lilv / 12), months);

        // 每期还款
        double refundOrCapticalPerPhase = tem * var / (var - 1);
        List<MoneyPerPhase> moneyPerPhaseList = new ArrayList<>();

        // 每期的本金和利息
        for (int i = 0; i < months; i++) {
            double var2 = Math.pow((1 + lilv /12), i);
            double capticalOrRefund = tem * var2 / (var - 1);
            double interest = refundOrCapticalPerPhase - capticalOrRefund;
            moneyPerPhaseList.add(MoneyPerPhase.builder()
                .capticalOrRefund(df.format(capticalOrRefund))
                .interest(df.format(interest))
                .build());
        }

        // 累积还款总和
        double totalRefund = refundOrCapticalPerPhase * months;

        // 累积支付利息
        double totalInterest = totalRefund - benjin;

        return CalRet.builder()
            .interestRate(String.valueOf(lilv))
            .totalInterest(df.format(totalInterest))
            .totalRefund(df.format(totalRefund))
            .refundOrCapticalPerPhase(df.format(refundOrCapticalPerPhase))
            .moneyPerPhaseList(moneyPerPhaseList)
            .build();
    }

    // 等额本金
    public static CalRet getDengEBenJin(double benjin, double lilv, int months) {
        DecimalFormat df = new DecimalFormat("######0.00");

        // 累积还款总和
        double totalRefund = 0;

        // 每期本金
        double capticalPerPhase = benjin / months;
        List<MoneyPerPhase> moneyPerPhaseList = new ArrayList<>();

        // 每期的还款和利息
        for (int i = 1; i <= months; i++) {
            double interest = (benjin - capticalPerPhase * (i - 1)) * (lilv / 12);
            double refund = capticalPerPhase + interest;
            moneyPerPhaseList.add(MoneyPerPhase.builder()
                .capticalOrRefund(df.format(refund))
                .interest(df.format(interest))
                .build());

            totalRefund += refund;
        }

        // 累积支付利息
        double totalInterest = totalRefund - benjin;

        return CalRet.builder()
            .interestRate(String.valueOf(lilv))
            .totalInterest(df.format(totalInterest))
            .totalRefund(df.format(totalRefund))
            .refundOrCapticalPerPhase(df.format(capticalPerPhase))
            .moneyPerPhaseList(moneyPerPhaseList)
            .build();

    }

    public static void main(String args[]) {

        int months = 360;//设置贷款月数
        double benjin = 200000;//设置贷款金额
        double lilv = 0.047;//设置年利率

        System.out.print("当前贷款额是：   " + benjin + "元                 ");
        System.out.print("当前年利率是：   " + (lilv * 100) + "%     ");
        System.out.print("贷款总月数是:  " + months + " 月");
        System.out.println("");

        System.out.println("---------------------------------------------");
        System.out.println("采用等额本息法：");
        CalRet ret1 = getDengEBenXi(benjin, lilv, months);
        System.out.println("月" + ret1);

        System.out.println("-----------------------------------------------");
        System.out.println("采用等额本金法：");
        CalRet ret2 = getDengEBenJin(benjin, lilv, months);
        List<Double> nianhuankuanList = new ArrayList<>();
        double tem = 0;
        double sum = 0;

        if (months % 12 != 0) {
            nianhuankuanList.add(tem);
        }

        for (int i = 0; i < nianhuankuanList.size(); i++) {
            System.out.println("第" + (i + 1) + "年:" + nianhuankuanList.get(i));
        }

        System.out.println("总还款:" + sum);
        System.out.println("还款总额占本金比例:" + sum / benjin);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CalRet {
        private String interestRate;
        private String totalInterest;
        private String totalRefund;
        private String refundOrCapticalPerPhase;
        private List<MoneyPerPhase> moneyPerPhaseList;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoneyPerPhase {
        private String capticalOrRefund;
        private String interest;
    }

}