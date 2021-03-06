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
@DiscriminatorValue("GRATUIT")
public class EvenementGratuit extends Evenement {
    //----------------------------------------------------------------------------CONSTRUCTEUR
    protected EvenementGratuit() {
    }
    
    public EvenementGratuit(List<Demande> asks, Lieu spot, Activite act, Periode peri, Date dato, Statut state) {
        this.demandes = asks;
        this.lieu = spot;
        this.activite = act;
        this.periode = peri;
        this.date = dato;
        this.statutEvenement = state;
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

    public float getPAFIndividuel() { return 0; }
    public void setPAFIndividuel(float s) {}
    
    public void setDemandes(List<Demande> s) {
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
        return "Evenement{" +
                ", Première Demande de=" + demandes.get(0).getAdherent().getNom() +
                ", Activite=" +activite.getDenomination();
    }
}
//---------------------------------------------------------------------------------------------------------------------