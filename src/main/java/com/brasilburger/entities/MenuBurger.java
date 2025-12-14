package com.brasilburger.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class MenuBurger {
    private long menuId;
    private long burgerId;
}
