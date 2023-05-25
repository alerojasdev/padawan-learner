package com.alejandro.algorithmexercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Given two non-empty arrays of integers, write a function that determines whether the second array is subsequence of the first one.
//
//        A subsequence of an array is a set of numbers that aren't necessarily adjacent in the array but that are in the same order as they appear in the array.
//        For instance, the numbers [1, 3, 4] form a subsequence of the array [1, 2, 3, 4], and so do the numbers [2, 4].
//        Note that a single number in the array and the array itself are both valid subsequence of the array.
public class ValidateSubsequence {
    public static void main(String[] args){
        List<Integer> array = Arrays.asList(5,1,22,25,6,-1,8,10);
        List<Integer> subsequence = Arrays.asList(1,6,-1,10);
        System.out.printf("The given array is subsequence of the original array ??? = " +isValidSubsequence(array, subsequence));
    }
    public static boolean isValidSubsequence(List<Integer> array, List<Integer> sequence) {
        ArrayList<Integer> comparator = new ArrayList<Integer>();
        int k = 0;
        for (int i = 0; i < sequence.size(); i++) {
            for (; k < array.size();) {
                if (sequence.get(i).equals(array.get(k++))) {
                    comparator.add(sequence.get(i));
                    break;
                }
            }
        }
        return sequence.size() == comparator.size();
    }
}