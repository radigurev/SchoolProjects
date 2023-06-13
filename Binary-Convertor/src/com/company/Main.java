package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static String num;

    //Парсиране на кода от една бройна система в друга
    public static String parseBinary(int binaryForm, int newBinaryForm) {
        if (binaryForm != 10)
            num = Integer.toString(multiply(binaryForm));

        return division(newBinaryForm);

    }
    //Превръщане от буква в число
    public static int checkForLetters(String num) {
        int number;
        switch (num) {
            case "A" -> number = 10;
            case "B" -> number = 11;
            case "C" -> number = 12;
            case "D" -> number = 13;
            case "E" -> number = 14;
            case "F" -> number = 15;
            default -> number = Integer.parseInt(num);
        }
        return number;
    }
    //Променяме дадената бройна система в десетична бройна система
    public static int multiply(int binary) {
        String[] number = num.split("");
        int finalResult = 0;
        for (int i = 0; i < number.length; i++) {
            finalResult += (int) (checkForLetters(number[i]) * Math.pow(binary, number.length - 1 - i));

        }
        return finalResult;
    }
    //Променяме от десетична в дадената бройна система
    public static String division(int binary) {
        StringBuilder result = new StringBuilder();
        int number = Integer.parseInt(num);
        while (number > 0) {
            int digit = number % binary;
            switch (digit) {
                case 10 -> result.append("A");
                case 11 -> result.append("B");
                case 12 -> result.append("C");
                case 13 -> result.append("D");
                case 14 -> result.append("E");
                case 15 -> result.append("F");
                default -> result.append(digit);
            }
            number /= binary;
        }
        return result.reverse().toString();
    }

    public static void main(String[] args) {
        Scanner manqk = new Scanner(System.in);
        System.out.println("Range is from 2->16");
        System.out.println("Binary form:");
        int binaryForm = Integer.parseInt(manqk.nextLine());
        System.out.println("Parse it to:");
        int newBinaryForm = Integer.parseInt(manqk.nextLine());
        System.out.println("Enter number to parse:");
        num = manqk.nextLine();
        System.out.printf("Final result: %s", parseBinary(binaryForm, newBinaryForm));
    }
}
