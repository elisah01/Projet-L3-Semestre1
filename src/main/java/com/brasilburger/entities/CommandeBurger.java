package com.brasilburger.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class CommandeBurger {
    private Long commandeId;
    private Long burgerId;
    private int quantite;
}
