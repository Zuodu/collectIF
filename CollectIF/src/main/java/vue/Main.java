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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Lieu;

/**
 *
 * @author pchiu
 */
public class Main {
    public static void main(String[] s) throws Exception {
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        
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
    }
}
