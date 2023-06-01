package com.alejandro.algorithmexercises;

import java.util.Arrays;

import static com.alejandro.algorithmexercises.Util.swap;

public class OrderArray {

    public static void main(String[] args){
        int[] arr = new int[]{3,6,10,5,4};
//        bubbleSort(arr);
        aleSort(arr);
    }

    public static void aleSort(int[] arr) {
        for (int i = 0; i < arr.length-1; i++){
            int vli = i;
            for (int k = i+1; k < arr.length; k++) {
                if (arr[k] < arr[vli]){
                    vli = k;
                }
            }
            if (i != vli){
                swap(arr, vli, i);
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void bubbleSort(int[] arr) {
        boolean huvoSwap = false;
        do {
            huvoSwap = false;

            for (int i = 0; i < arr.length-1; i++){
                if (arr[i] > arr[i+1]){
                    swap(arr, i, i+1);
                    huvoSwap=true;
                }
            }

        } while (huvoSwap==true);

        System.out.println(Arrays.toString(arr));
    }


}
