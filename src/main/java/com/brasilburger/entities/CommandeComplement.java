package com.brasilburger.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class CommandeComplement {
    private long commandeId;
    private long complementId;
    private int quantite;
}
