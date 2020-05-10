package com.tensquare.user.test;

/**
 * @author nanheng
 * @date 2020/4/26 11:57
 */
public class Cat extends Animal {

    // 覆盖（重写）方法
    public void cry() {
        System.out.println("喵喵");
    }

    public void eat() {
        System.out.println("我是猫，我爱吃鱼");
    }
}
