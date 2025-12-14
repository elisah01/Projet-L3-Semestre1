package com.brasilburger.services;

import com.brasilburger.entities.Burger;
import java.util.List;
import java.util.Optional;

public interface IBurgerService {
    Burger create(Burger burger);
    List<Burger> list();
    Optional<Burger> get(Long id);
    void archive(Long id);
}
