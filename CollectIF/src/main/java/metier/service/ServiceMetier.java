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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
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
        
    public void creerAdherent(Adherent a) throws Exception
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
    
    public void creerActivite(Activite a) throws Exception
    {
        ActiviteDAO actDAO = new ActiviteDAO();
        JpaUtil.ouvrirTransaction();
        actDAO.Create(a);
        JpaUtil.validerTransaction();
        log("Success in creating new "+a.toString());
    }
    
    public void creerLieu(Lieu a) throws Exception
    {
        LieuDAO lDAO = new LieuDAO();
        JpaUtil.ouvrirTransaction();
        lDAO.Create(a);
        JpaUtil.validerTransaction();
        log("Success in creating new "+a.toString());
    }
    
    public void creerDemande(Demande d) throws Exception
    {
        DemandeDAO demDAO = new DemandeDAO();
        JpaUtil.ouvrirTransaction();
        demDAO.Create(d);
        JpaUtil.validerTransaction();
        log("Success in creating new "+d.toString());
    }
    
    public void creerEvenement(Evenement e) throws Exception
    {
        EvenementDAO evDAO = new EvenementDAO();
        JpaUtil.ouvrirTransaction();
        evDAO.Create(e);
        JpaUtil.validerTransaction();
        log("Success in creating new "+e.toString());
    }
    
    public Adherent connexion(String mail) throws Exception
    /*
            Trouve/Vérifie l'existence du mail dans la BD
            */
    {
        AdherentDAO adhDAO = new AdherentDAO();
        JpaUtil.ouvrirTransaction();
        Adherent utilisateur = adhDAO.findByMail(mail);
        JpaUtil.validerTransaction();

        return utilisateur;
    }
    
    public List<Demande> afficherDemandeUtilisateur() throws Exception
    /*
            Trouve/Vérifie l'existence du mail dans la BD
            */
    {
        List<Demande> listeDemande = new ArrayList<Demande>();
        DemandeDAO demDAO = new DemandeDAO();
        JpaUtil.ouvrirTransaction();
        listeDemande = demDAO.findAll();
        JpaUtil.validerTransaction();

        return listeDemande;
    }


    
    public List<Demande> afficherDemandeEnCours(Date present) throws Exception
    /*
            Trouve/Vérifie l'existence du mail dans la BD
            */
    {
        List<Demande> listeDemande = new ArrayList<Demande>();
        DemandeDAO demDAO = new DemandeDAO();
        JpaUtil.ouvrirTransaction();
        listeDemande = demDAO.findAll();
        JpaUtil.validerTransaction();

        ListIterator<Demande> demandeIterator = listeDemande.listIterator();
        while(demandeIterator.hasNext()) {
            Demande demande = demandeIterator.next();
            if(present.after(demande.getDate()))
            demandeIterator.remove();
        }
        return listeDemande;
    }

    public static void envoiMailSucces(Adherent aa)
        /* envoi un message dans la console qui simule un e-mail de confirmation d'inscription

             */
    {
        System.out.println("From    : collectif@collectif.org\nTo      : "+aa.getMail()+"\nSubject : Bienvenue chez Collect'IF\n");
        System.out.println("Bonjour "+aa.getPrenom()+",\n"+"Nous vous confirmons votre adhesion a l'association COLLECT'IF. " +
                "Votre numéro d'ahérent est : "+aa.getId()+".\n");
    }

    public static void envoiMailEchec(Adherent aa)
    {
        System.out.println("From    : collectif@collectif.org\nTo      : "+aa.getMail()+"\nSubject : Bienvenue chez Collect'IF\n");
        System.out.println("Bonjour "+aa.getPrenom()+",\n"+"Votre adhesion a l'association COLLECT'IF a malencontreusement " +
                "echouee... Merci de recommencer ulterieurement.\n");
    }

}
