package com.brasilburger.repositories;

import com.brasilburger.entities.Paiement;
import java.util.Optional;

public interface IPaiementRepository {
    Paiement save(Paiement paiement);
    Optional<Paiement> findByCommandeId(Long commandeId);
}
