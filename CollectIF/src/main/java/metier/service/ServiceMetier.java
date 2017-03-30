/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.ActiviteDAO;
import dao.AdherentDAO;
import dao.DemandeDAO;
import dao.EvenementDAO;
import dao.JpaUtil;
import dao.LieuDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Evenement;
import metier.modele.EvenementGratuit;
import metier.modele.EvenementPayant;
import metier.modele.Lieu;
import util.Statut;

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
        try {
            JpaUtil.creerEntityManager();
        
            AdherentDAO adhDAO = new AdherentDAO();
            JpaUtil.ouvrirTransaction();
            adhDAO.Create(a);
            JpaUtil.validerTransaction();
            log("Success in creating new "+a.toString());
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public void creerActivite(Activite a) throws Exception
    {
        try {
            JpaUtil.creerEntityManager();
        
            ActiviteDAO actDAO = new ActiviteDAO();
            JpaUtil.ouvrirTransaction();
            actDAO.Create(a);
            JpaUtil.validerTransaction();
            log("Success in creating new "+a.toString());
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public void creerLieu(Lieu a) throws Exception
    {
        try {
            JpaUtil.creerEntityManager();
        
            LieuDAO lDAO = new LieuDAO();
            JpaUtil.ouvrirTransaction();
            lDAO.Create(a);
            JpaUtil.validerTransaction();
            log("Success in creating new "+a.toString());
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    //TODO: DEBUG CACA
    public void creerDemande(Demande d) throws Exception
    {
        Evenement event = null;
           boolean exists = true;
        try {
            JpaUtil.creerEntityManager();
        
            DemandeDAO demDAO = new DemandeDAO();
            JpaUtil.ouvrirTransaction();
            demDAO.create(d);
            JpaUtil.validerTransaction();
            
            try {
                JpaUtil.ouvrirTransaction();
                event = demDAO.findAvailableEvent(d);
                JpaUtil.validerTransaction();
            } catch (NoResultException e) {
                exists = false;
            }
            if(exists == false) {
                List<Demande> newList = new ArrayList<Demande>(); newList.add(d);
                if(d.getActivite().getPayant()) {
                    event = new EvenementPayant(newList, null, d.getActivite(), d.getPeriode(), d.getDate(), Statut.EnAttente, .0f);
                    creerEvenement(event);
                    JpaUtil.ouvrirTransaction();
                    d.setEvenement(event);
                    demDAO.update(d);
                    JpaUtil.validerTransaction();
                }
                else {
                    event = new EvenementGratuit(newList, null, d.getActivite(), d.getPeriode(), d.getDate(), Statut.EnAttente);
                    creerEvenement(event);
                    JpaUtil.ouvrirTransaction();
                    d.setEvenement(event);
                    demDAO.update(d);
                    JpaUtil.validerTransaction();
                }
            } else {
                event.getDemandes().add(d);
                if(event.getDemandes().size() == event.getActivite().getNbParticipants()) {
                    JpaUtil.ouvrirTransaction();
                    event.setStatutEvenement(Statut.Pret);
                    d.setEvenement(event);
                    demDAO.update(d);
                    JpaUtil.validerTransaction();
                }
            }
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public void creerEvenement(Evenement event) throws Exception
    {
        try {
            JpaUtil.creerEntityManager();
        
            EvenementDAO evDAO = new EvenementDAO();
            JpaUtil.ouvrirTransaction();
            evDAO.Create(event);
            JpaUtil.validerTransaction();
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Adherent connexion(String mail) throws Exception
    /*
            Trouve/Vérifie l'existence du mail dans la BD
            */
    {
        Adherent utilisateur;
                
        try {
            JpaUtil.creerEntityManager();
        
            AdherentDAO adhDAO = new AdherentDAO();
            JpaUtil.ouvrirTransaction();
            utilisateur = adhDAO.findByMail(mail);
            JpaUtil.validerTransaction();
        }
        catch(Exception e) {
            throw e;
        }

        return utilisateur;
    }
    
    public List<Demande> afficherDemandeUtilisateur(Adherent adherent) throws Exception
    /*
            Trouve/Vérifie l'existence du mail dans la BD
            */
    {
        List<Demande> listeDemande = new ArrayList<Demande>();
        
        try {
            JpaUtil.creerEntityManager();
        
            DemandeDAO demDAO = new DemandeDAO();
            JpaUtil.ouvrirTransaction();
            listeDemande = demDAO.findbyUser(adherent);
            JpaUtil.validerTransaction();
        }
        catch(Exception e) {
            throw e;
        }

        return listeDemande;
    }


    
    public List<Demande> afficherEvenementEnCours(Date present) throws Exception
    /*
            Trouve/Vérifie l'existence du mail dans la BD
            */
    {
        List<Demande> listeDemande = new ArrayList<Demande>();
        
        try {
            JpaUtil.creerEntityManager();
        
            DemandeDAO demDAO = new DemandeDAO();
            JpaUtil.ouvrirTransaction();
            listeDemande = demDAO.findAllPresent(present);
            JpaUtil.validerTransaction();
        }
        catch(Exception e) {
            throw e;
        }

        /*ListIterator<Demande> demandeIterator = listeDemande.listIterator();
        while(demandeIterator.hasNext()) {
            Demande demande = demandeIterator.next();
            if(present.after(demande.getDate()))
            demandeIterator.remove();
        }*/
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

    public static void envoiMailEvenement(Evenement evnt)
    {
        List<Demande> listeDemandes = evnt.getDemandes();
        SimpleDateFormat format = new SimpleDateFormat("dd MMMMM yyyy");
        ServiceTechnique tech = new ServiceTechnique();
        LatLng arrivee = new LatLng(evnt.getLieu().getLatitude(),evnt.getLieu().getLongitude());

        for(int i=0;i<listeDemandes.size();i++)
        {
            LatLng depart = new LatLng(listeDemandes.get(i).getAdherent().getLatitude(),listeDemandes.get(i).getAdherent().getLongitude());
            double distance = ServiceTechnique.getDistanceEnKm(depart,arrivee);
            System.out.println("From    : collectif@collectif.org\nTo      : "+listeDemandes.get(i).getAdherent().getMail()+"\nSubject : Nouvel Evenement Collect'IF\n");
            System.out.println("Bonjour"+listeDemandes.get(i).getAdherent().getNom()+",\n"+"Comme vous l'aviez souhaite, Collect'IF organise un" +
                    "evenement de "+listeDemandes.get(0).getActivite()+" le"+format.format(listeDemandes.get(0).getDate())+". Vous trouverez ci-dessous les details de cet evenement.");
            System.out.println("\n Associativement votre,\n      Le responsable de l'Association\n\n");
            System.out.println("Evenement : "+listeDemandes.get(0).getActivite()+"\nDate : "+format.format(listeDemandes.get(0).getDate()
            +"\nLieu : "+evnt.getLieu().getAdresse()+"(à "+distance+" km de chez vous)"));
            if(evnt.getPAFIndividuel() != 0) System.out.println("La PAF est de : "+evnt.getPAFIndividuel()+"\n\n");
            System.out.println("Vous jouerez avec :\n");
            for(int j=0;j<listeDemandes.size() && j!= i;j++)
            {
                System.out.println("     "+listeDemandes.get(j).getAdherent().getPrenom()+" "+listeDemandes.get(j).getAdherent().getNom());
            }
        }
    }

}
