package dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Demande;
import metier.modele.Evenement;
import util.Statut;

public class EvenementDAO {
    
    public Evenement findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Evenement ev = null;
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
            throw e;
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
            throw e;
        }
        
        return list;
    }
    
    public void Create(Evenement a) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(a);
        }
        catch(Exception e) {
            throw e;
        }
    }
}