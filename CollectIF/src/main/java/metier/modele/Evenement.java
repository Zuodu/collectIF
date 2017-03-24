package metier.modele;

import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import util.Statut;

@Entity
@Inheritance
@DiscriminatorColumn(name = "GRATUITE")
public abstract class Evenement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected String nom;
    @OneToMany(mappedBy="evenement")
    protected List<Demande> demandes;
    @OneToOne
    protected Lieu lieu;
    @OneToOne
    protected Activite activite;
    protected Statut statutEvenement;

    protected Evenement() {
    }

    @Override
    public abstract String toString();
    public abstract List<Demande> getDemandes();

}
