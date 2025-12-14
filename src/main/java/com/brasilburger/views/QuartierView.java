package com.brasilburger.views;

import com.brasilburger.core.utils.Input;
import com.brasilburger.entities.Quartier;
import com.brasilburger.services.IQuartierService;
import com.brasilburger.services.IZoneService;
import com.brasilburger.services.impl.QuartierServiceImpl;
import com.brasilburger.services.impl.ZoneServiceImpl;

import java.util.List;

public class QuartierView {

    private final IQuartierService service = new QuartierServiceImpl();
    private final IZoneService zoneService = new ZoneServiceImpl();
    public void menu() {
        int c;
        do {
            System.out.println("=== Quartiers ===");
            System.out.println("1. Ajouter quartier");
            System.out.println("2. Lister quartiers par zone");
            System.out.println("0. Retour");
            c = Input.readInt("Choix: ");
            switch (c) {
                case 1 -> create();
                case 2 -> listByZone();
            }
        } while (c != 0);
    }

    private void create() {
        String nom = Input.readString("Nom quartier: ");
        long zoneId = Input.readInt("Zone id: ");
        Quartier q = new Quartier();
        q.setNom(nom);
        q.setZoneId(zoneId);
        service.create(q);
        System.out.println("Quartier créé id=" + q.getId());
    }

    private void listByZone() {
        long zid = Input.readInt("Zone id: ");
        List<Quartier> list = service.listByZone(zid);
        for (Quartier q : list) {
            System.out.printf("%d - %s%n", q.getId(), q.getNom());
        }
    }
}
