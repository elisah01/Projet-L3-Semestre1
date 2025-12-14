package com.brasilburger.core.utils;

import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);

    public static String readString(String message) {
        System.out.print(message + ": ");
        return scanner.nextLine();
    }

    public static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message + ": ");
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Erreur : Veuillez entrer un nombre valide (ex: 12.50).");
            }
        }
    }

    public static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message + ": ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Erreur : Veuillez entrer un entier valide.");
            }
        }
    }
    
    // Pour faire une pause avant de réafficher le menu
    public static void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }
}