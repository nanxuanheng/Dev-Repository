package com.tensquare.user.Test1;

/**
 * @author nanheng
 * @date 2020/4/27 22:38
 */
public class Signleton {

    public static Signleton  uniqueInstance;
    private Signleton(){}
    public static Signleton getInstance(){
        if (uniqueInstance==null) {
            synchronized (Signleton.class) {


                if (uniqueInstance == null) {
                    uniqueInstance = new Signleton();
                    System.out.println(Thread.currentThread().getName() + "uniqueInstance初始化");
                }
            else {
                    System.out.println(Thread.currentThread().getName() + ": uniqueInstance is not null now...");
                }
            }
        }

        return uniqueInstance;
    }

}
class TestSignleton{
    public static void main(String[] args) {
        for (int i=1;i<1000;i++){
           final Thread t1 = new Thread();
            t1.setName("thread"+1);
            t1.start();
        }
    }

    public static class ThreadSignleton implements  Runnable{

        @Override
        public void run() {
            Signleton.getInstance();
        }
    }

}