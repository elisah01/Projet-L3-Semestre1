package com.brasilburger.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class LivraisonCommande {
    private long livraisonId;
    private long commandeId;
}
