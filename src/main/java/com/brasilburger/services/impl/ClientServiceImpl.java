package com.brasilburger.services.impl;

import com.brasilburger.entities.Client;
import com.brasilburger.repositories.IClientRepository;
import com.brasilburger.repositories.impl.ClientRepositoryImpl;
import com.brasilburger.services.IClientService;

import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements IClientService {

    private final IClientRepository repo;

    public ClientServiceImpl() {
        this.repo = new ClientRepositoryImpl();
    }

    public ClientServiceImpl(IClientRepository repo) { this.repo = repo; }

    @Override
    public Client create(Client client) {
        validate(client);
        return repo.save(client);
    }

    @Override
    public List<Client> list() { return repo.findAll(); }

    @Override
    public Optional<Client> findById(Long id) { return repo.findById(id); }

    @Override
    public Optional<Client> findByTelephone(String telephone) { return repo.findByTelephone(telephone); }

    private void validate(Client c) {
        if (c.getNom() == null || c.getNom().isBlank()) throw new IllegalArgumentException("Nom obligatoire");
        if (c.getTelephone() == null || c.getTelephone().isBlank()) throw new IllegalArgumentException("Téléphone obligatoire");
    }
}
