package com.alejandro.algorithmexercises;

import java.util.Arrays;
// Array of Products
// Write a function that takes in a non-empty array of integers and returns an array of the same length,
// where each element in the output array is equal to the product of every other number in the input array.

// In other words, the value at output[i] is equal to the product of every number in the input array other
// than input[i].

// Note that you're expected to solve this problem without using division.

public class ArrayOfProducts {
    public static void main(String[] args){

        int[] arr = new int[]{5,1,4,2};
        System.out.println(Arrays.toString(arrayOfProducts(arr)));

    }
    public static int[] arrayOfProducts(int [] arr){
        int[] newArr = new int[arr.length];

        for (int i = 0; i < arr.length; i++){
            int products=1;
            for (int k = 0; k < arr.length; k++){

                if (i != k) {
                    products=products*arr[k];
                }
            }
            newArr[i] = products;
            products=1;
        }
        return newArr;
    }
}