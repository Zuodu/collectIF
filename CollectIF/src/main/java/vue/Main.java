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
import java.util.ListIterator;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Lieu;
import metier.service.ServiceMetier;
import static util.Periode.Matin;

/**
 *
 * @author pchiu
 */
public class Main {
    public static void main(String[] s) throws Exception {
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        
        ServiceMetier service = new ServiceMetier();
        
        Date date1 = new Date(98, 11, 23);
        ActiviteDAO tarotDAO = new ActiviteDAO();
        Demande d1 = new Demande("asing8183@free.fr", "SING", tarotDAO.findById(14), Matin, date1);
        service.creerDemande(d1);
        
        Date date2 = new Date(312, 02, 14);
        ActiviteDAO volleyDAO = new ActiviteDAO();
        Demande d2 = new Demande("asing8183@free.fr", "SING", volleyDAO.findById(15), Matin, date2);
        service.creerDemande(d2);
        
        Date date3 = new Date(412, 05, 17);
        ActiviteDAO tennisDAO = new ActiviteDAO();
        Demande d3 = new Demande("romain.mie@free.fr", "MIE", tennisDAO.findById(17), Matin, date3);
        service.creerDemande(d3);
        
        ListIterator<Demande> it1 = service.afficherDemandeUtilisateur().listIterator();
        while(it1.hasNext()) {
            System.out.println(it1.next().toString());
        }
        
        System.out.println("--------------------------------------");
        
        Date present = new Date(310, 02, 14);
        ListIterator<Demande> it2 = service.afficherDemandeEnCours(present).listIterator();
        while(it2.hasNext()) {
            System.out.println(it2.next().toString());
        }
        
        
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
