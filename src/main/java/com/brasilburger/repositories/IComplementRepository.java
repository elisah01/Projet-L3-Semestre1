package com.brasilburger.repositories;

import com.brasilburger.entities.Complement;
import java.util.List;
import java.util.Optional;

public interface IComplementRepository {
    Complement save(Complement complement);
    List<Complement> findAll();
    Optional<Complement> findById(Long id);
    void archive(Long id);
}
