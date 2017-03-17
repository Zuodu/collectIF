package metier.modele;

import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import util.Statut;

@Entity
@Inheritance
@DiscriminatorColumn(name = "GRATUITE")
public abstract class Evenement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected String nom;
    protected Demande[] demandes;
    protected Lieu lieu;
    protected Activite activite;
    protected Statut statutEvenement;

    protected Evenement() {
    }

    @Override
    public abstract String toString();

}
