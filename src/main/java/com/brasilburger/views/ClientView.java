package com.brasilburger.views;

import com.brasilburger.core.utils.Input;
import com.brasilburger.entities.Client;
import com.brasilburger.services.IClientService;
import com.brasilburger.services.impl.ClientServiceImpl;

import java.util.List;
import java.util.Optional;

public class ClientView {

    private final IClientService service = new ClientServiceImpl();

    public void menu() {
        int c;
        do {
            System.out.println("=== Clients ===");
            System.out.println("1. Créer client");
            System.out.println("2. Lister clients");
            System.out.println("3. Rechercher par téléphone");
            System.out.println("0. Retour");
            c = Input.readInt("Choix: ");
            switch (c) {
                case 1 -> create();
                case 2 -> list();
                case 3 -> findByTel();
                case 0 -> {}
                default -> System.out.println("Choix invalide");
            }
        } while (c != 0);
    }

    public void create() {
        String nom = Input.readString("Nom: ");
        String prenom = Input.readString("Prenom: ");
        String tel = Input.readString("Téléphone: ");
        String email = Input.readString("Email (optionnel): ");
        String password = Input.readString("Mot de passe (optionnel): ");
        Client c = new Client();
        c.setNom(nom);
        c.setPrenom(prenom);
        c.setTelephone(tel);
        c.setEmail(email);
        c.setPassword(password);
        service.create(c);
        System.out.println("Client créé");
    }

    private void list() {
        List<Client> list = service.list();
        for (Client c : list) {
            System.out.printf("%d - %s %s - %s%n", c.getId(), c.getNom(), c.getPrenom(), c.getTelephone());
        }
    }

    private void findByTel() {
        String tel = Input.readString("Téléphone: ");
        Optional<Client> oc = service.findByTelephone(tel);
        if (oc.isPresent()) {
            Client c = oc.get();
            System.out.printf("%d - %s %s - %s%n", c.getId(), c.getNom(), c.getPrenom(), c.getTelephone());
        } else {
            System.out.println("Client non trouvé");
        }
    }
}
