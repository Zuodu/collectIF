/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ActiviteDAO;
import dao.AdherentDAO;
import dao.DemandeDAO;
import dao.EvenementDAO;
import dao.JpaUtil;
import dao.LieuDAO;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Evenement;
import metier.modele.Lieu;

/**
 *
 * @author pchiu
 */
public class ServiceMetier {
    
        private static void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            ex.hashCode();
        }
    }
    
        private static void log(String message) {
        System.out.flush();
        pause(5);
        System.err.println("[ServiceMetier:Log] " + message);
        System.err.flush();
        pause(5);
    }
        
    public static void createAdherent(Adherent a) throws Exception
    {
        AdherentDAO adhDAO = new AdherentDAO();
        JpaUtil.ouvrirTransaction();
        adhDAO.Create(a);
        JpaUtil.validerTransaction();
        log("Success in creating new "+a.toString());
    }
    
    public static void createActivite(Activite a) throws Exception 
    {
        ActiviteDAO actDAO = new ActiviteDAO();
        JpaUtil.ouvrirTransaction();
        actDAO.Create(a);
        JpaUtil.validerTransaction();
        log("Success in creating new "+a.toString());
    }
    
    public static void createLieu(Lieu a) throws Exception 
    {
        LieuDAO lDAO = new LieuDAO();
        JpaUtil.ouvrirTransaction();
        lDAO.Create(a);
        JpaUtil.validerTransaction();
        log("Success in creating new "+a.toString());
    }
    
    public static void createDemande(Demande d) throws Exception 
    {
        DemandeDAO demDAO = new DemandeDAO();
        JpaUtil.ouvrirTransaction();
        demDAO.Create(d);
        JpaUtil.validerTransaction();
        log("Success in creating new "+d.toString());
    }
    
    public static void createEvenement(Evenement e) throws Exception 
    {
        EvenementDAO evDAO = new EvenementDAO();
        JpaUtil.ouvrirTransaction();
        evDAO.Create(e);
        JpaUtil.validerTransaction();
        log("Success in creating new "+e.toString());
    }
    
    public static int connexion(String mail) throws Exception
    {
        
    }
}
