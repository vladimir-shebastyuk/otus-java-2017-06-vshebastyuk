package ru.otus.common.services.db.datasets;

import java.util.Set;

/**
 * Created by jstingo on 03.10.2017.
 */
public interface UserDataSet extends DataSet {
    String getName();

    void setName(String name);

    int getAge();

    void setAge(int age);

    AddressDataSet getAddress();

    void setAddress(AddressDataSet address);

    Set<PhoneDataSet> getPhones();

    void setPhones(Set<PhoneDataSet> phones);

    void addPhone(PhoneDataSet phoneDataSet);
}
