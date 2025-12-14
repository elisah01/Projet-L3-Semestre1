package com.brasilburger.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class CommandeMenu {
    private long commandeId;
    private long menuId;
    private int quantite;
}
