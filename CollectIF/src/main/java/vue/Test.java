package vue;

import com.google.maps.model.LatLng;
import dao.ActiviteDAO;
import dao.JpaUtil;
import dao.LieuDAO;
import java.util.Date;
import java.util.ListIterator;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Evenement;
import metier.modele.Lieu;
import metier.service.ServiceMetier;
import static metier.service.ServiceTechnique.*;
import util.Periode;
import util.Statut;

/**
 * Created by zuoduzuodu on 17/03/2017.
 */
public class Test
{
    public static void main(String[] args) throws Exception {

        JpaUtil.init();
        ServiceMetier service = new ServiceMetier();
        
        Adherent borrot = service.connexion("rborrotimatiasdantas4171@free.fr");
        System.out.println(borrot.toString());
        
        Adherent zifan = service.connexion("zifan@free.fr");
        if(zifan == null)
        System.out.println("zifan est null");
        
        Adherent Manu = new Adherent("AMOUROUX", "Manu", "manu@insa-lyon.fr", "20, Avenue Albert Einstein 69100 Villeurbanne");
        LatLng coordManu = getLatLng(Manu.getAdresse());
        Manu.setLatitudeLongitude(coordManu.lat, coordManu.lng);
        System.out.println(service.creerAdherent(Manu));
        
        System.out.println(service.creerAdherent(borrot));
        
        ActiviteDAO actiDAO = new ActiviteDAO();
        Activite tennis = actiDAO.findById(17);
        Date date1 = new Date(315, 11, 23);
        Demande d1 = new Demande(borrot, tennis, Periode.ApresMidi, date1);
        System.out.println(service.creerDemande(d1));
        System.out.println(service.creerDemande(d1));
        
        Adherent sing = service.connexion("asing8183@free.fr");
        Adherent honry = service.connexion("matteo.honry@yahoo.com");
        Demande d2 = new Demande(sing, tennis, Periode.ApresMidi, date1);
        Demande d3 = new Demande(honry, tennis, Periode.ApresMidi, date1);
        System.out.println(service.creerDemande(d2));
        System.out.println(service.creerDemande(d3));
        
        System.out.println(service.afficherDemandeUtilisateur(borrot).size());
        
        Date date2 = new Date(98, 11, 23);
        Date present = new Date(310, 02, 14);
        Demande d4 = new Demande(honry, tennis, Periode.ApresMidi, date2);
        System.out.println(service.creerDemande(d4));
        System.out.println(service.afficherEvenementEnCours(present).size());
        
        Evenement eventAValider = null;
        LieuDAO lieuDAO = new LieuDAO();
        Lieu colBesson = lieuDAO.findById(1);
        ListIterator<Evenement> it2 = service.afficherEvenementEnCours(present).listIterator();
        while(it2.hasNext()) {
            eventAValider = it2.next();
            if(eventAValider.getStatutEvenement() == Statut.Pret) {
                eventAValider.setStatutEvenement(Statut.Valide);
                eventAValider.setPAFIndividuel(15.0f);
                eventAValider.setLieu(colBesson);
                break;
            }
        }
        System.out.println(service.majEvenement(eventAValider));
        
        
        
    }
}