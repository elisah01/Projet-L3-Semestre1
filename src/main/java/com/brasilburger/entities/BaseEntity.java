package com.brasilburger.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {   // <-- CHANGEMENT ICI
        this.id = id;
    }
}