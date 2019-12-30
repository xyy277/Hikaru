package savvy.wit.framework.core.algorithm.model.leetcode.day02;

import java.util.concurrent.Semaphore;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Foo
 * Author : zhoujiajun
 * Date : 2019/11/21 14:29
 * Version : 1.0
 * Description : 
 ******************************/
class Foo {

    // 信号量, 请求 - 1, 释放 + 1. 当信号量 <= 0 时的请求会令线程阻塞, 直至信号量回到正数.
    private Semaphore mutex1 = new Semaphore(0), mutex2 = new Semaphore(0);

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        mutex1.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        mutex1.acquire();
        printSecond.run();
        mutex2.release();
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        mutex2.acquire();
        printThird.run();
    }

    public static void main(String[] args) throws Exception {
        Foo foo1 = new Foo();
        Foo foo2 = new Foo();
        Foo foo3 = new Foo();
        foo1.first(() -> System.out.println("first"));
        foo2.second(() -> System.out.println("second"));
        foo3.third(() -> System.out.println("third"));
    }
}
