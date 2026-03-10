package com.alejandro.algorithmexercises;

import java.util.*;

public class SmallestDifference {

//    Write a function that takes in two non-empty arrays of integers, finds the pair of numbers (one from each array)
//    whose absolute difference is closest to zero, and returns an array containing these two numbers, with the number from the first array in the first position.
//
//    Note that the absolute difference of two integers is the distance between them on the real number line.
//    For example, the absolute difference of -5 and 5 is 10, and the absolute difference of -5 and -4 is 1.
//
//    You can assume that there will only be one pair of numbers with the smallest difference.
//
//    arrayOne = [-1,5,10,20,28,3]
//    arrayTwo = [26,134,135,15,17]

    public static void main(String[] args) {
        int[] arrayOne = new int[]{-124, -1,5,10,20,28,3};
        int[] arrayTwo = new int[]{124, 26,134,135,15,17};
        System.out.println(Arrays.toString(smallestDifference(arrayOne, arrayTwo)));
    }
    public static int[] smallestDifference(int[] arrayOne, int[] arrayTwo){

        int smallestDifference = Math.abs(arrayOne[0] - arrayTwo[0]);
        int[] pairOfNumbers = new int[]{arrayOne[0], arrayTwo[0]};

        for (int i = 0; i < arrayOne.length; i++){
            for (int e = 0; e < arrayTwo.length; e++){
                int distance = Math.abs(arrayOne[i] - arrayTwo[e]);
                if (distance < smallestDifference){
                    smallestDifference = distance;
                    pairOfNumbers[0] = arrayOne[i];
                    pairOfNumbers[1] = arrayTwo[e];
                }
            }
        }
        return pairOfNumbers;
    }
}
