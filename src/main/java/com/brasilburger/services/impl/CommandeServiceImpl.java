package com.brasilburger.services.impl;

import com.brasilburger.entities.*;
import com.brasilburger.entities.enums.ModeConsommation;
import com.brasilburger.repositories.*;
import com.brasilburger.repositories.impl.*;
import com.brasilburger.services.ICommandeService;

import java.util.List;
import java.util.Optional;

public class CommandeServiceImpl implements ICommandeService {

    private final ICommandeRepository commandeRepo;
    private final IBurgerRepository burgerRepo;
    private final IMenuRepository menuRepo;
    private final IComplementRepository complementRepo;
    private final IZoneRepository zoneRepo;

    public CommandeServiceImpl() {
        this.commandeRepo = new CommandeRepositoryImpl();
        this.burgerRepo = new BurgerRepositoryImpl();
        this.menuRepo = new MenuRepositoryImpl();
        this.complementRepo = new ComplementRepositoryImpl();
        this.zoneRepo = new ZoneRepositoryImpl();
    }

    public CommandeServiceImpl(ICommandeRepository commandeRepo,
                               IBurgerRepository burgerRepo,
                               IMenuRepository menuRepo,
                               IComplementRepository complementRepo,
                               IZoneRepository zoneRepo) {
        this.commandeRepo = commandeRepo;
        this.burgerRepo = burgerRepo;
        this.menuRepo = menuRepo;
        this.complementRepo = complementRepo;
        this.zoneRepo = zoneRepo;
    }

    @Override
    public Commande create(Commande commande) {
        validate(commande);
        double total = computeTotal(commande);
        commande.setTotal(total);
        return commandeRepo.save(commande);
    }

    @Override
    public List<Commande> list() {
        return commandeRepo.findAll();
    }

    @Override
    public Optional<Commande> get(Long id) {
        return commandeRepo.findById(id);
    }

    @Override
    public List<Commande> listByClient(Long clientId) {
        return commandeRepo.findByClientId(clientId);
    }

    @Override
    public void updateEtat(Long commandeId, String etat) {
        commandeRepo.updateEtat(commandeId, etat);
    }

    private void validate(Commande c) {
        if (c.getClientId() == null) throw new IllegalArgumentException("Client obligatoire");
        if (c.getModeConsommation() == null) throw new IllegalArgumentException("Mode consommation obligatoire");
    }

    /**
     * Calcule le total d'une commande :
     * - somme (prix burger * q)
     * - + somme (prix menu * q)  (menu price = menuRepo.calculateMenuPrice(menuId))
     * - + somme (prix complement * q)
     * - + prix de zone si mode = LIVRAISON et zoneId != null
     */
    private double computeTotal(Commande commande) {
        double total = 0.0;

        if (commande.getBurgers() != null) {
            for (CommandeBurger cb : commande.getBurgers()) {
                Optional<com.brasilburger.entities.Burger> ob = burgerRepo.findById(cb.getBurgerId());
                if (ob.isPresent()) {
                    total += ob.get().getPrix() * cb.getQuantite();
                } else {
                    throw new IllegalArgumentException("Burger introuvable: " + cb.getBurgerId());
                }
            }
        }

        if (commande.getMenus() != null) {
            for (CommandeMenu cm : commande.getMenus()) {
                double menuPrice = menuRepo.calculateMenuPrice(cm.getMenuId());
                total += menuPrice * cm.getQuantite();
            }
        }

        if (commande.getComplements() != null) {
            for (CommandeComplement cc : commande.getComplements()) {
                Optional<com.brasilburger.entities.Complement> oc = complementRepo.findById(cc.getComplementId());
                if (oc.isPresent()) {
                    total += oc.get().getPrix() * cc.getQuantite();
                } else {
                    throw new IllegalArgumentException("Complément introuvable: " + cc.getComplementId());
                }
            }
        }

        // ajouter prix zone en cas de livraison (si applicable)
        if (commande.getModeConsommation() == ModeConsommation.LIVRAISON && commande.getZoneId() != null) {
            var oz = zoneRepo.findById(commande.getZoneId());
            if (oz.isPresent()) total += oz.get().getPrix();
            else throw new IllegalArgumentException("Zone introuvable: " + commande.getZoneId());
        }

        return total;
    }
}
