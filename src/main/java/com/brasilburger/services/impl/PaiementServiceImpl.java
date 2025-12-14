package com.brasilburger.services.impl;

import com.brasilburger.entities.Paiement;
import com.brasilburger.repositories.IPaiementRepository;
import com.brasilburger.repositories.impl.PaiementRepositoryImpl;
import com.brasilburger.services.IPaiementService;

import java.util.Optional;

public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository repo;

    public PaiementServiceImpl() {
        this.repo = new PaiementRepositoryImpl();
    }

    public PaiementServiceImpl(IPaiementRepository repo) { this.repo = repo; }

    @Override
    public Paiement create(Paiement paiement) {
        if (paiement.getMontant() <= 0) throw new IllegalArgumentException("Montant invalide");
        return repo.save(paiement);
    }

    @Override
    public Optional<Paiement> findByCommande(Long commandeId) {
        return repo.findByCommandeId(commandeId);
    }
}
