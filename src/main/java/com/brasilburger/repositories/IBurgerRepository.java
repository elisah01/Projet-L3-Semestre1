package com.brasilburger.repositories;

import com.brasilburger.entities.Burger;
import java.util.List;
import java.util.Optional;

public interface IBurgerRepository {
    Burger save(Burger burger);
    List<Burger> findAll();
    Optional<Burger> findById(Long id);
    void archive(Long id);
}
