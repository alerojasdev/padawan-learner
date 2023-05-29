package com.alejandro.algorithmexercises;

//  Write a function that takes in an n x m two-dimensional array (that can be
//          square-shaped when n == m) and returns a one-dimensional array of all the
//          array's elements in spiral order.
//         array = [
//         [1,   2,  3, 4],
//         [12, 13, 14, 5],
//         [11, 16, 15, 6],
//         [10,  9,  8, 7],
//         ]


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpiralTraverse {

    public static void main(String[] args){
        int[][]
                 array = {
{1, 2, 3, 4},
{10, 11, 12, 5},
{9, 8, 7, 6}
                };

        System.out.println(spiralTraverse(array));
    }
    public static List<Integer> spiralTraverse(int[][] arr) {
        List<Integer> flatArr = new ArrayList<>();

        int inicioFila  = 0;
        int finFila     = arr.length-1;
        int inicioCol   = 0;
        int finCol      = arr[0].length-1;
        int itemCount   = (arr.length == 0 ? 1 : arr.length) * (arr[0].length == 0 ? 1 : arr[0].length);
        System.out.println("arraySize: "+ itemCount);
        while (itemCount > 0) {
            for (int i = inicioCol; i <= finCol && itemCount>0; i++) {// izquierda -> derecha
                flatArr.add(arr[inicioFila][i]); itemCount--;
            }
            inicioFila++;
            for (int i = inicioFila; i <= finFila && itemCount>0; i++) {// arriba -> abajo
                flatArr.add(arr[i][finCol]); itemCount--;
            }
            finCol--;
            for (int i = finCol; i >= inicioCol && itemCount>0; i--) {// derecha -> izquierda
                flatArr.add(arr[finFila][i]); itemCount--;
            }
            finFila--;
            for (int i = finFila; i >= inicioFila && itemCount>0; i--) {// abajo --> arriba
                flatArr.add(arr[i][inicioCol]); itemCount--;
            }
            inicioCol++;
        }

        return flatArr;
    }
}

