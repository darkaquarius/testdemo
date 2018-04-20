package demo;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author huishen
 * @date 18/3/26 下午9:28
 */
public class BigDecimalDemo {

    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal(12.3456789);
        BigDecimal round = bigDecimal.round(MathContext.DECIMAL32);

    }

}
