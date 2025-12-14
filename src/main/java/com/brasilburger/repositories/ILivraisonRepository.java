package com.brasilburger.repositories;

import com.brasilburger.entities.Livraison;
import java.util.Optional;
import java.util.List;

public interface ILivraisonRepository {
    Livraison save(Livraison livraison);
    Optional<Livraison> findById(Long id);
    List<Livraison> findAll();
    void addCommandeToLivraison(Long livraisonId, Long commandeId);
}
