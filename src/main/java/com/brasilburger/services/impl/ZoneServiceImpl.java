package com.brasilburger.services.impl;

import com.brasilburger.entities.Zone;
import com.brasilburger.repositories.IZoneRepository;
import com.brasilburger.repositories.impl.ZoneRepositoryImpl;
import com.brasilburger.services.IZoneService;

import java.util.List;
import java.util.Optional;

public class ZoneServiceImpl implements IZoneService {

    private final IZoneRepository repo;

    public ZoneServiceImpl() { this.repo = new ZoneRepositoryImpl(); }
    public ZoneServiceImpl(IZoneRepository repo) { this.repo = repo; }

    @Override
    public Zone create(Zone zone) {
        if (zone.getNom()==null || zone.getNom().isBlank()) throw new IllegalArgumentException("Nom zone obligatoire");
        if (zone.getPrix() < 0) throw new IllegalArgumentException("Prix zone invalide");
        return repo.save(zone);
    }

    @Override
    public List<Zone> list() { return repo.findAll(); }

    @Override
    public Optional<Zone> get(Long id) { return repo.findById(id); }
}
