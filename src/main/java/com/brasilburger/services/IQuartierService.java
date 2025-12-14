package com.brasilburger.services;

import com.brasilburger.entities.Quartier;
import java.util.List;
import java.util.Optional;

public interface IQuartierService {
    Quartier create(Quartier q);
    List<Quartier> listByZone(Long zoneId);
    Optional<Quartier> get(Long id);
}
