package com.brasilburger.views;

import com.brasilburger.core.utils.Input;
import com.brasilburger.entities.Burger;
import com.brasilburger.services.IBurgerService;
import com.brasilburger.services.impl.BurgerServiceImpl;

import java.util.List;

public class BurgerView {

    private final IBurgerService service = new BurgerServiceImpl();

    public void menu() {
        int c;
        do {
            System.out.println("=== Burgers ===");
            System.out.println("1. Créer burger");
            System.out.println("2. Lister burgers");
            System.out.println("3. Archiver burger");
            System.out.println("0. Retour");
            c = Input.readInt("Choix: ");
            switch (c) {
                case 1 -> create();
                case 2 -> list();
                case 3 -> archive();
                case 0 -> { }
                default -> System.out.println("Choix invalide");
            }
        } while (c != 0);
    }

    private void create() {
        String nom = Input.readString("Nom: ");
        double prix = Input.readDouble("Prix: ");
        String imageUrl = Input.readString("Image URL (Cloudinary): ");
        Burger b = new Burger();
        b.setNom(nom);
        b.setPrix(prix);
        b.setImageUrl(imageUrl);
        Burger saved = service.create(b);
        System.out.println("Burger créé Id=" + saved.getId());
    }

    private void list() {
        List<Burger> list = service.list();
        System.out.println("=== Liste des burgers ===");
        for (Burger b : list) {
            System.out.printf("%d - %s - %.2f - %s%n", b.getId(), b.getNom(), b.getPrix(), b.getImageUrl());
        }
    }

    private void archive() {
        long id = Input.readInt("Id du burger à archiver: ");
        service.archive(id);
        System.out.println("Burger archivé");
    }
}
