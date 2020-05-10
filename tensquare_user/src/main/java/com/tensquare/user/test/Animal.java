package com.tensquare.user.test;

/**
 * @author nanheng
 * @date 2020/4/26 11:53
 */
// 动物类
public abstract class Animal {
    int age;
    String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 动物类里面有叫和吃两个方法
    public void cry() {
        System.out.println("我不知道叫什么");
    }

    public void eat() {
        System.out.println("我不知道吃什么");
    }
}