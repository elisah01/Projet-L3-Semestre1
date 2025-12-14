package com.brasilburger.services;

import com.brasilburger.entities.Menu;
import java.util.List;
import java.util.Optional;

public interface IMenuService {
    Menu create(Menu menu);
    List<Menu> list();
    Optional<Menu> get(Long id);
    void archive(Long id);

    void addBurger(Long menuId, Long burgerId);
    void removeBurger(Long menuId, Long burgerId);
    void addComplement(Long menuId, Long complementId);
    void removeComplement(Long menuId, Long complementId);
}
