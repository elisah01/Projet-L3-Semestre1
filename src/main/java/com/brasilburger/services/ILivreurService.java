package com.brasilburger.services;

import com.brasilburger.entities.Livreur;
import java.util.List;
import java.util.Optional;

public interface ILivreurService {
    Livreur create(Livreur l);
    List<Livreur> list();
    Optional<Livreur> get(Long id);
}
