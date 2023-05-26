package com.alejandro.algorithmexercises;

import java.util.*;
import java.util.stream.Collectors;

public class FourNumberSum {

    public static void main(String[] args){
        int[] list = new int[]{7,6,4,-1,1,2};
        int targetSum = 16;
        for (Integer[] re : threeNumberSum(list, targetSum)) {
            System.out.println(Arrays.toString(re));
        }
    }
    public static List<Integer[]> threeNumberSum(int[] array, int targetSum) {
            Arrays.sort(array);
            List<Integer[]> result = new ArrayList<>();
            int b = 0;
            int i = 1;
            int l = 2;
            int r = array.length-1;
            int sum = 0;

            while (i < array.length -3){
                sum = array[b]+array[i]+array[l]+array[r];
                if (sum < targetSum){
                    b=b+1;
                    l=l+1;
                } else if (sum > targetSum){
                    r=r-1;
                } else {
                    result.add(new Integer[]{array[b], array[i], array[l], array[r]});
                    b=b+1;
                    l=i+1;
                    r=r-1;
                }
                if (l>=r) {
                    i++;
                    b=b+1;
                    l=i+1;
                    r=array.length-1;
                }

            for (Integer[] re : result) {
                System.out.println(Arrays.toString(re));
            }
        }
            return result;
    }
}