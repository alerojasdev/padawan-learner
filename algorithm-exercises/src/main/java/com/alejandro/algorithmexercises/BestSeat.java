package com.alejandro.algorithmexercises;

import java.util.*;

public class BestSeat {

    public static void main(String[] args) {
        int[] seats = new int[]{1,0,0,0,1,0,0,0,0,1};
        System.out.println(Arrays.toString(bestSeat(seats)));
    }
    public static int[] bestSeat(int[] seats){

        List<Integer> zerosAndIndex = new ArrayList<>();
        int k = 0;
        int zerosCounter = 0;
        int bestSeat = 0;
        int maxSeats = 0;

        for (int i = 1; i < seats.length; i++){
            if (seats[i] == 0){
                zerosCounter++;
                zerosAndIndex.add(zerosCounter);
            } else {
                zerosCounter = 0;
            }
        }

        for (zerosCounter)

    }
}
