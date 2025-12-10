package com.chefai.util;
import java.util.Scanner;
public class InputHelper{
    private static final Scanner sc = new Scanner(System.in);
    public static String lerLinha(String m) {
        System.out.print(m);return sc.nextLine();
    }
    public static double lerDouble(String m) {
        System.out.print(m);return Double.parseDouble(sc.nextLine());
    }
}