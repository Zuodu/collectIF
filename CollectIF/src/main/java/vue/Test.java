package vue;

import com.google.maps.model.LatLng;
import dao.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import metier.modele.*;
import metier.service.ServiceMetier;
import static metier.service.ServiceTechnique.*;
import util.*;


/**
 * Created by zifan on 17/03/2017.
 */
public class Test
{
    public static void main(String[] args) throws Exception {

        JpaUtil.init();
        JpaUtil.creerEntityManager();
        ServiceMetier service = new ServiceMetier();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Debut de la phase de tests, creation des objets DAO");
        ActiviteDAO actiDAO = new ActiviteDAO();
        LieuDAO lieuDAO = new LieuDAO();
        DemandeDAO demDAO = new DemandeDAO();
        EvenementDAO evntDAO = new EvenementDAO();
        AdherentDAO adhDAO = new AdherentDAO();
        mainloop:
        while (true) {
            System.out.println("Menu de tests : choisissez un test a effectuer (input le numero)");
            System.out.println("1 connexion existante - rborrotimatiasdantas4171@free.fr");
            System.out.println("2 co inexistante - zifan@free.fr");
            System.out.println("3 adhesion de Manu avec recup de LatLng");
            System.out.println("4 adhesion d'un adherent existant - rborrotimatiasdantas4171@free.fr");
            System.out.println("5 poster une demande normale - Tennis par borrot");
            System.out.println("6 re-poster la même demande ( FAIRE LE 5 D'ABORD) - Tennis par borrot");
            System.out.println("7 faire 2 demande qui completent le nombre de participants d'un evnt");
            System.out.println("8 validation des evnts ");
            System.out.println("9 affichage des evnts d'actualite");
            System.out.println("10 affichage des demandes d'un utilisateur - borrot");
            System.out.println("11 quitter la boucle");

            Integer sel = Saisie.lireInteger("test no ? :", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9,10,11));

            //------------------------------------------------------------------------------------BATTERIE
            switch (sel) {
                case 1:
                    Adherent borrot = service.connexion("rborrotimatiasdantas4171@free.fr");
                    System.out.println(borrot.toString());
                    break;
                case 2:
                    Adherent zifan = service.connexion("zifan@free.fr");
                    if (zifan == null)
                        System.out.println("adherent non trouve");
                    break;
                case 3:
                    Adherent Manu = new Adherent("AMOUROUX", "Manu", "manu@insa-lyon.fr", "20, Avenue Albert Einstein 69100 Villeurbanne");
                    LatLng coordManu = getLatLng(Manu.getAdresse());
                    Manu.setLatitudeLongitude(coordManu.lat, coordManu.lng);
                    System.out.println(service.creerAdherent(Manu));
                    break;
                case 4:
                    borrot = service.connexion("rborrotimatiasdantas4171@free.fr");
                    System.out.println("etat creation borrot :" + service.creerAdherent(borrot));
                    break;
                case 5:
                    Activite tennis = actiDAO.findById(17);
                    borrot = service.connexion("rborrotimatiasdantas4171@free.fr");
                    Date date1 = sdf.parse("30/03/2017");
                    Demande d1 = new Demande(borrot, tennis, Periode.ApresMidi, date1);
                    System.out.println("etat creation demande :" + service.creerDemande(d1));
                    break;
                case 6:
                    tennis = actiDAO.findById(17);
                    date1 = sdf.parse("30/03/2017");
                    borrot = service.connexion("rborrotimatiasdantas4171@free.fr");
                    d1 = new Demande(borrot, tennis, Periode.ApresMidi, date1);
                    System.out.println("etat creation demande :" + service.creerDemande(d1));
                    break;
                case 7:
                    tennis = actiDAO.findById(17);
                    date1 = sdf.parse("30/03/2017");
                    Adherent sing = service.connexion("asing8183@free.fr");
                    Adherent honry = service.connexion("matteo.honry@yahoo.com");
                    Demande d2 = new Demande(sing, tennis, Periode.ApresMidi, date1);
                    Demande d3 = new Demande(honry, tennis, Periode.ApresMidi, date1);
                    System.out.println("etat creation demande :" + service.creerDemande(d2));
                    System.out.println("etat creation demande :" + service.creerDemande(d3));
                    break;
                case 8:
                    Evenement eventAValider = null;
                    Date present = sdf.parse("10/03/2017");
                    Lieu colBesson = lieuDAO.findById(1);
                    ListIterator<Evenement> it2 = service.afficherEvenementEnCours(present).listIterator();
                    while (it2.hasNext()) {
                        eventAValider = it2.next();
                        if (eventAValider.getStatutEvenement() == Statut.Pret) {
                            eventAValider.setStatutEvenement(Statut.Valide);
                            eventAValider.setPAFIndividuel(15.0f);
                            eventAValider.setLieu(colBesson);
                            System.out.println(service.majEvenement(eventAValider));
                        }
                    }
                    break;
                case 9:
                    tennis = actiDAO.findById(17);
                    borrot = service.connexion("rborrotimatiasdantas4171@free.fr");
                    Date date2 = sdf.parse("10/11/2015");
                    present = sdf.parse("10/03/2017");
                    Demande d4 = new Demande(borrot, tennis, Periode.ApresMidi, date2);
                    System.out.println(service.creerDemande(d4));
                    System.out.println(service.afficherEvenementEnCours(present).size());
                    break;
                case 10:
                    borrot = service.connexion("rborrotimatiasdantas4171@free.fr");
                    List<Demande> borrotDem = service.afficherDemandeUtilisateur(borrot);
                    System.out.println("Nombre de demandes de borrot: "+borrotDem.size());
                    System.out.println(borrotDem.get(0).toString());
                    System.out.println(borrotDem.get(1).toString());
                    break;
                case 11:
                    break mainloop;
            }
        }
        System.out.println("Tests persos sur listes etc");
        Evenement evnt = evntDAO.findById(1552);
        System.out.println(evnt.getStatutEvenement());
        List<Demande> hi = evnt.getDemandes();
        System.out.println(hi.size());
        System.out.println(hi.get(0).toString());
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
    }
}