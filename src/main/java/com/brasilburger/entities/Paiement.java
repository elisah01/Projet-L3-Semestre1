package com.brasilburger.entities;

import com.brasilburger.entities.enums.ModePaiement;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Paiement extends BaseEntity {

    private long commandeId;
    private double montant;
    private LocalDateTime datePaiement;
    private ModePaiement mode;
}
