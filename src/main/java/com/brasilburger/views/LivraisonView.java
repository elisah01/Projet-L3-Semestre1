package com.brasilburger.views;

import com.brasilburger.core.utils.Input;
import com.brasilburger.entities.Livraison;
import com.brasilburger.services.ILivraisonService;
import com.brasilburger.services.impl.LivraisonServiceImpl;

import java.util.List;

public class LivraisonView {

    private final ILivraisonService service = new LivraisonServiceImpl();

    public void menu() {
        int c;
        do {
            System.out.println("=== Livraisons ===");
            System.out.println("1. Créer livraison");
            System.out.println("2. Lister livraisons");
            System.out.println("3. Ajouter commande à livraison");
            System.out.println("0. Retour");
            c = Input.readInt("Choix: ");
            switch (c) {
                case 1 -> create();
                case 2 -> list();
                case 3 -> addCommande();
            }
        } while (c != 0);
    }

    private void create() {
        Long livreurId = null;
        Long zoneId = null;
        String input = Input.readString("Livreur id (vide si non): ");
        if (!input.isBlank()) livreurId = Long.parseLong(input);
        input = Input.readString("Zone id (vide si non): ");
        if (!input.isBlank()) zoneId = Long.parseLong(input);

        Livraison l = new Livraison();
        l.setLivreurId(livreurId);
        l.setZoneId(zoneId);
        service.create(l);
        System.out.println("Livraison créée id=" + l.getId());
    }

    private void list() {
        List<Livraison> list = service.list();
        for (Livraison l : list) {
            System.out.printf("%d - date=%s - livreurId=%s - zoneId=%s%n", l.getId(), l.getDate(), l.getLivreurId(), l.getZoneId());
        }
    }

    private void addCommande() {
        long livraisonId = Input.readInt("Livraison id: ");
        long commandeId = Input.readInt("Commande id: ");
        service.addCommandeToLivraison(livraisonId, commandeId);
        System.out.println("Commande ajoutée à livraison");
    }
}
