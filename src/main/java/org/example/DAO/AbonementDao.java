package org.example.DAO;

import org.example.Models.Abonement;
import org.example.Models.Trainer;
import org.example.Models.Visitor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class AbonementDao
{
    public void selectAbonement(int id,int visitorId) {

        Abonement abonement;
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from Abonement v where v.id=:id");
        query.setParameter("id", id);
        abonement = (Abonement) query.uniqueResult();
        t.commit();
        session.close();
        VisitorDao visitorDao=new VisitorDao();
        Visitor visitor=visitorDao.getOneById(visitorId);
        visitorDao.updateSubscriptionVisitor(visitor,abonement.getDays());

    }
    public List<Abonement> getAllAbonements()
    {
        List<Abonement> abonementList =new ArrayList<>();
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("FROM Abonement");
        abonementList= query.list();

        t.commit();
        session.close();
            return abonementList;
    }
    public void  sale(int id) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from Abonement v where v.id=:id");
        query.setParameter("id", id);
       Abonement abonement = (Abonement) query.uniqueResult();
       abonement.setSoldCount(abonement.getSoldCount()+1);
        t.commit();
        session.close();

    }
    public void  refreshSale(int id) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from Abonement v where v.id=:id");
        query.setParameter("id", id);
        Abonement abonement = (Abonement) query.uniqueResult();
        abonement.setSoldCount(0);
        t.commit();
        session.close();

    }
    public void abonementSave(Abonement abonement)
    {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.persist(abonement);
        t.commit();
        session.close();
    }
    public void abonementDelete(Long id) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession() ;
        Transaction tx = session.beginTransaction();
        Abonement abonement = session.load(Abonement.class, id);
        if (abonement != null) {
            session.delete(abonement);
        }
        tx.commit();
        session.close();
    }

}
