package com.alejandro.algorithmexercises;

public class PrimeNumber {

    public static void main(String[] args){
        int p = 0;

        while ((p=pn(p))<100) {
            System.out.println("p: "+p);
        }
    }
    public static int pn(int p){ // Deivi Solution
        boolean foundExtraDivisor;
        do {
            foundExtraDivisor = false;
            p++;
            for (int i = 2;i <= p-1;i++){
                if (p%i==0){
                    foundExtraDivisor = true;
                    break;
                }
            }
        } while (foundExtraDivisor);
        return p;
    }
    public static int pn1(int p){ // Ale Solution

//        while (true){
            p++;
            for (int i = 2;i <= p-1;i++){
//                if (p%i==1){
//                    continue;
//                }
                if (p%i==0){
                    p++;
                    i=2;
                }
            }
            return p;
//        }
    }
}
