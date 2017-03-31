package vue;

import dao.*;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.service.ServiceMetier;
import util.Periode;
import util.Saisie;

import java.text.SimpleDateFormat;
import java.util.Date;

import static util.Saisie.lireInteger;

/**
 * Created by zifan on 30/03/2017.
 */
public class TestC {
    public static void main(String[] args) throws Exception {

        JpaUtil.init();
        JpaUtil.creerEntityManager();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ServiceMetier service = new ServiceMetier();
        System.out.println("Debut de la phase de tests, creation des objets DAO");
        ActiviteDAO actiDAO = new ActiviteDAO();
        LieuDAO lieuDAO = new LieuDAO();
        DemandeDAO demDAO = new DemandeDAO();
        EvenementDAO evntDAO = new EvenementDAO();
        AdherentDAO adhDAO = new AdherentDAO();
        System.out.println("tentative de concurrent et lecture fantome... executez le test 5 d'abord.");
        Integer personne = Saisie.lireInteger("Vous etes sing (1) ou (2) honry ?");
        Saisie.pause("Appuyez sur entree pour continuer la lecture");
        Activite tennis = actiDAO.findById(17);
        Date date1 = sdf.parse("30/03/2017");
        Adherent moi;
        if(personne == 1) moi = service.connexion("asing8183@free.fr");
        else moi = service.connexion("matteo.honry@yahoo.com");
        Demande d2 = new Demande(moi, tennis, Periode.ApresMidi, date1);
        System.out.println("etat creation demande :" + service.creerDemande(d2));
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
    }
}
