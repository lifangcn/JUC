package indi.michael.jucex020;


import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author michael
 * @since 2020/1/29
 * <p>
 * 使用1.lock/condition 2.synchronized/wait/notify 要求用线程顺序打印A1B2C3....Z26
 */
public class JUCTest04 {

    public static void main(String[] args) {


        char[] char1 = "ABCDEF".toCharArray();
        char[] char2 = "123456".toCharArray();

        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition1 = reentrantLock.newCondition();
        Condition condition2 = reentrantLock.newCondition();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 3, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());


        new Thread(() -> {
            reentrantLock.lock();
            for (int i = 0; i < char1.length; i++) {
                condition1.signal();
                System.out.print(char1[i]);

                try {
                    condition2.signal();
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            condition2.signal();
            reentrantLock.unlock();
        }, "T1").start();

        new Thread(() -> {
            reentrantLock.lock();
            for (int i = 0; i < char2.length; i++) {
                System.out.print(char2[i]);

                try {
                    condition1.signal();
                    condition2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            condition1.signal();
            reentrantLock.unlock();
        }, "T2").start();

    }
}
