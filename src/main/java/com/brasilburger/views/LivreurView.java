package com.brasilburger.views;

import com.brasilburger.core.utils.Input;
import com.brasilburger.entities.Livreur;
import com.brasilburger.services.ILivreurService;
import com.brasilburger.services.impl.LivreurServiceImpl;

import java.util.List;

public class LivreurView {

    private final ILivreurService service = new LivreurServiceImpl();

    public void menu() {
        int c;
        do {
            System.out.println("=== Livreurs ===");
            System.out.println("1. Créer livreur");
            System.out.println("2. Lister livreurs");
            System.out.println("0. Retour");
            c = Input.readInt("Choix: ");
            switch (c) {
                case 1 -> create();
                case 2 -> list();
            }
        } while (c != 0);
    }

    private void create() {
        String nom = Input.readString("Nom: ");
        String prenom = Input.readString("Prenom: ");
        String tel = Input.readString("Téléphone: ");
        Livreur l = new Livreur();
        l.setNom(nom);
        l.setPrenom(prenom);
        l.setTelephone(tel);
        service.create(l);
        System.out.println("Livreur créé id=" + l.getId());
    }

    private void list() {
        List<Livreur> list = service.list();
        for (Livreur l : list) {
            System.out.printf("%d - %s %s - %s%n", l.getId(), l.getNom(), l.getPrenom(), l.getTelephone());
        }
    }
}
