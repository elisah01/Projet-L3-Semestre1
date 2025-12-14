package com.brasilburger.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Client extends BaseEntity {
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String password;
    private LocalDateTime createdAt;
}
