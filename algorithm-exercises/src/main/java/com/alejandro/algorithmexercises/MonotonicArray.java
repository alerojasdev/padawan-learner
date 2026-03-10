package com.alejandro.algorithmexercises;

public class MonotonicArray {
    public static void main(String[] args){
        int[] array = new int[]{-1,-5,-10,-1100,-110,-1101,-1102,-9001};

        System.out.println(monotonicArray(array));

    }
    public static boolean monotonicArray(int[] arr){

        boolean isIncreasing = false;
        boolean isDecreasing = false;

        for (int i = 0; i < arr.length-1;i++){

            if (arr[i] < arr[i + 1]){
                isIncreasing = true;
            }

            if (arr[i] > arr[i + 1]){
                isDecreasing = true;
            }

            if (isIncreasing && isDecreasing){
                return false;
            }

        }
        return true;
    }
}














