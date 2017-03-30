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
import util.Saisie;
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

    // vérifier la présence
    public boolean creerAdherent(Adherent a) throws Exception
    /*
            Vérifie l'existence d'un Adhérent donné par formulaire d'inscritption 
            dans la BD et le créé s'il n'y est pas.
            */
    {
        boolean memberExists = true;
        JpaUtil.creerEntityManager();

        AdherentDAO adhDAO = new AdherentDAO();
        
        try {
            JpaUtil.ouvrirTransaction();
            adhDAO.findByMail(a.getMail());
            JpaUtil.validerTransaction();
        } catch (NoResultException e) {
            memberExists = false;
        }
        
        if(!memberExists) {
            try {
                JpaUtil.ouvrirTransaction();
                adhDAO.Create(a);
                JpaUtil.validerTransaction();
            }
            catch(Exception e) {
                throw e;
            }
        } else {
            return false;
        }
        return true;
    }

    //TODO: DEBUG CACA
    public boolean creerDemande(Demande d) throws Exception
    {
        Evenement event = null;
        boolean eventExists = true;
        boolean demandExists = true;
        
        JpaUtil.creerEntityManager();

        DemandeDAO demDAO = new DemandeDAO();

        try {
            JpaUtil.ouvrirTransaction();
            demDAO.findAvailableDemand(d);
            JpaUtil.validerTransaction();
        } catch (NoResultException e) {
            demandExists = false;
        }

        if(!demandExists) {
            try {
                JpaUtil.ouvrirTransaction();
                demDAO.create(d);
                Saisie.pause("Le systeme va essayer de trouver un evenement a vous associer. Appuyez sur [Entree] pour continuer...");
                event = demDAO.findAvailableEvent(d);
                JpaUtil.validerTransaction();
            } catch (NoResultException e) {
                eventExists = false;
            }
            if(eventExists == false) {
                List<Demande> newList = new ArrayList<Demande>(); newList.add(d);
                if(d.getActivite().getPayant()) {
                    event = new EvenementPayant(newList, null, d.getActivite(), d.getPeriode(), d.getDate(), Statut.EnAttente, .0f);
                    creerEvenement(event);
                    JpaUtil.ouvrirTransaction();
                    d.setEvenement(event);
                    demDAO.update(d);
                    JpaUtil.validerTransaction();
                } else {
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
        } else {
            return false;
        }
        return true;
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
        Adherent utilisateur = null;

        try {
            JpaUtil.creerEntityManager();

            AdherentDAO adhDAO = new AdherentDAO();
            JpaUtil.ouvrirTransaction();
            utilisateur = adhDAO.findByMail(mail);
            JpaUtil.validerTransaction();
        }
        catch(NoResultException eb)
        {
            return null;
        }
        catch(Exception e) {
            e.printStackTrace();
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
