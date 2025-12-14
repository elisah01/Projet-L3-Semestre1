package com.brasilburger.services;

import com.brasilburger.entities.Paiement;
import java.util.Optional;

public interface IPaiementService {
    Paiement create(Paiement paiement);
    Optional<Paiement> findByCommande(Long commandeId);
}
