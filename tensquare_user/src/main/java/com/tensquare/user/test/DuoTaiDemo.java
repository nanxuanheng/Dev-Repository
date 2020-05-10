package com.tensquare.user.test;

/**
 * @author nanheng
 * @date 2020/4/26 12:14
 */
public class DuoTaiDemo {
    public static void main(String[] args) {
        Master master = new Master();
        master.feed(new Dog(), new Bone());

        master.feed(new Cat(), new Fish());

    }
}
