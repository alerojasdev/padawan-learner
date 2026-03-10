package com.alejandro.algorithmexercises;

import java.util.Iterator;
import java.util.TreeMap;

public class RomanNumeralsInterpreter {
    public static void main(String[] args){
        int exampleInt = 356;
        System.out.println(romanNumerals(exampleInt));

        System.out.println("how many numbers are in the input? = " + Integer.toString(exampleInt).length());
    }
    public static String romanNumerals(int number){

        TreeMap<Integer, String> representations = new TreeMap<>(); //
        representations.put(1, "I");
        representations.put(2, "II");
        representations.put(3, "III");
        representations.put(4, "IV");
        representations.put(5, "V");
        representations.put(6, "VI");
        representations.put(7, "VII");
        representations.put(8, "VIII");
        representations.put(9, "IX");
        representations.put(10, "X");
        representations.put(20, "XX");
        representations.put(30, "XXX");
        representations.put(40, "XL");
        representations.put(50, "L");
        representations.put(60, "LX");
        representations.put(70, "LXX");
        representations.put(80, "LXXX");
        representations.put(90, "LC");
        representations.put(100, "C");
        representations.put(200, "CC");
        representations.put(300, "CCC");
        representations.put(400, "CD");
        representations.put(500, "D");
        representations.put(600, "DC");
        representations.put(700, "DCC");
        representations.put(800, "DCCC");
        representations.put(900, "DM");
        representations.put(1000, "M");

//            if (representations.containsKey(number)){
//                return representations.get(number);
//        }

        Iterator<Integer> it = representations.descendingKeySet().iterator();

        StringBuilder returnNumbers = new StringBuilder();
        int result;

        while (number > it.next()) {
            System.out.printf(String.valueOf(it.next()));
        }
        return "";
    }
}
