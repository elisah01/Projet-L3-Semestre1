package com.brasilburger.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Livreur extends BaseEntity {
    private String nom;
    private String prenom;
    private String telephone;
}
