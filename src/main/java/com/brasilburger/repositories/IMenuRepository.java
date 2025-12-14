package com.brasilburger.repositories;

import com.brasilburger.entities.Menu;
import java.util.List;
import java.util.Optional;

public interface IMenuRepository {
    Menu save(Menu menu);
    List<Menu> findAll();
    Optional<Menu> findById(Long id);
    void archive(Long id);

    // relations
    void addBurgerToMenu(Long menuId, Long burgerId);
    void removeBurgerFromMenu(Long menuId, Long burgerId);
    void addComplementToMenu(Long menuId, Long complementId);
    void removeComplementFromMenu(Long menuId, Long complementId);

    // calcule le prix d'un menu (somme des burgers + compléments qui composent le menu)
    double calculateMenuPrice(Long menuId);
}
