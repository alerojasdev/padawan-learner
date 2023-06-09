package com.alejandro.algorithmexercises;

public class PrimeNumber {

    public static void main(String[] args){
        System.out.println(pn(43));
    }
    public static int pn(int p){
        p++;
        while (true){
            for (int i = 2;i <= p-1;i++){
                if (p%i==1){
                    continue;
                }
                if (p%i==0){
                    p++;
                    i=2;
                }
            }
            return p;
        }
    }
}
