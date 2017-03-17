package metier.modele;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import util.Statut;

@Entity
@DiscriminatorValue("PAYANT")
public class EvenementPayant extends Evenement{

    private Float PAFIndividuel;

    protected EvenementPayant() {
    }
    
    public EvenementPayant(String name, Demande[] asks, Lieu spot, Activite act, Statut state, Float PAF) {
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