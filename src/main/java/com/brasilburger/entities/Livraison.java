package com.brasilburger.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Livraison extends BaseEntity {
    private LocalDateTime date;
    private Long livreurId;
    private Long zoneId;
}
