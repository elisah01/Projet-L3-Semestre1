package com.brasilburger.views;

import com.brasilburger.core.utils.Input;
import com.brasilburger.entities.Menu;
import com.brasilburger.services.IMenuService;
import com.brasilburger.services.impl.MenuServiceImpl;

import java.util.List;

public class MenuView {
    private final IMenuService service = new MenuServiceImpl();

    public void menu() {
        int c;
        do {
            System.out.println("=== Menus ===");
            System.out.println("1. Créer menu");
            System.out.println("2. Lister menus");
            System.out.println("3. Archiver menu");
            System.out.println("4. Ajouter burger à menu");
            System.out.println("5. Ajouter complément à menu");
            System.out.println("0. Retour");
            c = Input.readInt("Choix: ");
            switch (c) {
                case 1 -> create();
                case 2 -> list();
                case 3 -> archive();
                case 4 -> addBurger();
                case 5 -> addComplement();
                case 0 -> {}
                default -> System.out.println("Choix invalide");
            }
        } while (c != 0);
    }

    private void create() {
        String nom = Input.readString("Nom: ");
        String url = Input.readString("Image URL: ");
        Menu m = new Menu();
        m.setNom(nom);
        m.setImageUrl(url);
        service.create(m);
        System.out.println("Menu créé");
    }

    private void list() {
        List<Menu> menus = service.list();
        System.out.println("=== Menus ===");
        for (Menu m : menus) {
            System.out.printf("%d - %s - %s%n", m.getId(), m.getNom(), m.getImageUrl());
        }
    }

    private void archive() {
        long id = Input.readInt("Id menu: ");
        service.archive(id);
        System.out.println("Menu archivé");
    }

    private void addBurger() {
        long menuId = Input.readInt("Menu Id: ");
        long burgerId = Input.readInt("Burger Id: ");
        service.addBurger(menuId, burgerId);
        System.out.println("Burger ajouté au menu");
    }

    private void addComplement() {
        long menuId = Input.readInt("Menu Id: ");
        long compId = Input.readInt("Complement Id: ");
        service.addComplement(menuId, compId);
        System.out.println("Complément ajouté au menu");
    }
}
