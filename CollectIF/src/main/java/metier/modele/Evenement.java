package metier.modele;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import util.Periode;
import util.Statut;
//---------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------ENTITE
@Entity
@Inheritance
@DiscriminatorColumn(name = "GRATUITE")
public abstract class Evenement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @OneToMany(mappedBy="evenement",cascade = CascadeType.PERSIST)
    protected List<Demande> demandes;
    @OneToOne
    protected Lieu lieu;
    @OneToOne
    protected Activite activite;
    protected Periode periode;
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date date;
    protected Statut statutEvenement;
    @Version
    @Column(name="optlock",columnDefinition="integer DEFAULT 0",nullable=false)
    protected Long version = 0L;
    //----------------------------------------------------------------------------CONSTRUCTEUR
    protected Evenement() {
    }
    //----------------------------------------------------------------------------PUBLIC
    public abstract String toString();
    public abstract List<Demande> getDemandes();
    public abstract Activite getActivite();
    public abstract void setStatutEvenement(Statut statut);
    public abstract Statut getStatutEvenement();
    public abstract Lieu getLieu();
    public abstract void setLieu(Lieu s);
    public abstract float getPAFIndividuel();
    public abstract void setPAFIndividuel(float s);
}
//---------------------------------------------------------------------------------------------------------------------