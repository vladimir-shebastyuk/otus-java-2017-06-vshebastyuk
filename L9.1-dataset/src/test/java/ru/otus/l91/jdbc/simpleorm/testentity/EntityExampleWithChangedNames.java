package ru.otus.l91.jdbc.simpleorm.testentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "entity")
public class EntityExampleWithChangedNames {
    private int simpleName;

    @Column(name = "column_name_field")
    private int fieldWithChangedColumnName;
}
