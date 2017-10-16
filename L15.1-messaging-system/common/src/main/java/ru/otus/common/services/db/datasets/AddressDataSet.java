package ru.otus.common.services.db.datasets;

/**
 * Created by jstingo on 03.10.2017.
 */
public interface AddressDataSet extends DataSet {
    String getStreet();

    void setStreet(String street);
}
