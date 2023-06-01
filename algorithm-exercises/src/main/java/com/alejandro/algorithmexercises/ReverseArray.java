package com.alejandro.algorithmexercises;


import java.util.Arrays;

import static com.alejandro.algorithmexercises.Util.swap;

public class ReverseArray {
    public static void main(String[] args){

        int[] arr = new int[]{3,4,5,6,7};

        //1
        int k = arr.length-1;
        for (int i = 0; i < arr.length/2; i++){
            swap(arr, i, k);
            k--;
        }
        System.out.println(Arrays.toString(arr));
        //2
        int m =arr.length-1;
        for (int n = 0; n < arr.length ; n++) {
            if (m > n){
                swap(arr, n, m);
                --m;
            }
        }
        System.out.println(Arrays.toString(arr));
        //3
        int h = arr.length-1;
        int j = 0;
        while (h > j){
            swap(arr, h, j);
            --h;
            ++j;
        }
        System.out.println(Arrays.toString(arr));
    }
}

