package com.alejandro.algorithmexercises;

public class IsSorted {
    public static void main(String[] args){
        int[] arr = new int[]{1,2,3,5,6,7,80,90,};

        System.out.println(isSorted(arr));
    }
    public static boolean isSorted(int[] arr){
        for (int i = 0; i < arr.length-1; i ++){
            if (arr[i] > arr[i+1]){
                return false;
            }
        }
        return true;
    }
}
