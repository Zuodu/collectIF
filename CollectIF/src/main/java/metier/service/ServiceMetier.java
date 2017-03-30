/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.AdherentDAO;
import dao.DemandeDAO;
import dao.EvenementDAO;
import dao.JpaUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;

import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Evenement;
import metier.modele.EvenementGratuit;
import metier.modele.EvenementPayant;
import util.Statut;

/**
 * Services Metier de Collect'IF
 * @author pchiu & zyao
 */
public class ServiceMetier {

    //*************************************************************************************PRIVE
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

    //*************************************************************************************PUBLIC
    //*********************************************CONNEXION

    public Adherent connexion(String mail) throws Exception
    /**
     Trouve/Vérifie l'existence du mail dans la BD et renvoie un objet Adherent
     @param mail mail de l'utilisateur sous forme String
     @return objet Adherent
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
        catch(NoResultException e)
        {
            return null;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return utilisateur;
    }

    //*********************************************CREATION

    public boolean creerAdherent(Adherent newAdherent) throws Exception
    /**
     * Vérifie l'existence d'un Adherent et le persiste  dans le cas contraire.
     * @param newAdherent objet Adherent préalablement créé à faire persister.
     * @return boolean True si l'adhérent a été persisté, false si il existe déjà.
     */
    {
        boolean memberExists = true;
        JpaUtil.creerEntityManager();

        AdherentDAO adhDAO = new AdherentDAO();

        try {
            JpaUtil.ouvrirTransaction();
            adhDAO.findByMail(newAdherent.getMail());
            JpaUtil.validerTransaction();
        } catch (NoResultException e) {
            memberExists = false;
            JpaUtil.annulerTransaction();
        }

        if(!memberExists) {
            try {
                JpaUtil.ouvrirTransaction();
                adhDAO.Create(newAdherent);
                JpaUtil.validerTransaction();
            }
            catch(Exception e) {
                e.printStackTrace();
                JpaUtil.annulerTransaction();
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean creerDemande(Demande newDemande) throws Exception
    {
        Evenement event = null;
        boolean eventExists = true;
        boolean demandExists = true;

        JpaUtil.creerEntityManager();
        DemandeDAO demDAO = new DemandeDAO();
        //bloc de gestion des conflits possibles
        try {
            JpaUtil.ouvrirTransaction();
            demDAO.findAvailableDemand(newDemande);
            JpaUtil.validerTransaction();
        } catch (NoResultException e) {
            demandExists = false;
            JpaUtil.annulerTransaction();
        }
        // si cette demande n'existe pas déjà ( empecher de créer plrs fois une meme demande
        if(!demandExists) {
            try {
                JpaUtil.ouvrirTransaction();
                demDAO.create(newDemande);
                event = demDAO.findAvailableEvent(newDemande);
                JpaUtil.validerTransaction();
            } catch (NoResultException e) {
                eventExists = false;
                JpaUtil.annulerTransaction();
            }
            if(eventExists == false) {
                List<Demande> newList = new ArrayList<Demande>(); newList.add(newDemande);
                try {
                    if (newDemande.getActivite().getPayant()) {
                        event = new EvenementPayant(newList, null, newDemande.getActivite(), newDemande.getPeriode(), newDemande.getDate(), Statut.EnAttente, .0f);
                        creerEvenement(event);
                        JpaUtil.ouvrirTransaction();
                        newDemande.setEvenement(event);
                        demDAO.update(newDemande);
                        JpaUtil.validerTransaction();
                    } else {
                        event = new EvenementGratuit(newList, null, newDemande.getActivite(), newDemande.getPeriode(), newDemande.getDate(), Statut.EnAttente);
                        creerEvenement(event);
                        JpaUtil.ouvrirTransaction();
                        newDemande.setEvenement(event);
                        demDAO.update(newDemande);
                        JpaUtil.validerTransaction();
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                    JpaUtil.annulerTransaction();
                    return false;
                }
            }else {
                try{
                    event.getDemandes().add(newDemande);
                    if(event.getDemandes().size() == event.getActivite().getNbParticipants()) {
                        JpaUtil.ouvrirTransaction();
                        event.setStatutEvenement(Statut.Pret);
                        newDemande.setEvenement(event);
                        demDAO.update(newDemande);
                        JpaUtil.validerTransaction();
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();
                    JpaUtil.annulerTransaction();
                    return false;
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
            evDAO.create(event);
            JpaUtil.validerTransaction();
        }
        catch(Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        }
    }

    //*********************************************UPDATE
    public boolean majEvenement(Evenement event) throws Exception
    {
        try{
            JpaUtil.creerEntityManager();
            EvenementDAO evDAO = new EvenementDAO();
            JpaUtil.ouvrirTransaction();
            evDAO.update(event);
            JpaUtil.validerTransaction();
        }
        catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //*********************************************AFFICHAGE

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
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            return null;
        }

        return listeDemande;
    }

    public List<Demande> afficherEvenementEnCours(Date present) throws Exception
    /**
     * Extrait tous les événements encore d'actualité
     * @param present La date du jour
     * @return Liste contenant
     */

    {
        List<Evenement> listeEvenement = new ArrayList<Evenement>();

        try {
            JpaUtil.creerEntityManager();

            EvenementDAO evDAO = new EvenementDAO();
            JpaUtil.ouvrirTransaction();
            listeEvenement = evDAO.findAllPresent(present);
            JpaUtil.validerTransaction();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return listeDemande;
    }

    //*********************************************SERVICE MAIL

    public void envoiMailSucces(Adherent aa)
        /* envoi un message dans la console qui simule un e-mail de confirmation d'inscription

             */
    {
        System.out.println("From    : collectif@collectif.org\nTo      : "+aa.getMail()+"\nSubject : Bienvenue chez Collect'IF\n");
        System.out.println("Bonjour "+aa.getPrenom()+",\n"+"Nous vous confirmons votre adhesion a l'association COLLECT'IF. " +
                "Votre numéro d'ahérent est : "+aa.getId()+".\n");
    }

    public void envoiMailEchec(Adherent aa)
    {
        System.out.println("From    : collectif@collectif.org\nTo      : "+aa.getMail()+"\nSubject : Bienvenue chez Collect'IF\n");
        System.out.println("Bonjour "+aa.getPrenom()+",\n"+"Votre adhesion a l'association COLLECT'IF a malencontreusement " +
                "echouee... Merci de recommencer ulterieurement.\n");
    }

    public void envoiMailEvenement(Evenement evnt)
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
