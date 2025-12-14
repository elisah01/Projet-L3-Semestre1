package com.brasilburger.repositories;

import com.brasilburger.entities.Zone;
import java.util.List;
import java.util.Optional;

public interface IZoneRepository {
    Zone save(Zone zone);
    List<Zone> findAll();
    Optional<Zone> findById(Long id);
}
 