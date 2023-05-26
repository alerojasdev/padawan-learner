package com.alejandro.algorithmexercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//You're given an array of integers and an integer.
//        Write a function that moves all instances of that integer in the array to the end of the array and returns the array.
//
//        The function should perform this in place(i.e.
//        it should mutate the input array) and doesn't need to maintain the order of the other integers.
//        List<Integer> array = new ArrayList<>(2,1,2,2,2,3,4,2);

public class MoveElementToTheEnd {

    public static void main(String[] args){
        int toMove = 2;
        List<Integer> array = Arrays.asList(2,1,2,2,2,3,4,2);
        System.out.println(moveElementToEnd(array, toMove));
    }
    public static List<Integer> moveElementToEnd(List<Integer> array, int toMove) {
        int izquierda=0;
        int derecha= array.size()-1;
        while (izquierda<derecha){
            if (array.get(derecha) == toMove) {
                derecha--;
                continue;
            }
            if (array.get(izquierda) == toMove) {
                swap(array, izquierda, derecha);
                derecha--;
            }
            izquierda++;
        }
        return new ArrayList<>(array);
    }
    private static void swap(List<Integer> array, int primero, int ultimo) {
        int val1 = array.get(primero);
        int val2 = array.get(ultimo);
        array.set(primero, val2);
        array.set(ultimo, val1);
    }
 }
