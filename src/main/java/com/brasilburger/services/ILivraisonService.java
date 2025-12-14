package com.brasilburger.services;

import com.brasilburger.entities.Livraison;
import java.util.List;
import java.util.Optional;

public interface ILivraisonService {
    Livraison create(Livraison livraison);
    List<Livraison> list();
    Optional<Livraison> get(Long id);
    void addCommandeToLivraison(Long livraisonId, Long commandeId);
}
