package ru.otus.l91.jdbc.simpleorm.testentity;

import javax.persistence.Entity;

/**
 * Created by jstingo on 10.08.2017.
 */
@Entity
public class NonValidEntityNoDefaultConstructor {
    private int id;

    public NonValidEntityNoDefaultConstructor(int id) {
        this.id = id;
    }
}
