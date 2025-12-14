package com.brasilburger.services;

import com.brasilburger.entities.Commande;
import java.util.List;
import java.util.Optional;

public interface ICommandeService {
    Commande create(Commande commande);
    List<Commande> list();
    Optional<Commande> get(Long id);
    List<Commande> listByClient(Long clientId);
    void updateEtat(Long commandeId, String etat);
}
