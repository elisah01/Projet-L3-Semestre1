package com.brasilburger.repositories;

import com.brasilburger.entities.Quartier;
import java.util.List;
import java.util.Optional;

public interface IQuartierRepository {
    Quartier save(Quartier q);
    List<Quartier> findAllByZone(Long zoneId);
    Optional<Quartier> findById(Long id);
}
