package org.example.Models;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="trainers", schema="public")
public class Trainer {
    @Id
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
    @Column(name="present")
private boolean present;

    public Trainer(String telephone, String name, String surname, String lastname, String info, int age, boolean present) {
        this.telephone = telephone;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.info = info;
        this.age = age;
        this.present = present;
    }

    public Trainer() {

    }


}
