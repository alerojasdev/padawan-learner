package com.alejandro.algorithmexercises;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FirstElementNotRepeated {

    public static void main(String[] args) {
        int[] arr = new int[]{7,2,5,7,2,8,0};
        System.out.println(firstElement1(arr));
    }
    public static int firstElement(int[] arr){
        boolean isNotRepeated;
        for (int i = 0; i < arr.length; i++){
            isNotRepeated = false;
            for (int k = i+1; k < arr.length; k++){
                if (arr[i] == arr[k]){
                    isNotRepeated = true;
                    break;
                }
            }
            if (!isNotRepeated){
                return arr[i];
            }
        }
        return -1;
    }
    public static int firstElement1(int[] arr){
        Map<Integer, Integer> arrVal_ocurrences = new LinkedHashMap<>(); // order de insercion presevado, re-insercion no afecta la posicion original
        for (int i = 0; i < arr.length; i++){
            if (!arrVal_ocurrences.containsKey(arr[i])){ // preguntamos si aun no vimos este valor en el array
                arrVal_ocurrences.put(arr[i], 0); // agregamos ese valor como key, y 0 como contador
            }
            int count = arrVal_ocurrences.get(arr[i]); // obtenemos el contador actual de cuantas veces vimos este valor del array
            arrVal_ocurrences.put(arr[i], count+1);
        }
        for (Map.Entry<Integer, Integer> entry : arrVal_ocurrences.entrySet()) // listamos los entries // Ojo, esto devuelve en order de insercion
            if (entry.getValue() == 1) // si este valor se vio solo una vez... debe ser el primero
                return entry.getKey();
        return -1;
    }
}
