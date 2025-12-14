package com.brasilburger.services;

import com.brasilburger.entities.Client;
import java.util.List;
import java.util.Optional;

public interface IClientService {
    Client create(Client client);
    List<Client> list();
    Optional<Client> findById(Long id);
    Optional<Client> findByTelephone(String telephone);
}
