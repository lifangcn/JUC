package indi.michael.jucex020;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] ints = solution.distributeCandies(60, 4);
        for (int anInt : ints) {
            System.out.print(anInt + " \t");
        }
    }

    List<Integer> result = new ArrayList<>();
    int index = 0;// 当前分到第几个
    int count = 1;// 当前分几个糖果


    public int[] distributeCandies(int candies, int num_people) {
        //
        if (result.size() <= index) {
            for (int i = 0; i < num_people; i++) {
                result.add(0);
            }
        }
        if (candies <= count) {

            Integer integer = result.get(index);
            integer += candies;
            result.set(index, integer);
            return result.stream().mapToInt(Integer::valueOf).toArray();
        }

        Integer integer = result.get(index);
        integer += count;
        result.set(index, integer);
        int decr = candies - count;

        // 分到最后一个时，归零
        count++;
        index++;
        if (index == num_people) {
            index = 0;
        }
        return distributeCandies(decr, num_people);
    }
}