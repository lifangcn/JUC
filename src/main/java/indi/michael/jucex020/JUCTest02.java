package indi.michael.jucex020;

import java.util.LinkedList;
import java.util.List;

/**
 * @author michael
 * @implNote 提供两个方法，add、size 写两个线程
 * 线程1添加10个元素到容器，
 * 线程2监控到个数为5时，线程2提示并结束。
 * <p>
 * wait notify 方式
 * @since 2020/1/26
 */
public class JUCTest02 {

    List<Integer> list = new LinkedList<>();

    public void add(Integer integer) {
        list.add(integer);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        JUCTest02 obj = new JUCTest02();

        final Object lock = new Object();


        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 start");
                if (obj.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 print!");
                lock.notify();
            }
        }).start();

        new Thread(() -> {
            System.out.println("t1 start");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {

                    if (obj.size() == 5) {
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    obj.add(i);
                    System.out.println("add i = " + i);
                }
            }
        }).start();

    }

}
