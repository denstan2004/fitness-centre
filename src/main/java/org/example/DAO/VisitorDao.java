package org.example.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.Models.Visitor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VisitorDao {

    public Visitor getOne(String name, String lastname, String surname) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from Visitor v where v.name=:name and v.lastname=:lastname and v.surname=:surname");
        query.setParameter("name", name);
        query.setParameter("lastname", lastname);
        query.setParameter("surname", surname);
        Visitor visitor = (Visitor) query.uniqueResult();
        session.close();

        return visitor;
    }



    public String checkSubscription (String name, String lastname, String surname)
    {
        String result="абонемент ще дійсний";
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from Visitor v where v.name=:name and v.lastname=:lastname and v.surname=:surname");
        query.setParameter("name", name);
        query.setParameter("lastname", lastname);
        query.setParameter("surname", surname);
        Visitor visitor = (Visitor) query.uniqueResult();
        LocalDateTime newDate = LocalDateTime.now();

        if(visitor.getSubscription().isBefore(newDate)) {result="абонемент закінчився";}
        t.commit();
        session.close();
        return  result;
    }


    public List<Visitor> getAllVisitors()
    {
        List<Visitor> visitorList =new ArrayList<>();
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("FROM Visitor");
       visitorList= query.list();

        t.commit();
        session.close();
        return visitorList;
    }
public void visitorSave(Visitor visitor)
{
    Configuration cfg = new Configuration();
    cfg.configure("hibernate.cfg.xml");
    SessionFactory factory = cfg.buildSessionFactory();
    Session session = factory.openSession();
    Transaction t = session.beginTransaction();
    session.persist(visitor);
    t.commit();
    session.close();
}
    public void visitorDelete(String name, String lastname, String surname) {
        Configuration cfg = new Configuration().configure();
        SessionFactory factory = cfg.buildSessionFactory();
             Session session = factory.openSession() ;
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Visitor WHERE name=:name AND lastname=:lastname AND surname=:surname");
            query.setParameter("name", name);
            query.setParameter("lastname", lastname);
            query.setParameter("surname", surname);
            tx.commit();
    }
    public void updateSubscriptionVisitor(String name, String lastname, String surname) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from Visitor v where v.name=:name and v.lastname=:lastname and v.surname=:surname");
        query.setParameter("name", name);
        query.setParameter("lastname", lastname);
        query.setParameter("surname", surname);
        Visitor visitor = (Visitor) query.uniqueResult();
        LocalDateTime newDate = LocalDateTime.now().plusDays(30);
        visitor.setSubscription(newDate);
        session.update(visitor);
        t.commit();
        session.close();
    }
    public void updateForOneDay(String name, String lastname, String surname) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from Visitor v where v.name=:name and v.lastname=:lastname and v.surname=:surname");
        query.setParameter("name", name);
        query.setParameter("lastname", lastname);
        query.setParameter("surname", surname);
        Visitor visitor = (Visitor) query.uniqueResult();
        LocalDateTime newDate = LocalDateTime.now();
        visitor.setSubscription(newDate);
        session.update(visitor);
        t.commit();
        session.close();
    }
}
