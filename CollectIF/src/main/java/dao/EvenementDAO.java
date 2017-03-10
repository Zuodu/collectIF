package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Evenement;

public class EvenementDAO {
    
    public Evenement findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Evenement activite = null;
        try {
            activite = em.find(Evenement.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return activite;
    }
    
    public List<Evenement> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Evenement> activites = null;
        try {
            Query q = em.createQuery("SELECT e FROM Evenement e");
            activites = (List<Evenement>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return activites;
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