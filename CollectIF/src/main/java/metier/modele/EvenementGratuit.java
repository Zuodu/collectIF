package metier.modele;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import util.Statut;

@Entity
@DiscriminatorValue("GRATUIT")
public class EvenementGratuit extends Evenement {

    protected EvenementGratuit() {
    }
    
    public EvenementGratuit(String name, Demande[] asks, Lieu spot, Activite act, Statut state) {
        this.nom = name;
        this.demandes = asks;
        this.lieu = spot;
        this.activite = act;
        this.statutEvenement = state;
    }

    public String getNom() {
        return nom;
    }
    
    public Demande[] getDemandes() {
        return demandes;
    }
    
    public Lieu getLieu() {
        return lieu;
    }
    
    public Activite getActivite() {
        return activite;
    }
    
    public Statut getStatutEvenement() {
        return statutEvenement;
    }
    

    public void setNom(String s) {
        nom = s;
    }
    
    public void setDemandes(Demande[] s) {
        demandes = s;
    }
    
    public void setLieu(Lieu s) {
        lieu = s;
    }
    
    public void setActivite(Activite s) {
        activite = s;
    }
    
    public void setStatutEvenement(Statut s) {
        statutEvenement = s;
    }
    
    @Override
    public String toString() {
        return "Evenement{" + "nom=" + nom +
                ", Première Demande de=" + demandes[0].getNom() +
                ", Lieu=" + lieu.getDenomination() +
                ", Activite=" +activite.getDenomination();
    }
}