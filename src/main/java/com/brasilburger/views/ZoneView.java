package com.brasilburger.views;

import com.brasilburger.core.utils.Input;
import com.brasilburger.entities.Zone;
import com.brasilburger.services.IZoneService;
import com.brasilburger.services.impl.ZoneServiceImpl;

import java.util.List;

public class ZoneView {
    private final IZoneService service = new ZoneServiceImpl();

    public void menu() {
        int c;
        do {
            System.out.println("=== Zones ===");
            System.out.println("1. Créer zone");
            System.out.println("2. Lister zones");
            System.out.println("0. Retour");
            c = Input.readInt("Choix: ");
            switch (c) {
                case 1 -> create();
                case 2 -> list();
            }
        } while (c != 0);
    }

    private void create() {
        String nom = Input.readString("Nom zone: ");
        double prix = Input.readDouble("Prix livraison: ");
        Zone z = new Zone();
        z.setNom(nom);
        z.setPrix(prix);
        service.create(z);
        System.out.println("Zone créée id=" + z.getId());
    }

    private void list() {
        List<Zone> zones = service.list();
        for (Zone z : zones) {
            System.out.printf("%d - %s - %.2f%n", z.getId(), z.getNom(), z.getPrix());
        }
    }
}
