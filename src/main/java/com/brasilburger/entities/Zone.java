package com.brasilburger.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Zone extends BaseEntity {
    private String nom;
    private double prix;
}
