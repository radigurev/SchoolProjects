package com.company;

import java.util.*;

public class Main {

    public static String encode(String[] sentence, int k) {
        StringBuilder coded = new StringBuilder();
        for (String word :
                sentence) {
            for (int i = 0; i < word.length(); i++) {
                char letter = (char)((int)word.charAt(i)+k);
                if (letter == 'z') {
                    letter = (char) (97 + k - 1);
                } else if (letter == 'Z') {
                    letter = (char) (65 + k - 1);
                }else

                coded.append(letter);

            }
            coded.append(" ");
        }

        return encodeInNumber(coded.toString().substring(0,coded.length()-1));
    }

    public static String encodeInNumber(String sentence) {
        StringBuilder numbers = new StringBuilder();
        String finalEncodedSentence="";
        System.out.println();
        for (String letter :
                sentence.split("")) {
            char l=letter.charAt(0);
            int letterInAlphabetic = l;
            if (!letter.equals(" ")) {
                if (letterInAlphabetic > 64 && letterInAlphabetic < 91) {
                    letterInAlphabetic -= 64;
                } else
                    letterInAlphabetic -= 96;

                while (letterInAlphabetic>0){
                    numbers.append(letterInAlphabetic%2);
                    letterInAlphabetic/=2;
                }
                while (numbers.length()<5){
                    numbers.append("0");
                }
            }

            finalEncodedSentence+=numbers.reverse()+" ";
            numbers.delete(0,numbers.length());
        }

        return finalEncodedSentence;
    }

    public static String encodeK(int k){
        StringBuilder num=new StringBuilder();
        while (k>0){
            num.append(k%2);
            k/=2;
        }
        while (num.length()<5)
           num.append("0");

        return num.reverse().toString();
    }

    public static void main(String[] args) {
        Scanner manqk = new Scanner(System.in);
        System.out.println("Enter a sentence to encode:");
        String[] word = manqk.nextLine().split(" ");
        System.out.println("Enter k:");
        int k = Integer.parseInt(manqk.nextLine());
        System.out.printf("K: %s",encodeK(k));
        System.out.println(encode(word,k));
    }
}
