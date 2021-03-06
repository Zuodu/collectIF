package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Adherent;
//---------------------------------------------------------------------------------------------------------------------
public class AdherentDAO {
    //----------------------------------------------------------------------------PUBLIC
    public Adherent findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Adherent adherent = null;
        try {
            adherent = em.find(Adherent.class, id);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return adherent;
    }
    
    public List<Adherent> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Adherent> adherents = null;
        try {
            Query q = em.createQuery("SELECT a FROM Adherent a");
            adherents = (List<Adherent>) q.getResultList();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return adherents;
    }
    
    public void Create(Adherent a) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(a);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Adherent findByMail(String mail) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Adherent adherent = null;
        try {
            Query q = em.createQuery("SELECT a FROM Adherent a WHERE a.mail = :mail");
            q.setParameter("mail", mail);
            adherent = (Adherent) q.getSingleResult();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return adherent;
    }
}
//---------------------------------------------------------------------------------------------------------------------