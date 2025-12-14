package com.brasilburger.repositories;

import com.brasilburger.entities.Client;
import java.util.List;
import java.util.Optional;

public interface IClientRepository {
    Client save(Client client);
    List<Client> findAll();
    Optional<Client> findById(Long id);
    Optional<Client> findByTelephone(String telephone);
}
