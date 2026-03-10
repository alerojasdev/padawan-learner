package com.alejandro.algorithmexercises;

import java.util.Arrays;

public class OrderArrayByIndexValue {

    public static int[] buildArray(int[] nums) {
        int[] arr = new int[nums.length]; // new int[10]   arr.len == 10   arr[9]
        for (int i = 0; i < nums.length; i++){
            arr[i] = nums[nums[i]];
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] input = new int[]{5, 0, 1, 2, 3, 4};
        int[] input1 = new int[]{0,2,1,5,3,4};

        int[] answer = buildArray(input);
        int[] answer1 = buildArray(input1);

        System.out.printf("Answer: %s%n", Arrays.toString(answer));
        System.out.printf("Input: %s%n", Arrays.toString(answer1));

    }

}
