package com.alejandro.algorithmexercises;

import java.util.Arrays;
//Write a function that takes in a non-empty array of distinct integers and an integer representing a target sum.
//        If any two numbers in the input array sum up to the target sum, the function should return them in an array, in any order.
//        If no two numbers sum up to the target sum, the function should return an empty array.
//        Note that the target sum has to be obtained by summing two different integers in the array;
//        you cant add a single integer to itself in order to obtain the target sum.
//
//        You can assume that there will be at most one pair of numbers summing up to the target sum.
public class TwoNumberSum {
    public static void main(String[] args){
        int[] arraySum = new int[]{3,5,-4,8,11,1,-1,6};
        Arrays.sort(arraySum);
        System.out.printf("TwoNumberSum = " + Arrays.toString(twoNumberSum(arraySum, 10)));
    }
    public static int[] twoNumberSum(int[] array, int targetSum) {
        for (int i = 0; i < array.length-1; i++){
            for (int k = i+1; k < array.length; k++){
                if (i == k){
                    break;
                }
                if (array[i] + array[k] == targetSum){
                    return new int[]{array[i], array[k]};
                }
            }
        }
        return new int[0];
    }
}