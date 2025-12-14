package com.brasilburger.services.impl;

import com.brasilburger.entities.Livreur;
import com.brasilburger.repositories.ILivreurRepository;
import com.brasilburger.repositories.impl.LivreurRepositoryImpl;
import com.brasilburger.services.ILivreurService;

import java.util.List;
import java.util.Optional;

public class LivreurServiceImpl implements ILivreurService {

    private final ILivreurRepository repo;

    public LivreurServiceImpl() { this.repo = new LivreurRepositoryImpl(); }
    public LivreurServiceImpl(ILivreurRepository repo) { this.repo = repo; }

    @Override
    public Livreur create(Livreur l) {
        if (l.getNom() == null || l.getNom().isBlank()) throw new IllegalArgumentException("Nom obligatoire");
        if (l.getPrenom() == null || l.getPrenom().isBlank()) throw new IllegalArgumentException("Prenom obligatoire");
        return repo.save(l);
    }

    @Override
    public List<Livreur> list() { return repo.findAll(); }

    @Override
    public Optional<Livreur> get(Long id) { return repo.findById(id); }
}
