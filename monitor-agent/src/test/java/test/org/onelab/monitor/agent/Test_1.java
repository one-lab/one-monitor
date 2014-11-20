package test.org.onelab.monitor.agent;

/**
 * Created by chunliangh on 14-11-18.
 */
public class Test_1 {
    public static void main(String[] args) {

    }

    public int test1(byte by, short sh, char ch, int in, long lo, float fl, double dou,
                     String str, byte[] bytes, short[] shorts, char[] chars, int[] ints,
                     long[] longs, float[] floats, double[] doubles, String[] strings,
                     Byte[] bytess, Short[] shortss, Character[] characters, Integer[] integers,
                     Long[] longss, Float[] floatss, Double[] doubless, Test_1 test1, Test_1[] test1s) {
        System.out.println(by);
        System.out.println(sh);
        System.out.println(ch);
        System.out.println(in);
        System.out.println(lo);
        System.out.println(fl);
        System.out.println(dou);
        System.out.println(str);
        System.out.println(bytes);
//        System.out.println(shorts);
//        System.out.println(chars);
//        System.out.println(ints);
//        System.out.println(longs);
//        System.out.println(floats);
//        System.out.println(doubles);
//        System.out.println(strings);
//        System.out.println(bytess);
//        System.out.println(shortss);
//        System.out.println(characters);
//        System.out.println(integers);
//        System.out.println(longss);
//        System.out.println(floatss);
//        System.out.println(doubless);
//        System.out.println(test1);
//        System.out.println(test1s);
        by = 12;
        sh = 22;
        ch = 32;
        in = 42;
        lo = 52;
        fl = 62;
        dou = 72;
        str = "82";
        byte b1 = bytes[0];
        short s1 = shorts[1];
        char c1 = chars[2];
        long l1 = longs[3];
        float f1 = floats[4];
        double d1 = doubles[5];
        String str1 = strings[6];
        Byte b11 = bytes[0];
        Short s11 = shorts[1];
        Character c11 = chars[2];
        Long l11 = longs[3];
        Float f11 = floats[4];
        Double d11 = doubles[5];
        Byte b111 = bytess[0];
        Short s111 = shortss[1];
        Character c111 = characters[2];
        Long l111 = longss[3];
        Float f111 = floatss[4];
        Double d111 = doubless[5];
        test1 = test1s[0];
        Character[] characters1 = characters;
        if (in>15){
            return 12;
        }else {
            return 11;
        }
    }
    public void test2(){
//        String a = "sss";
//        System.out.println(a);
//        String b = new String();
//        System.out.println(b);
        String a = "s";
        test3("ss",false);
    }
    public int test3(String a,boolean b){
        try{
            a.equals(b);
        }catch (Exception e){
            a = "-1";
        }finally {
            b = false;
        }
        return 0;
    }
    public void test4() throws Exception{
        try{
            System.currentTimeMillis();
        }catch (Exception e){
            test5(e);
            throw e;
        }
    }
    public void test5(Throwable throwable){
        try {
            Class a = Throwable.class;
            Object b = a;
        }finally {
            new String();
        }
    }
}
