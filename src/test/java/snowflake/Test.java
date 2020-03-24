package snowflake;

public class Test {

    @org.junit.Test public void test() {
        SnowFlakeGenerator snowFlakeGenerator = new SnowFlakeGenerator.Factory().create(12345, 98981);
        System.out.println(snowFlakeGenerator.nextId());
        System.out.println(snowFlakeGenerator.nextId());
    }


//    >>：带符号右移。正数右移高位补0，负数右移高位补1。比如：
//
//        4 >> 1，结果是2；-4 >> 1，结果是-2。-2 >> 1，结果是-1。
//
//        >>>：无符号右移。无论是正数还是负数，高位通通补0。
//
//    对于正数而言，>>和>>>没区别。
//    对于负数而言，-2 >>> 1，结果是2147483647（Integer.MAX_VALUE），-1 >>> 1，结果是2147483647（Integer.MAX_VALUE）

    @org.junit.Test
    public void binaryToDecimal() {
        int n = 4;
        for (int i = 31; i >= 0; i--)
            System.out.print(n >>> i & 1);

        System.out.println();

        System.out.println(Integer.toBinaryString(n));

        System.out.println(Integer.toBinaryString(n >> 2));

        System.out.println(Integer.toBinaryString(n >>> 2));

        // 负数位移
        System.out.println((0-n) + " >> 2 位移的结果是：" + ((0 - n) >> 2) + "， 二进制是：" + Integer.toBinaryString( (0 - n) >> 2));
        System.out.println(Integer.toBinaryString( (0 - n) >> 2).length());

        System.out.println((0-n) + " >>> 2 位移的结果是：" + ((0 - n) >>> 2) + "， 二进制是：" + Integer.toBinaryString( (0 - n) >>> 2));
        System.out.println(Integer.toBinaryString( (0 - n) >>> 2).length());

        n = 2;
        System.out.println((0-n) + " >>> 1 位移的结果是：" + ((0 - n) >>> 1) + "， 二进制是：" + Integer.toBinaryString( (0 - n) >>> 2));
        System.out.println(Integer.toBinaryString( (0 - n) >>> 1).length());

        System.out.println(Integer.toBinaryString( -2));
        System.out.println(Integer.toBinaryString( -4));

        System.out.println(Integer.numberOfLeadingZeros( 1 ));
        System.out.println(Integer.toBinaryString( 1 ));


        // 逐级减少区间，判断区间范围内是否为0，最终减少到2（最高位为符号位直接判断）
        System.out.println(Integer.numberOfLeadingZeros( Integer.MAX_VALUE >> 20));
    }
}