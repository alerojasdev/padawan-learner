package com.alejandro.algorithmexercises;

public class IsPalindrome {
    public static void main(String[] args){
        int[] arr = new int[]{5,1,2,2,1,5};
        System.out.println(isPalindrome(arr));
    }
    public static String isPalindrome(int[] arr){
        int last = arr.length-1;
        for (int i = 0; i < arr.length;){
            if (arr[i] == arr[last]){
                i++;
                last--;
            }else {
                break;
            }
            if (last < i){
                return "The array is palindrome";
            }
        }
        return "Is not a palindrome";
    }
}
