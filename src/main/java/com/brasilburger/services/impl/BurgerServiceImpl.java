package com.brasilburger.services.impl;

import com.brasilburger.entities.Burger;
import com.brasilburger.repositories.IBurgerRepository;
import com.brasilburger.repositories.impl.BurgerRepositoryImpl;
import com.brasilburger.services.IBurgerService;

import java.util.List;
import java.util.Optional;

public class BurgerServiceImpl implements IBurgerService {

    private final IBurgerRepository repo;

    public BurgerServiceImpl() {
        this.repo = new BurgerRepositoryImpl();
    }

    // For DI/testing
    public BurgerServiceImpl(IBurgerRepository repo) {
        this.repo = repo;
    }

    @Override
    public Burger create(Burger burger) {
        validate(burger);
        return repo.save(burger);
    }

    @Override
    public List<Burger> list() {
        return repo.findAll();
    }

    @Override
    public Optional<Burger> get(Long id) {
        return repo.findById(id);
    }

    @Override
    public void archive(Long id) {
        repo.archive(id);
    }

    private void validate(Burger b) {
        if (b.getNom() == null || b.getNom().isBlank()) throw new IllegalArgumentException("Nom obligatoire");
        if (b.getPrix() <= 0) throw new IllegalArgumentException("Prix invalide");
    }
}
