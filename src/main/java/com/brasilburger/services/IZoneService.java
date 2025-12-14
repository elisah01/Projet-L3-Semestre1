package com.brasilburger.services;

import com.brasilburger.entities.Zone;
import java.util.List;
import java.util.Optional;

public interface IZoneService {
    Zone create(Zone zone);
    List<Zone> list();
    Optional<Zone> get(Long id);
}
