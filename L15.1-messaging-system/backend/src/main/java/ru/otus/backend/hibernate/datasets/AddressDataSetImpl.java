package ru.otus.backend.hibernate.datasets;

import ru.otus.common.services.db.datasets.AddressDataSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 *
 */
@Entity()
@Table(name = "addresses")
public class AddressDataSetImpl extends DataSetImpl implements AddressDataSet {
    private String street;

    public AddressDataSetImpl() {
    }

    public AddressDataSetImpl(String street) {
        this.street = street;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AddressDataSetImpl that = (AddressDataSetImpl) o;
        return Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), street);
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}
