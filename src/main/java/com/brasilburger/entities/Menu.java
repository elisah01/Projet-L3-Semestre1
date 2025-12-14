package com.brasilburger.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Menu extends BaseEntity {
    private String nom;
    private String imageUrl;
    private boolean archive;
    private LocalDateTime createdAt;
}
