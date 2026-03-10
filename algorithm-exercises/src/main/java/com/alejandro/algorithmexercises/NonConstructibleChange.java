package com.alejandro.algorithmexercises;

import java.util.Arrays;

public class NonConstructibleChange {

//    Given an array of positive integers representing the values of coins in your possession,
//    write a function that returns the minimum amount of change (the minimum sum of money) that you CANNOT create.
//    The given coins can have any positive integer value and arent necessarily unique(i.e., you can have multiple coins of the same value).
//
//    For example, if youre given coins = [1,2,5], the minimum amount of change that you cant create is 4.
//    If you're given no coins, the minimum amount of change that you cant create is 1.
    public static void main(String[] args){
        System.out.println(Integer.toString(nonConstructibleChange(new int[]{5,7,1,1,2,3,22})));
    }
    public static int nonConstructibleChange(int[] coins) {
        Arrays.sort(coins);
        int sum = 0;
        for(int i = 0; i < coins.length; i++){

            if(coins[i]> sum+1 ){
                return sum+1;
            }
            sum = sum + coins[i];
        }
        return sum + 1;
    }
}
