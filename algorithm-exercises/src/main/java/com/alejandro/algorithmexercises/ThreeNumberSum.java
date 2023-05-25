package com.alejandro.algorithmexercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeNumberSum {
    public static List<Integer[]> threeNumberSum(int[] array, int targetSum) {
        Arrays.sort(array);
        List<Integer[]> result = new ArrayList<Integer[]>();
        int i =0;
        int l = 1;
        int r = array.length-1;
        int sum = 0;
        while (i < array.length -2 ){
            sum = array[i]+array[l]+array[r];
            if (sum < targetSum){
                l=l+1;
            } else if (sum > targetSum){
                r=r-1;
            } else {
                result.add(new Integer[]{array[i], array[l], array[r]});
                l=i+1;
                r=r-1;
            }
            if (l>=r) {
                i++;
                l=i+1;
                r=array.length-1;
            }
        }
        return result;
    }
}
