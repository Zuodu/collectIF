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
        
    public static void creerAdherent(Adherent a) throws Exception
    /*
            Vérifie l'existence d'un Adhérent donné par formulaire d'inscritption 
            dans la BD et le créé s'il n'y est pas.
            */
    {
        AdherentDAO adhDAO = new AdherentDAO();
        JpaUtil.ouvrirTransaction();
        adhDAO.Create(a);
        JpaUtil.validerTransaction();
        log("Success in creating new "+a.toString());
    }
    
    public static void creerActivite(Activite a) throws Exception 
    {
        ActiviteDAO actDAO = new ActiviteDAO();
        JpaUtil.ouvrirTransaction();
        actDAO.Create(a);
        JpaUtil.validerTransaction();
        log("Success in creating new "+a.toString());
    }
    
    public static void creerLieu(Lieu a) throws Exception 
    {
        LieuDAO lDAO = new LieuDAO();
        JpaUtil.ouvrirTransaction();
        lDAO.Create(a);
        JpaUtil.validerTransaction();
        log("Success in creating new "+a.toString());
    }
    
    public static void creerDemande(Demande d) throws Exception 
    {
        DemandeDAO demDAO = new DemandeDAO();
        JpaUtil.ouvrirTransaction();
        demDAO.Create(d);
        JpaUtil.validerTransaction();
        log("Success in creating new "+d.toString());
    }
    
    public static void creerEvenement(Evenement e) throws Exception 
    {
        EvenementDAO evDAO = new EvenementDAO();
        JpaUtil.ouvrirTransaction();
        evDAO.Create(e);
        JpaUtil.validerTransaction();
        log("Success in creating new "+e.toString());
    }
    
    public static Adherent connexion(String mail) throws Exception
    /*
            Trouve/Vérifie l'existence du mail dans la BD
            */
    {
        
    }
    
    public static int disconnection(String mail) throws Exception
    /*
            Trouve/Vérifie l'existence du mail dans la BD et change le statut de
            l'utilisateur 
            */
    {
        
    }
    
}
