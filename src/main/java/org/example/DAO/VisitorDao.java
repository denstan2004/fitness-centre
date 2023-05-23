package org.example.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.Models.Visitor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class VisitorDao {
    public Visitor getOneById(int id) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from Visitor v where v.id=:id");
        query.setParameter("id", id);
        Visitor visitor = (Visitor) query.uniqueResult();
        t.commit();
        session.close();
        return  visitor;
    }
    public Visitor getOneByNumber(String number) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from Visitor v where v.telephone=:number");
        query.setParameter("number",number );
        Visitor visitor = (Visitor) query.uniqueResult();
        t.commit();
        session.close();
        return  visitor;
    }

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
        LocalDateTime newDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);

        if(visitor.getSubscription().isBefore(newDate) ||visitor.getSubscription().isEqual(newDate)) {result="абонемент закінчився";}
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
    public int getAllVisitorsCount()
    {
        int count;
        List<Visitor> visitorList =new ArrayList<>();
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("FROM Visitor");
        visitorList= query.list();
       count=visitorList.size();
        t.commit();
        session.close();
        return count;
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
    public void updateSubscriptionVisitor(Visitor visitor,int days) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Visitor oldVisitor = session.get(Visitor.class, visitor.getId());
        oldVisitor.setName(visitor.getName());
        oldVisitor.setSubscription(visitor.getSubscription());
        oldVisitor.setTelephone(visitor.getTelephone());
        oldVisitor.setSurname(visitor.getSurname());
        oldVisitor.setLastname(visitor.getLastname());
        LocalDateTime newDate = LocalDateTime.now().plusDays(days);
        oldVisitor.setSubscription(newDate);
        session.update(oldVisitor);

        t.commit();
        session.close();
    }
    public List<Visitor> getVisitorsByAgeRange(long minAge, long maxAge) {
        List<Visitor> visitors = new ArrayList<>();

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Visitor> criteriaQuery = builder.createQuery(Visitor.class);
        Root<Visitor> root = criteriaQuery.from(Visitor.class);

        criteriaQuery.select(root)
                .where(builder.between(root.get("age"), minAge, maxAge));

        Query<Visitor> query = session.createQuery(criteriaQuery);
        visitors = query.getResultList();

        t.commit();
        session.close();

        return visitors;
    }

    public List<Visitor> getVisitorsByAgeRangeAndGender(long minAge, long maxAge, String gender) {
        List<Visitor> visitors = new ArrayList<>();
     List <Visitor> result= new ArrayList<>();
      visitors=getVisitorsByAgeRange(minAge,maxAge);
      if(gender.equals("both"))
      {
          return visitors;
      }
      else
      {for(int i=0; i<visitors.size();i++)
          {

              if(visitors.get(i).getSex().equals(gender))
                  result.add(visitors.get(i));
          }
          return result;
      }

      }

    }




