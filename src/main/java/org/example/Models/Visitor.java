package org.example.Models;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="visitors", schema="public")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="telephone_number")
    private String telephone;
    @Column(name="subscription")
    private LocalDateTime subscription;
    @Column(name="name")
    private String name;
    @Column(name="surname")
    private String surname;
    @Column(name="lastname")
    private  String lastname;
    @Column(name="photo")
    private byte[] photoPath;

    public byte[] getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(byte[] photoPath) {
        this.photoPath = photoPath;
    }



    @Override
    public String toString() {
        return "Visitor{" +
                "id=" + id +
                ", telephone='" + telephone + '\'' +
                ", subscription=" + subscription +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getSubscription() {
        return subscription;
    }

    public void setSubscription(LocalDateTime subscription) {
        this.subscription = subscription;
    }

    public Visitor(String telephone, LocalDateTime subscription, String name, String surname, String lastname) {
        this.telephone = telephone;
        this.subscription = subscription;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;

    }


    public Visitor() {

    }
}
