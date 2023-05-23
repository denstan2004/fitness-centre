    package org.example.Models;

    import jakarta.persistence.*;

    @Entity
    @Table(name="abonements", schema="public")
    public class Abonement {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private int id;
        @Column(name="name")
        private String name;
        @Column(name="days")
        private int days;
        @Column(name="price")
        private int price;
        @Column(name="sold_count")
        private int soldCount;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSoldCount() {
            return soldCount;
        }

        public void setSoldCount(int soldCount) {
            this.soldCount = soldCount;
        }

        public Abonement(String name, int days) {
            this.name = name;
            this.days = days;
        }

        public Abonement() {

        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }


    }
