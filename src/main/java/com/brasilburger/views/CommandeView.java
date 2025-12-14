package com.brasilburger.views;

import com.brasilburger.core.utils.Input;
import com.brasilburger.entities.*;
import com.brasilburger.services.ICommandeService;
import com.brasilburger.services.IClientService;
import com.brasilburger.services.impl.CommandeServiceImpl;
import com.brasilburger.services.impl.ClientServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandeView {

    private final ICommandeService commandeService = new CommandeServiceImpl();
    private final IClientService clientService = new ClientServiceImpl();

    public void menu() {
        int c;
        do {
            System.out.println("=== Commandes ===");
            System.out.println("1. Créer commande");
            System.out.println("2. Lister commandes");
            System.out.println("3. Mettre à jour état");
            System.out.println("0. Retour");
            c = Input.readInt("Choix: ");
            switch (c) {
                case 1 -> createCommande();
                case 2 -> list();
                case 3 -> updateEtat();
                case 0 -> {}
                default -> System.out.println("Choix invalide");
            }
        } while (c != 0);
    }

    private void createCommande() {
        String tel = Input.readString("Téléphone client (existant ou nouveau): ");
        Optional<Client> oc = clientService.findByTelephone(tel);
        Client client;
        if (oc.isEmpty()) {
            System.out.println("Client non trouvé. Création...");
            new ClientView().create();
            oc = clientService.findByTelephone(tel);
            if (oc.isEmpty()) {
                System.out.println("Échec création client. Abandon.");
                return;
            }
        }
        client = oc.get();
        Commande commande = new Commande();
        commande.setClientId(client.getId());

        // mode consommation
        String mode = Input.readString("Mode (SUR_PLACE/A_EMPORTER/LIVRAISON): ");
        try {
            commande.setModeConsommation(com.brasilburger.entities.enums.ModeConsommation.valueOf(mode));
        } catch (Exception e) {
            System.out.println("Mode invalide");
            return;
        }

        // ajouter burgers/menus/complements
        List<CommandeBurger> burgers = new ArrayList<>();
        List<CommandeMenu> menus = new ArrayList<>();
        List<CommandeComplement> comps = new ArrayList<>();

        while (true) {
            System.out.println("Ajouter: 1-Burger 2-Menu 3-Complement 0-Fini");
            int ch = Input.readInt("Choix: ");
            if (ch == 0) break;
            if (ch == 1) {
                long id = Input.readInt("Burger Id: ");
                int q = Input.readInt("Quantité: ");
                CommandeBurger cb = new CommandeBurger();
                cb.setBurgerId(id); cb.setQuantite(q);
                burgers.add(cb);
            } else if (ch == 2) {
                long id = Input.readInt("Menu Id: "); int q = Input.readInt("Quantité: ");
                CommandeMenu cm = new CommandeMenu();
                cm.setMenuId(id); cm.setQuantite(q);
                menus.add(cm);
            } else if (ch == 3) {
                long id = Input.readInt("Complement Id: "); int q = Input.readInt("Quantité: ");
                CommandeComplement cc = new CommandeComplement();
                cc.setComplementId(id); cc.setQuantite(q);
                comps.add(cc);
            }
        }

        commande.setBurgers(burgers);
        commande.setMenus(menus);
        commande.setComplements(comps);

        // total : for now ask user to enter total (or implement automatic later)
        double total = Input.readDouble("Total (entrez le total calculé ou 0 pour forcer calcul automatique): ");
        commande.setTotal(total);
        try {
            Commande saved = commandeService.create(commande);
            System.out.println("Commande créée id=" + saved.getId());
        } catch (Exception e) {
            System.out.println("Erreur création commande: " + e.getMessage());
        }
    }

    private void list() {
        List<Commande> list = commandeService.list();
        for (Commande c : list) {
            System.out.printf("%d - clientId=%d - etat=%s - total=%.2f - date=%s%n",
                    c.getId(), c.getClientId(), c.getEtat(), c.getTotal(), c.getDateCommande());
        }
    }

    private void updateEtat() {
        long id = Input.readInt("Commande Id: ");
        String etat = Input.readString("Nouvel état (EN_COURS/VALIDE/TERMINE/ANNULEE): ");
        commandeService.updateEtat(id, etat);
        System.out.println("Etat mis à jour");
    }
}
