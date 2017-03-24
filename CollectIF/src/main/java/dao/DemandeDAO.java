package dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Adherent;
import metier.modele.Demande;

public class DemandeDAO {
    
    public Demande findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Demande activite = null;
        try {
            activite = em.find(Demande.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return activite;
    }
    
    public List<Demande> findbyUser(Adherent adherent) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Demande> activites = null;
        try {
            Query q = em.createQuery("SELECT d FROM Demande d WHERE d.adherent = :adherent");
            q.setParameter("adherent", adherent);
            activites = (List<Demande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return activites;
    }
    
    public List<Demande> findAllPresent(Date present) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Demande> activites = null;
        try {
            Query q = em.createQuery("SELECT d FROM Demande d WHERE d.date >= :present");
            q.setParameter("present", present);
            activites = (List<Demande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return activites;
    }
    
    public void Create(Demande a) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(a);
        }
        catch(Exception e) {
            throw e;
        }
    }
}