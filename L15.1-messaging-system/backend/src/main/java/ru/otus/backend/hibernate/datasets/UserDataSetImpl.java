package ru.otus.backend.hibernate.datasets;

import ru.otus.common.services.db.datasets.AddressDataSet;
import ru.otus.common.services.db.datasets.PhoneDataSet;
import ru.otus.common.services.db.datasets.UserDataSet;

import javax.persistence.*;
import java.util.*;

/**
 *
 */
@Entity()
@Table(name = "users")
public class UserDataSetImpl extends DataSetImpl implements UserDataSet {
    private String name;

    private int age;

    @OneToOne(targetEntity = AddressDataSetImpl.class,fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressDataSet address;

    @OneToMany(targetEntity = PhoneDataSetImpl.class,fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<PhoneDataSet> phones;

    public UserDataSetImpl() {
        this.phones = new HashSet<>();
    }

    public UserDataSetImpl(String name, int age) {
        this.name = name;
        this.age = age;
        this.phones = new HashSet<>();
    }

    public UserDataSetImpl(String name, int age, AddressDataSetImpl address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public UserDataSetImpl(String name, int age, Set<PhoneDataSet> phones) {
        this.name = name;
        this.age = age;
        this.phones = phones;
    }

    public UserDataSetImpl(String name, int age, AddressDataSetImpl address, Set<PhoneDataSet> phones) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phones = phones;
    }

    public UserDataSetImpl(long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public AddressDataSet getAddress() {
        return address;
    }

    @Override
    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    @Override
    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    @Override
    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }

    @Override
    public void addPhone(PhoneDataSet phoneDataSet){
        this.getPhones().add(phoneDataSet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDataSetImpl that = (UserDataSetImpl) o;
        return age == that.age &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(phones, that.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, age, address, phones);
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + this.address +
                ", phones=" + this.phones +
                '}';
    }
}
