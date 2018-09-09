package ipipdatx;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author huishen
 * @date 2018/8/4 上午10:14
 */
public class Main {

    public static void main(String[] args) {

        try {
            City city = new City("/Users/huishen/git/testdemo/src/main/resources/mydata4vipday4.datx"); // 城市库

            System.out.println(Arrays.toString(city.find("8.8.8.8")));
            System.out.println(Arrays.toString(city.find("255.255.255.255")));
            System.out.println(Arrays.toString(city.find("169.45.77.183")));
            System.out.println(Arrays.toString(city.find("180.172.107.61")));

            // District district = new District("/Users/huishen/git/testdemo/src/main/resources/mydata4vipday4.datx");//区县库
            //
            // System.out.println(Arrays.toString(district.find("1.12.0.0")));
            // System.out.println(Arrays.toString(district.find("223.255.127.250")));

            // BaseStation baseStation = new BaseStation("/Users/huishen/git/testdemo/src/main/resources/mydata4vipday4.datx"); // 基站库
            //
            // System.out.println(Arrays.toString(baseStation.find("8.8.8.8")));
            // System.out.println(Arrays.toString(baseStation.find("223.221.121.0")));

        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (IPv4FormatException ipex) {
            ipex.printStackTrace();
        }
    }
}
