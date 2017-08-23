package ru.otus.l91.jdbc.simpleorm.testentity;

import javax.persistence.Entity;

/**
 *
 */
@Entity
public class NonValidEntityNoIdField {
    private int nonId;

    public NonValidEntityNoIdField() {
    }
}
