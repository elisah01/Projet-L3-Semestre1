package com.brasilburger.services.impl;

import com.brasilburger.entities.Livraison;
import com.brasilburger.repositories.ILivraisonRepository;
import com.brasilburger.repositories.impl.LivraisonRepositoryImpl;
import com.brasilburger.services.ILivraisonService;

import java.util.List;
import java.util.Optional;

public class LivraisonServiceImpl implements ILivraisonService {

    private final ILivraisonRepository repo;

    public LivraisonServiceImpl() { this.repo = new LivraisonRepositoryImpl(); }
    public LivraisonServiceImpl(ILivraisonRepository repo) { this.repo = repo; }

    @Override
    public Livraison create(Livraison livraison) {
        return repo.save(livraison);
    }

    @Override
    public List<Livraison> list() { return repo.findAll(); }

    @Override
    public Optional<Livraison> get(Long id) { return repo.findById(id); }

    @Override
    public void addCommandeToLivraison(Long livraisonId, Long commandeId) {
        repo.addCommandeToLivraison(livraisonId, commandeId);
    }
}
