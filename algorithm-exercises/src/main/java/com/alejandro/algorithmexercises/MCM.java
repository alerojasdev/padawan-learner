package com.alejandro.algorithmexercises;

public class MCM {
    public static void main(String[] args){
        System.out.println(mcm1(123,432));
    }
    public static int mcm(int a, int b){
        int sumA=0;
        while (true){
            sumA=sumA+a;
            if ((sumA/b)==a){
                return sumA/b;
            }
        }
    }
    public static int mcm1(int a, int b){
        int product = a*b;
        int i=2;
        while (true){
            if ((i*MCD.mcd(a, b)) == product){
                return i;
            }
            i++;
        }
    }
}
