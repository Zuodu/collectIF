package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import util.Statut;

@Entity
public class Evenement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String nom;
    private Demande[] demandes;
    private Lieu lieu;
    private Activite activite;
    private Statut statutEvenement;
    private float PAFIndividuel;

    protected Evenement() {
    }
    
    public Evenement(String name, Demande[] asks, Lieu spot, Activite act, Statut state, float PAF) {
        this.nom = name;
        this.demandes = asks;
        this.lieu = spot;
        this.activite = act;
        this.statutEvenement = state;
        this.PAFIndividuel = PAF;
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
    
    public float getPAFIndividuel() {
        return PAFIndividuel;
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
    
    public void setPAFIndividuel(float s) {
        PAFIndividuel = s;
    }

    @Override
    public String toString() {
        return "Evenement{" + "nom=" + nom +
                ", Première Demande de=" + demandes[0].getNom() +
                ", Lieu=" + lieu.getDenomination() +
                ", Activite=" +activite.getDenomination() +
                ", PAFIndiv=" + PAFIndividuel + '}';
    }

}
