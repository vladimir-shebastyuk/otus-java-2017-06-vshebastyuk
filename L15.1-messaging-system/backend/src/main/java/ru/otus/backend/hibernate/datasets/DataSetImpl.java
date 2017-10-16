package ru.otus.backend.hibernate.datasets;

import ru.otus.common.services.db.datasets.DataSet;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Базовый класс для сущностей хранимых в БД
 */
@MappedSuperclass
public class DataSetImpl implements DataSet {

    public static final long NOT_SET_ID = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    public DataSetImpl() {
        this.id = DataSetImpl.NOT_SET_ID;
    }

    public DataSetImpl(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSetImpl dataSet = (DataSetImpl) o;
        return id == dataSet.id;
    }

    @Override
    public int hashCode() {
        return 31; //не извользуем зависимый от id хэш, т.к. иначе у нас будут различные хэщи
        //до сохранения в БД и после https://vladmihalcea.com/2016/06/06/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    }
}
