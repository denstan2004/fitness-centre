package org.example.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="trainers", schema="public")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="telephone_number")
    private String telephone;
    @Column(name="name")
    private String name;
    @Column(name="surname")
    private String surname;
    @Column(name="lastname")
    private  String lastname;
    @Column(name="info")
    private String info;
    @Column(name="age")
    private int age;
    @Column(name= "ready")
    private boolean ready;
    @Column(name= "visitorscount")
    private int countVisitors;
    @Column(name= "photo")
    private byte[] photoPath;

    public Trainer(String telephone, String name, String surname, String lastname, String info, int age, boolean ready) {
        this.telephone = telephone;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.info = info;
        this.age = age;
        this.ready = ready;
    }

    public Trainer() {

    }

    public int getCountVisitors() {
        return countVisitors;
    }

    public void setCountVisitors(int countVisitors) {
        this.countVisitors = countVisitors;
    }

    public int getId() {
        return id;
    }



    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isReady() {
      return ready;
    }

    public void setReady(boolean present) {
        this.ready = present;
    }

    public byte[] getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(byte[] photo) {
        this.photoPath = photo;
    }
}
