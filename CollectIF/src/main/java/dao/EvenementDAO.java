package dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Evenement;

public class EvenementDAO {
    
    public Evenement findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Evenement ev;
        try {
            ev = em.find(Evenement.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return ev;
    }
    
    public List<Evenement> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Evenement> ev = null;
        try {
            Query q = em.createQuery("SELECT e FROM Evenement e");
            ev = (List<Evenement>) q.getResultList();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return ev;
    }
    
    public List<Evenement> findAllPresent(Date present) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Evenement> list = null;
        try {
            Query q = em.createQuery("SELECT e FROM Evenement e WHERE e.date >= :present");
            q.setParameter("present", present);
            list = (List<Evenement>) q.getResultList();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void create(Evenement evnt) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(evnt);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Evenement evnt) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.merge(evnt);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}