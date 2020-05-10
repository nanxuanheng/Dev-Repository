package com.tensquare.user.test;

/**
 * @author nanheng
 * @date 2020/4/26 10:20
 */
public class Test {


    /**
     * 父类方法
     */
    public static class  Parent1{
         public int book=6;
         public void base(){
             System.out.println("父类的普通方法");
         }
        public void test(){
             System.out.println("父类的测试方法");
        }
    }


    /**
     * 子类方法
     *
     */
    public static class Children1 extends Parent1{
        public String book="轻量级javaee企业应用实战";
        public void test(){
            System.out.println("子类的测试方法");
        }
        public void sub(){
            System.out.println("子类的普通方法");
        }


    }





    /*=============================================*/

    /**
     * main方法测试
     * @param args
     */
    public static void main(String[] args) {
        Parent1 d1 = new Children1();
        //Children1 d1 = new Children1();
        System.out.println(d1.book);
        //轻量级javaee企业应用实战

        d1.base();

        d1.test();
    }
}
