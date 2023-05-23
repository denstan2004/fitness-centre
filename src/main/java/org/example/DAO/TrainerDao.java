package org.example.DAO;

import org.example.Models.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TrainerDao {
    Configuration cfg = new Configuration();
    public List<Trainer> getAllTrainers()
    {
        List<Trainer> trainerList =new ArrayList<>();

        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("FROM Trainer");
        trainerList = query.list();
        t.commit();
        session.close();
        return trainerList;
    }

    public void trainerSave(Trainer trainer)
    {

        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.persist(trainer);
        t.commit();
        session.close();
    }
    public void trainerDelete(Long id) {
        cfg.configure("hibernate.cfg.xml");

        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession() ;
        Transaction tx = session.beginTransaction();
        Trainer trainer = session.load(Trainer.class, id);
        if (trainer != null) {
            session.delete(trainer);
        }
        tx.commit();
        session.close();
    }
    public void trainerRefresh(Long id) {
        cfg.configure("hibernate.cfg.xml");

        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession() ;
        Transaction tx = session.beginTransaction();
        Trainer trainer = session.load(Trainer.class, id);
        if (trainer != null) {
           trainer.setCountVisitors(0);
           session.saveOrUpdate(trainer);
        }
        tx.commit();
        session.close();
    }


    public void changePresentFalse(Long id) {

        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Trainer trainer = session.get(Trainer.class, id);
        if(trainer.isReady()) {
            trainer.setReady(false);
        }
        trainer.setCountVisitors(trainer.getCountVisitors()+1);
        session.saveOrUpdate(trainer);
        t.commit();
        session.close();
    }
    public void changePresentTrue(Long id) {
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Trainer trainer = session.get(Trainer.class, id);
        if(trainer.isReady()==false) {
            trainer.setReady(true);
        }

        session.saveOrUpdate(trainer);
        t.commit();
        session.close();
    }


}
