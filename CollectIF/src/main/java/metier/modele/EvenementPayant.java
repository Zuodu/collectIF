package metier.modele;

import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import util.Periode;
import util.Statut;
//---------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------ENTITE
@Entity
@DiscriminatorValue("PAYANT")
public class EvenementPayant extends Evenement{
    //----------------------------------------------------------------------------CONSTRUCTEUR
    private Float PAFIndividuel;

    protected EvenementPayant() {
    }
    
    public EvenementPayant(List<Demande> asks, Lieu spot, Activite act, Periode peri, Date dato, Statut state, Float PAF) {
        this.demandes = asks;
        this.lieu = spot;
        this.activite = act;
        this.periode = peri;
        this.date = dato;
        this.statutEvenement = state;
        this.PAFIndividuel = PAF;
    }
    //----------------------------------------------------------------------------PUBLIC
    public List<Demande> getDemandes() {
        return demandes;
    }
    
    public Lieu getLieu() {
        return lieu;
    }
    
    public Activite getActivite() {
        return activite;
    }
    
     public Periode getPeriode() {
        return periode;
    }
    
    public Date getDate() {
        return date;
    }
    
    public Statut getStatutEvenement() {
        return statutEvenement;
    }
    
    public float getPAFIndividuel() {
        return PAFIndividuel;
    }
    
    public void setDemandes(List<Demande> s) {
        demandes = s;
    }
    
    public void setLieu(Lieu s) {
        lieu = s;
    }
    
    public void setActivite(Activite s) {
        activite = s;
    }
    
    public void setPeriode(Periode p) {
        periode = p;
    }
    
    public void setDate(Date d) {
        date = d;
    }
    
    public void setStatutEvenement(Statut s) {
        statutEvenement = s;
    }
    
    public void setPAFIndividuel(float s) {
        PAFIndividuel = s;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                ", Première Demande de=" + demandes.get(0).getAdherent().getNom() +
                ", Activite=" +activite.getDenomination() +
                ", PAFIndiv=" + PAFIndividuel + '}';
    }
}
//---------------------------------------------------------------------------------------------------------------------