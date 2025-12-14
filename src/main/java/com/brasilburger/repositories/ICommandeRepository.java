package com.brasilburger.repositories;

import com.brasilburger.entities.Commande;
import java.util.List;
import java.util.Optional;

public interface ICommandeRepository {
    Commande save(Commande commande); // should persist commande and items inside a transaction
    List<Commande> findAll();
    Optional<Commande> findById(Long id);
    List<Commande> findByClientId(Long clientId);
    void updateEtat(Long commandeId, String etat); // use string for enum mapping
}
