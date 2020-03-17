package indi.michael.jucex020;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author michael
 * @implNote 提供两个方法，add、size 写两个线程
 * 线程1添加10个元素到容器，
 * 线程2监控到个数为5时，线程2提示并结束。
 * <p>
 * LockSupport 方式
 * @since 2020/1/26
 */
public class JUCTest03<T> {
    List<T> list = new LinkedList<>();

    public void add(T t) {
        list.add(t);
    }

    public int size() {
        return list.size();
    }

    public static Thread t1 = null;
    public static Thread t2 = null;

    public static void main(String[] args) {
        JUCTest03 obj = new JUCTest03();

        t2 = new Thread(() -> {
            System.out.println("t2 start");
//            if (obj.size() != 5) {
            LockSupport.park();
//            }
            LockSupport.unpark(t1);
            System.out.println("t2 print!");
        }, "t1");


        t1 = new Thread(() -> {
            System.out.println("t1 start");
            for (int i = 0; i < 10; i++) {
                if (obj.size() == 5) {
                    //
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
                obj.add(i);
                System.out.println("add i = " + i);
            }
        }, "t2");
        t2.start();
        t1.start();

    }

}
