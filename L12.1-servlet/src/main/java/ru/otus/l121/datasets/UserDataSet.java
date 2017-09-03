package ru.otus.l121.datasets;

import javax.persistence.*;
import java.util.*;

/**
 *
 */
@Entity()
@Table(name = "users")
public class UserDataSet extends DataSet {
    private String name;

    private int age;

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressDataSet address;

    @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<PhoneDataSet> phones;

    public UserDataSet() {
        this.phones = new HashSet<>();
    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
        this.phones = new HashSet<>();
    }

    public UserDataSet(String name, int age, AddressDataSet address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public UserDataSet(String name, int age, Set<PhoneDataSet> phones) {
        this.name = name;
        this.age = age;
        this.phones = phones;
    }

    public UserDataSet(String name, int age, AddressDataSet address, Set<PhoneDataSet> phones) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phones = phones;
    }

    public UserDataSet(long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public void addPhone(PhoneDataSet phoneDataSet){
        this.getPhones().add(phoneDataSet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDataSet that = (UserDataSet) o;
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
