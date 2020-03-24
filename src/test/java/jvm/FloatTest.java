package jvm;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2019/11/20 上午10:42
 * @Modified By:
 * @Version:
 * @TaskId:
 */
public class FloatTest {

    @Test
    public void test() {

        printFloatBitStr(10.25f);
        printFloatBitStr(-10.25f);

        // 最大的正float有限数
        printFloatBitStr(Float.MAX_VALUE);
        // 最小的正float有限数
        printFloatBitStr(Float.MIN_NORMAL);
        // 最小的正float弱规范数
        printFloatBitStr(Float.MIN_VALUE);
        // QNaN
        printFloatBitStr(Float.NaN);


        printFloatBitStr(10.25f);

        // 数字表示不了
        System.out.println(String.valueOf(10.250000059604645f));

        // 表示不了，变成10.25f了
        printFloatBitStr(10.250000059604645f);
        // 表示不了，变成10.250001f了
        printFloatBitStr(10.2500011f);

        printFloat("0 10000010 01001000000000000000000");
        printFloat("0 10000010 01001000000000000000001");

    }


    private void add(Float a, Float b) {
        FloatInfo floatA = getExponet(a);
        FloatInfo floatB = getExponet(b);
        int diff = floatA.exponet - floatB.exponet;

        int mantissaA = floatA.mantissa;
        int mantissaB = floatB.mantissa;
        String res ;
        if (diff > 0) {
            // 对阶 小的偏移
            mantissaB = mantissaB >> (diff/2);
            res = "0" + getBitStr(floatA.exponet, 8) + getBitStr(mantissaB + mantissaA, 23);
        }
        else {
            mantissaA = mantissaA >> (diff/2);
            res = "0" + getBitStr(floatB.exponet, 8) + getBitStr(mantissaB + mantissaA, 23);
        }

        System.out.println("=============");
        printFloatBitStr(a);
        printFloatBitStr(b);
        printFloat(res);
        System.out.println("=============");
    }


    class FloatInfo {
        // 指数位的值
        private int exponet;

        // 尾数的值
        private int mantissa;

        // 符号位
        private int sign;

        public FloatInfo(int exponet, int mantissa, int sign) {
            this.exponet = exponet;
            this.mantissa = mantissa;
            this.sign = sign;
        }
    }


    private String getBitStr(int f, int size) {
        String fs = Integer.toBinaryString(f);
        while (fs.length() < size) {
            fs = "0" + fs;
        }

        return fs;

    }


    /**
     *
     * @Description: 获取指数位的值
     *
     * @Auther: csp
     * @Date:  2019/11/20 下午5:37
     * @param a
     * @Return: FloatInfo
     * @Version:
     * @TaskId:
     */
    private FloatInfo getExponet(Float a) {
        String fs = Integer.toBinaryString(Float.floatToIntBits(a));
        while (fs.length() < 32) {
            fs = "0" + fs;
        }
        return new FloatInfo( Integer.parseInt(fs.substring(1, 9), 2), Integer.parseInt("1" + fs.substring(9), 2), Integer.parseInt(fs.substring(0, 1), 2));
    }


    private void printFloatBitStr(Float f) {
        String fs = Integer.toBinaryString(Float.floatToIntBits(f));
        while (fs.length() < 32) {
            fs = "0" + fs;
        }
        System.out.println(fs.substring(0, 1) + " " + fs.substring(1, 9) + " " + fs.substring(9) + " = " + f );

    }

    private void printFloat(String fs) {
        System.out.println(fs + " = " + Float.intBitsToFloat(Integer.parseInt(fs.replaceAll(" ", ""), 2)) );
    }



    public static Double round(int scale, Double value) {
        return new BigDecimal(String.valueOf(value)).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Test
    public void test2() {
        // 此处以双精度 double为例，计算过程中，会存在位移，导致精度丢失。
        System.out.println("0.05 + 0.01 = " + (0.05 + 0.01f) + "  => " + add(10, 0.05, 0.01));
        System.out.println("1.0 - 0.42 = " + (1.0f - 0.42f) + "  => " + sub(10, 1.0, 0.42));
        System.out.println("4.015 * 100 = " + (4.015 * 100) + "  => " + mul(10, 4.015, 100d));
        System.out.println("123.3 / 100 = " + (123.3 / 100) + "  => " + div(10, 123.3, 100d));

        System.out.println(4.015d);
        System.out.println(0.99d);

    }

    public static Double add(int scale, Double ... args) {
        BigDecimal sum = new BigDecimal("0");
        for (double arg : args) {
            sum = sum.add(new BigDecimal(String.valueOf(arg)));
        }
        return sum.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double sub(int scale, Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double mul(int scale, Double ... args) {
        BigDecimal sum = new BigDecimal("1");
        for (double arg : args) {
            sum = sum.multiply(new BigDecimal(String.valueOf(arg)));
        }
        return sum.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double div(int scale, Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}
