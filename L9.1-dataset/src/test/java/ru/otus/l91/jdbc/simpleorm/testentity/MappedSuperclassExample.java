package ru.otus.l91.jdbc.simpleorm.testentity;

import ru.otus.l91.datasets.DataSet;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 */
@MappedSuperclass
public class MappedSuperclassExample {
    @Id
    private long id;

    private transient int ignoredFieldOfSuperClass;

    public MappedSuperclassExample() {
        this.id = DataSet.NOT_SET_ID;
    }

    public MappedSuperclassExample(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
