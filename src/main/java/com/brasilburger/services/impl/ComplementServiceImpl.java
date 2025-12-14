package com.brasilburger.services.impl;

import com.brasilburger.entities.Complement;
import com.brasilburger.repositories.IComplementRepository;
import com.brasilburger.repositories.impl.ComplementRepositoryImpl;
import com.brasilburger.services.IComplementService;

import java.util.List;
import java.util.Optional;

public class ComplementServiceImpl implements IComplementService {

    private final IComplementRepository repo;

    public ComplementServiceImpl() {
        this.repo = new ComplementRepositoryImpl();
    }

    public ComplementServiceImpl(IComplementRepository repo) {
        this.repo = repo;
    }

    @Override
    public Complement create(Complement complement) {
        validate(complement);
        return repo.save(complement);
    }

    @Override
    public List<Complement> list() {
        return repo.findAll();
    }

    @Override
    public Optional<Complement> get(Long id) {
        return repo.findById(id);
    }

    @Override
    public void archive(Long id) {
        repo.archive(id);
    }

    private void validate(Complement c) {
        if (c.getNom() == null || c.getNom().isBlank()) throw new IllegalArgumentException("Nom obligatoire");
        if (c.getPrix() <= 0) throw new IllegalArgumentException("Prix invalide");
    }
}
