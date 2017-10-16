package ru.otus.backend.hibernate.datasets;

import ru.otus.common.services.db.datasets.PhoneDataSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 *
 */
@Entity()
@Table(name = "phones")
public class PhoneDataSetImpl extends DataSetImpl implements PhoneDataSet {
    private String number;

    public PhoneDataSetImpl() {
    }

    public PhoneDataSetImpl(String number) {
        this.number = number;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PhoneDataSetImpl that = (PhoneDataSetImpl) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), number);
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
