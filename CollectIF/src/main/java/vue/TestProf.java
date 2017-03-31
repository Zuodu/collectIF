package vue;

import com.google.maps.model.LatLng;
import dao.*;

import java.util.Arrays;
import java.util.Date;
import java.util.ListIterator;
import metier.modele.*;
import metier.service.ServiceMetier;
import static metier.service.ServiceTechnique.*;
import util.*;

/**
 * Created by zifanYao on 30/03/2017.
 */
public class TestProf {
    public static void main(String[] args) throws Exception {

        JpaUtil.init();
        ServiceMetier service = new ServiceMetier();
        System.out.println("Debut de la phase de tests, creation des objets DAO");
        ActiviteDAO actiDAO = new ActiviteDAO();
        LieuDAO lieuDAO = new LieuDAO();
        DemandeDAO demDAO = new DemandeDAO();
        EvenementDAO evntDAO = new EvenementDAO();
        AdherentDAO adhDAO = new AdherentDAO();
        System.out.println("debut des tests du prof...");
        //mettre des tests a faire ici...
        Adherent c = service.connexion("rborrotimatiasdantas4171@free.fr");
        Evenement ev = evntDAO.findById(1852);
        service.envoiMailEvenement(ev);

    }
}
