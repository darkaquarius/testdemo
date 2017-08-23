package demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huishen on 17/8/17.
 *
 */

@SuppressWarnings("Duplicates")
public class SyGongJiJinDaiKuan {

    public static double getDengEBenXi(double benjin, double lilv, int months) {
        double var = Math.pow((1 + lilv / 12), months);
        double tem = benjin * (lilv / 12) * var;
        tem = tem / (var - 1);
        return tem;
    }

    public static List<Double> getDengEBenJin(double benjin, double lilv, int months) {
        List<Double> yuehuankuanList = new ArrayList<Double>();
        for (int i = 1; i <= months; i++) {
            double var = benjin / months;
            double tem = (benjin - var * (i - 1)) * (lilv / 12);
            tem = var + tem;
            yuehuankuanList.add(tem);
        }
        return yuehuankuanList;
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
        double huankuane = getDengEBenXi(benjin, lilv, months);
        System.out.println("月" + huankuane);
        System.out.println("年" + huankuane * 12);
        System.out.println("总" + huankuane * 360);
        System.out.println("还款总额占本金比例:" + huankuane * 360 / benjin);

        System.out.println("-----------------------------------------------");
        System.out.println("采用等额本金法：");
        List<Double> yuehuankuanList = getDengEBenJin(benjin, lilv, months);
        List<Double> nianhuankuanList = new ArrayList<Double>();
        double tem = 0;
        double sum = 0;
        for (int i = 0; i < months; i++) {

            //System.out.println(yuehuankuanList.get(i));
            tem += yuehuankuanList.get(i);
            sum += yuehuankuanList.get(i);
            if (i % 12 == 11) {
                nianhuankuanList.add(tem);
                tem = 0;
            }
        }
        if (months % 12 != 0) {
            nianhuankuanList.add(tem);
        }

        for (int i = 0; i < nianhuankuanList.size(); i++) {
            System.out.println("第" + (i + 1) + "年:" + nianhuankuanList.get(i));
        }

        System.out.println("总还款:" + sum);
        System.out.println("还款总额占本金比例:" + sum / benjin);
    }

}