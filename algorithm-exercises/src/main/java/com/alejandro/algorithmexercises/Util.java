package com.alejandro.algorithmexercises;

import java.util.List;

public class Util {
    public static void swap(List<Integer> arr, int a, int b){
        int v1 = arr.get(a);
        int v2 = arr.get(b);
        arr.set(a, v2);
        arr.set(b, v1);
    }
    public static void swap(int[] arr, int a, int b){
        int v1 = arr[a];
        int v2 = arr[b];
        arr[a] = v2;
        arr[b] = v1;
    }
}
