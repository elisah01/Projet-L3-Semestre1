package com.brasilburger.repositories;

import com.brasilburger.entities.Livreur;
import java.util.List;
import java.util.Optional;

public interface ILivreurRepository {
    Livreur save(Livreur l);
    List<Livreur> findAll();
    Optional<Livreur> findById(Long id);
}
