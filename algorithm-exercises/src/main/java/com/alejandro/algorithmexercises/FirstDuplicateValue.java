package com.alejandro.algorithmexercises;

import java.util.HashSet;
import java.util.Set;

//First Duplicate Value
//
//        Given an array of integers between 1 and n, inclusive,
//        where n is the length of the array,
//        write a function that returns the first integer that appears more than once
//        (when the array is read from the left to right).
//        In other words, out of all the integers that might occur more than once in the input array,
//        your function should return the one whose first duplicate value has the minimum index.
//        If no integer appears more than once, your function should return-1.
//        Note that you're allowed to mutate the input array.
public class FirstDuplicateValue {

    public static void main(String[] args){
        int[] arr = new int[]{9,2,1,5,2,3,3,4};

        System.out.println(firstDuplicateValue(arr));
        System.out.println(firstDuplicateValue1(arr));

    }
    public static int firstDuplicateValue(int[] arr){
        Set<Integer> seeing = new HashSet<>();

        for (int j : arr) {
            if (seeing.contains(j)) {
                return j;
            }
            seeing.add(j);
        }
        return -1;
    }
    public static int firstDuplicateValue1(int[] arr){
        for (int i = 0; i < arr.length; i++){
            for (int k = i + 1; k < arr.length; k++){
                if (arr[i] == arr[k]){
                    return arr[i];
                }
            }
        }
        return -1;
    }

}
