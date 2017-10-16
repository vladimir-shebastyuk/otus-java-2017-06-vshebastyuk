package ru.otus.common.services.db.datasets;

/**
 * Created by jstingo on 03.10.2017.
 */
public interface PhoneDataSet extends DataSet {
    String getNumber();

    void setNumber(String number);
}
