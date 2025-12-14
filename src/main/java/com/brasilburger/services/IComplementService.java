package com.brasilburger.services;

import com.brasilburger.entities.Complement;
import java.util.List;
import java.util.Optional;

public interface IComplementService {
    Complement create(Complement complement);
    List<Complement> list();
    Optional<Complement> get(Long id);
    void archive(Long id);
}
