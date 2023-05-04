package org.example.DAO;

import org.example.Models.Trainer;
import org.example.Models.Visitor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

public class TrainerDao {
    public List<Visitor> getAllTrainers()
    {
        List<Visitor> trainerList =new ArrayList<>();
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("FROM Trainer");
        trainerList= query.list();
        t.commit();
        session.close();
        return trainerList;
    }
    public void trainerSave(Trainer trainer)
    {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.persist(trainer);
        t.commit();
        session.close();
    }
    public void trainerDelete(String name, String lastname, String surname) {
        Configuration cfg = new Configuration().configure();
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession() ;
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Trainer WHERE name=:name AND lastname=:lastname AND surname=:surname");
        query.setParameter("name", name);
        query.setParameter("lastname", lastname);
        query.setParameter("surname", surname);
        tx.commit();
    }
}
