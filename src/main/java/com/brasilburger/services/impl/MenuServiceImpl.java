package com.brasilburger.services.impl;

import com.brasilburger.entities.Menu;
import com.brasilburger.repositories.IMenuRepository;
import com.brasilburger.repositories.impl.MenuRepositoryImpl;
import com.brasilburger.services.IMenuService;

import java.util.List;
import java.util.Optional;

public class MenuServiceImpl implements IMenuService {

    private final IMenuRepository repo;

    public MenuServiceImpl() {
        this.repo = new MenuRepositoryImpl();
    }

    public MenuServiceImpl(IMenuRepository repo) {
        this.repo = repo;
    }

    @Override
    public Menu create(Menu menu) {
        if (menu.getNom() == null || menu.getNom().isBlank()) throw new IllegalArgumentException("Nom obligatoire");
        return repo.save(menu);
    }

    @Override
    public List<Menu> list() {
        return repo.findAll();
    }

    @Override
    public Optional<Menu> get(Long id) {
        return repo.findById(id);
    }

    @Override
    public void archive(Long id) {
        repo.archive(id);
    }

    @Override
    public void addBurger(Long menuId, Long burgerId) {
        repo.addBurgerToMenu(menuId, burgerId);
    }

    @Override
    public void removeBurger(Long menuId, Long burgerId) {
        repo.removeBurgerFromMenu(menuId, burgerId);
    }

    @Override
    public void addComplement(Long menuId, Long complementId) {
        repo.addComplementToMenu(menuId, complementId);
    }

    @Override
    public void removeComplement(Long menuId, Long complementId) {
        repo.removeComplementFromMenu(menuId, complementId);
    }
}
