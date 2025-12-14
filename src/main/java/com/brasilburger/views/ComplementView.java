package com.brasilburger.views;

import com.brasilburger.core.utils.Input;
import com.brasilburger.entities.Complement;
import com.brasilburger.services.IComplementService;
import com.brasilburger.services.impl.ComplementServiceImpl;

import java.util.List;

public class ComplementView {
    private final IComplementService service = new ComplementServiceImpl();

    public void menu() {
        int c;
        do {
            System.out.println("=== Compléments ===");
            System.out.println("1. Créer complément");
            System.out.println("2. Lister compléments");
            System.out.println("3. Archiver complément");
            System.out.println("0. Retour");
            c = Input.readInt("Choix: ");
            switch (c) {
                case 1 -> create();
                case 2 -> list();
                case 3 -> archive();
                case 0 -> {}
                default -> System.out.println("Choix invalide");
            }
        } while (c != 0);
    }

    private void create() {
        String nom = Input.readString("Nom: ");
        double prix = Input.readDouble("Prix: ");
        String url = Input.readString("Image URL: ");
        Complement comp = new Complement();
        comp.setNom(nom);
        comp.setPrix(prix);
        comp.setImageUrl(url);
        service.create(comp);
        System.out.println("Complément créé");
    }

    private void list() {
        List<Complement> list = service.list();
        System.out.println("=== Liste Compléments ===");
        for (Complement c : list) {
            System.out.printf("%d - %s - %.2f%n", c.getId(), c.getNom(), c.getPrix());
        }
    }

    private void archive() {
        long id = Input.readInt("Id à archiver: ");
        service.archive(id);
        System.out.println("Archivage OK");
    }
}
