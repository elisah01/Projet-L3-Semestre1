package com.brasilburger.services.impl;

import com.brasilburger.entities.Quartier;
import com.brasilburger.repositories.IQuartierRepository;
import com.brasilburger.repositories.impl.QuartierRepositoryImpl;
import com.brasilburger.services.IQuartierService;

import java.util.List;
import java.util.Optional;

public class QuartierServiceImpl implements IQuartierService {

    private final IQuartierRepository repo;

    public QuartierServiceImpl() { this.repo = new QuartierRepositoryImpl(); }
    public QuartierServiceImpl(IQuartierRepository repo) { this.repo = repo; }

    @Override
    public Quartier create(Quartier q) {
        if (q.getNom() == null || q.getNom().isBlank()) throw new IllegalArgumentException("Nom obligatoire");
        if (q.getZoneId() == null) throw new IllegalArgumentException("Zone obligatoire");
        return repo.save(q);
    }

    @Override
    public List<Quartier> listByZone(Long zoneId) { return repo.findAllByZone(zoneId); }

    @Override
    public Optional<Quartier> get(Long id) { return repo.findById(id); }
}
