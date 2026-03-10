package com.alejandro.algorithmexercises;

import java.util.Arrays;

public class SortedSquaredArray {
//    Write a function that takes in a non-empty array of integers that are sorted in ascending order
//    and returns a new array of the same length with the squares of the original integers also sorted in ascending order.

    public static void main(String[] args){
        System.out.println("The Squared Array is = " + Arrays.toString(new int[]{}));
        System.out.println("The Squared Array is = " + Arrays.toString(sortedSquaredArray(new int[]{1,2,3,5,6,8,9})));
    }
    public static int[] sortedSquaredArray(int[] array) {
        for (int i = 0; i < array.length; i++){
            array[i] = array[i] * array[i];
        }
        Arrays.sort(array);
        return array;
    }
}
