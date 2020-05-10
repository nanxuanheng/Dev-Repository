package com.tensquare.user.test;

/**
 * @author nanheng
 * @date 2020/4/26 15:02
 */
public class Test1 {

    public static class Cat extends Animal implements Eat{

        @Override
        public void eatFood() {
            System.out.println("我喜欢吃鱼");
        }
        @Override
        // 动物类里面有叫和吃两个方法
        public void cry() {
            System.out.println("我不知道叫什么");
        }

        public void eat() {
            System.out.println("我不知道吃什么");
        }
    }

    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.eatFood();
        cat.eat();
        cat.cry();

    }

    /**
     * 抽象类或者接口不能实例化，可以作为应用类型
     * @param an
     * @param eat
     */
   public void master(Animal an, Eat eat){
       an.eat();

        eat.eatFood();
   }
}

