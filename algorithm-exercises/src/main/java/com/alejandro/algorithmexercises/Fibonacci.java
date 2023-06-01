package com.alejandro.algorithmexercises;

import java.math.BigInteger;

public class Fibonacci {

    public static void main(String[] args){
        fibonacci(50);
    }
    public static void fibonacci(int limit){

        BigInteger ant = new BigInteger("0");
        BigInteger curr = new BigInteger("1");
        BigInteger temp;
        while (limit-->0){
            System.out.println(curr);
            temp = curr;
            curr = curr.add(ant);
            ant = temp;
        }
    }
}