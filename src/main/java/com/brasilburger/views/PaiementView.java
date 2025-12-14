package com.brasilburger.views;

import com.brasilburger.core.utils.Input;
import com.brasilburger.entities.Paiement;
import com.brasilburger.entities.enums.ModePaiement;
import com.brasilburger.services.IPaiementService;
import com.brasilburger.services.impl.PaiementServiceImpl;

import java.util.Optional;

public class PaiementView {

    private final IPaiementService service = new PaiementServiceImpl();

    public void menu() {
        int c;
        do {
            System.out.println("=== Paiements ===");
            System.out.println("1. Enregistrer paiement");
            System.out.println("2. Rechercher paiement par commande");
            System.out.println("0. Retour");
            c = Input.readInt("Choix: ");
            switch (c) {
                case 1 -> create();
                case 2 -> find();
                case 0 -> {}
                default -> System.out.println("Choix invalide");
            }
        } while (c != 0);
    }

    private void create() {
        long commandeId = Input.readInt("Commande Id: ");
        double montant = Input.readDouble("Montant: ");
        String m = Input.readString("Mode (WAVE/ORANGE_MONEY): ");
        Paiement p = new Paiement();
        p.setCommandeId(commandeId);
        p.setMontant(montant);
        p.setMode(ModePaiement.valueOf(m));
        service.create(p);
        System.out.println("Paiement enregistré");
    }

    private void find() {
        long commandeId = Input.readInt("Commande Id: ");
        Optional<Paiement> p = service.findByCommande(commandeId);
        if (p.isPresent()) {
            System.out.printf("Paiement %d - montant=%.2f - mode=%s%n", p.get().getId(), p.get().getMontant(), p.get().getMode());
        } else {
            System.out.println("Aucun paiement trouvé");
        }
    }
}
