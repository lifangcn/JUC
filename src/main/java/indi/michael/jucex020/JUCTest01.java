package indi.michael.jucex020;

import java.util.Vector;

/**
 * @author michael
 * @since 2020/1/26
 */
public class JUCTest01 {

    public Vector<Integer> list = new Vector<>();

    public void add(Integer integer) {
        list.add(integer);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        JUCTest01 obj = new JUCTest01();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                obj.add(i);
                System.out.println("add: " + i);

            }
        }).start();

        new Thread(() -> {
            while (true) {
                if (obj.size() == 5) {
                    break;
                }
            }
            System.out.println("list.size " + obj.size());
        }).start();
    }
}
