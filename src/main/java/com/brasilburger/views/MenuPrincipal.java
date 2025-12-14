package com.brasilburger.views;

import com.brasilburger.core.utils.Input;

public class MenuPrincipal {

    public void afficher() {
        int choix;
        do {
            System.out.println("=== BRASIL BURGER ===");
            System.out.println("1. Gérer Burgers");
            System.out.println("2. Gérer Menus");
            System.out.println("3. Gérer Compléments");
            System.out.println("4. Gérer Clients");
            System.out.println("5. Commandes");
            System.out.println("6. Paiements");
            System.out.println("7. Livraison & Zones");
            System.out.println("0. Quitter");
            choix = Input.readInt("Choix: ");
            switch (choix) {
                case 1 -> new BurgerView().menu();
                case 2 -> new MenuView().menu();
                case 3 -> new ComplementView().menu();
                case 4 -> new ClientView().menu();
                case 5 -> new CommandeView().menu();
                case 6 -> new PaiementView().menu();
                case 7 -> new LivraisonView().menu();
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 0);
    }
}
