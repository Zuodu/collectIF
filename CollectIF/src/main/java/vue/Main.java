/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.ActiviteDAO;
import dao.AdherentDAO;
import dao.JpaUtil;
import dao.LieuDAO;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Evenement;
import metier.modele.Lieu;
import metier.service.ServiceMetier;
import util.Saisie;

import static util.Periode.Matin;

/**
 *
 * @author pchiu
 */
public class Main {
    public static void main(String[] s) throws Exception {
        JpaUtil.init();
        
        ServiceMetier service = new ServiceMetier();
        
        Activite tarot = new Activite("Tarot", false, 2);
        ActiviteDAO tarotDAO = new ActiviteDAO();
        
        Adherent SING = new Adherent("SING", "Alicia", "asing8183@free.fr", "Chinatown SF.");
        service.creerAdherent(SING);
        Date date1 = new Date(98, 11, 23);
        Demande d1 = new Demande(SING, tarotDAO.findById(1), Matin, date1);
        Saisie.pause("Le systeme va essayer de trouver un evenement a vous associer. Appuyez sur [Entree] pour continuer...");
        service.creerDemande(d1);
        
        Adherent Zifan = new Adherent("YAO", "Zifan", "mail@zifan.fr", "Magellan");
        service.creerAdherent(Zifan);
        Date date2 = new Date(312, 02, 14);
        Demande d2 = new Demande(Zifan, tarotDAO.findById(1), Matin, date1);
        Saisie.pause("Le systeme va essayer de trouver un evenement a vous associer. Appuyez sur [Entree] pour continuer...");
        service.creerDemande(d2);
        
        Adherent Manu = new Adherent("AMOUROUX", "Manuel", "manudu92@flambok.fr", "G");
        service.creerAdherent(Manu);
        Date date3 = new Date(412, 05, 17);
        Demande d3 = new Demande(Manu, tarotDAO.findById(1), Matin, date1);
        Saisie.pause("Le systeme va essayer de trouver un evenement a vous associer. Appuyez sur [Entree] pour continuer...");
        service.creerDemande(d3);
        
        //List<Demande> listD = new List<Demande>();
        
        ListIterator<Demande> it1 = service.afficherDemandeUtilisateur(Manu).listIterator();
        while(it1.hasNext()) {
            System.out.println(it1.next().toString());
        }
        
        System.out.println("--------------------------------------");
        
        Date present = new Date(310, 02, 14);
        ListIterator<Evenement> it2 = service.afficherEvenementEnCours(present).listIterator();
        while(it2.hasNext()) {
            System.out.println(it2.next().toString());
        }
        System.out.println(d3.getEvenement().toString());

        
        
        /*
        // SERVICES ADHERENT
        
        Adherent Zifan = new Adherent("YAO", "Zifan","Zifan@Zifan.cn", "23 Rue Zifan 2323 Zifanville");
        AdherentDAO adhDAO = new AdherentDAO();
        
        JpaUtil.ouvrirTransaction();
        adhDAO.Create(Zifan);
        JpaUtil.validerTransaction();
        
        JpaUtil.ouvrirTransaction();
        for(int i = 0; i < adhDAO.findAll().size(); i++) {
            System.out.println(adhDAO.findAll().get(i).toString());
        }
        JpaUtil.validerTransaction();
        
        // SERVICES ACTIVITE
        
        Activite TP = new Activite("TP", false, 2);
        ActiviteDAO actiDAO = new ActiviteDAO();
        
        JpaUtil.ouvrirTransaction();
        actiDAO.Create(TP);
        JpaUtil.validerTransaction();
        
        JpaUtil.ouvrirTransaction();
        for(int i = 0; i < actiDAO.findAll().size(); i++) {
            System.out.println(actiDAO.findAll().get(i).toString());
        }
        JpaUtil.validerTransaction();
        
        // SERVICES LIEU
        
        Lieu IF = new Lieu("Blaise Pascal", "Département INSA", "7 Avenue Capelle");
        LieuDAO lieuDAO = new LieuDAO();
        
        JpaUtil.ouvrirTransaction();
        lieuDAO.Create(IF);
        JpaUtil.validerTransaction();
        
        JpaUtil.ouvrirTransaction();
        for(int i = 0; i < lieuDAO.findAll().size(); i++) {
            System.out.println(lieuDAO.findAll().get(i).toString());
        }
        JpaUtil.validerTransaction();
        
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        */
    }
}
