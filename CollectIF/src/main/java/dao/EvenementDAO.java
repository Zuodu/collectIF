package dao;

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
    
    public Evenement findAvailableEvent(Demande demande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Evenement ev = null;
        try {
            Query q = em.createQuery("SELECT e FROM Evenement e WHERE e.activite = :activite AND e.periode = :periode AND e.date = :date AND e.statut = :statut");
            q.setParameter("date", demande.getDate());
            q.setParameter("activite", demande.getActivite());
            q.setParameter("periode", demande.getPeriode());
            q.setParameter("statut", Statut.EnAttente);
            ev = (Evenement) q.getSingleResult();
        }
        catch(Exception e) {
            throw e;
        }
        
        return ev;
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