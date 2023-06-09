package com.alejandro.algorithmexercises;

public class MCD {
    public static void main(String[] args){
        System.out.println(mcd(143,234));
    }
    public static int mcd(int a, int b){
        int temp=1;
        for (int i = 2; i <= b;){
            if (a%i==0 && b%i==0){
                temp=temp*i;        //
                a=a/i;              // or just => temp=i;
                b=b/i;              //
            }else {
                i++;
            }
        }
        return temp;
    }
}
