package indi.michael.jucex020;


/**
 * @author michael
 * @since 2020/1/29
 * <p>
 * 使用 synchronized/wait/notify 要求用线程顺序打印A1B2C3....Z26
 */
public class JUCTest05 {

    public static void main(String[] args) {


        char[] char1 = "ABCDEF".toCharArray();
        char[] char2 = "123456".toCharArray();
        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {

                try {
                    for (char c : char1) {
                        System.out.print(c);
                        lock.notify();
                        lock.wait();
                    }
                    lock.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "T1").start();

        new Thread(() -> {
            synchronized (lock) {

                try {
                    for (char c : char2) {
                        System.out.print(c);
                        lock.notify();
                        lock.wait();
                    }
                    lock.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "T2").start();

    }
}
