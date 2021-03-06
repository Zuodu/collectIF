package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Evenement;
import util.Statut;
//---------------------------------------------------------------------------------------------------------------------
public class DemandeDAO {
    //-----------------------------------------------------------------------------PUBLIC
    public Demande findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Demande activite = null;
        try {
            activite = em.find(Demande.class, id);
        }
        catch(Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        
        return activites;
    }
    
    public Evenement findAvailableEvent(Demande demande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Evenement ev = null;
        try {
            Query q = em.createQuery("SELECT e FROM Evenement e WHERE e.activite = :activite AND e.periode = :periode " +
                    "AND e.date = :date AND e.statutEvenement = :statut");
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
    
    public void create(Demande a) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(a);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Demande findAvailableDemand(Demande demande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Demande dem;
        try {
            Query q = em.createQuery("SELECT d FROM Demande d WHERE d.activite = :activite AND d.periode = :periode " +
                    "AND d.date = :date AND d.adherent = :adherent");
            q.setParameter("date", demande.getDate());
            q.setParameter("activite", demande.getActivite());
            q.setParameter("periode", demande.getPeriode());
            q.setParameter("adherent", demande.getAdherent());
            dem = (Demande) q.getSingleResult();
        }
        catch(Exception e) {
            throw e;
        }
        return dem;
    }
    
    public void update(Demande a) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.merge(a);
        }
        catch(Exception e) {
            throw e;
        }
    }
}
//---------------------------------------------------------------------------------------------------------------------