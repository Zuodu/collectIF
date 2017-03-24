package metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import util.Periode;

@Entity
public class Demande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Adherent adherent;
    @OneToOne
    private Activite activite;
    @ManyToOne
    private Evenement evenement;
    private Periode periode;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

    protected Demande() {
    }
    
    public Demande(Adherent adherent, Activite act, Periode per, Date dato) {
        this.adherent = adherent;
        this.activite = act;
        this.periode = per;
        this.date = dato;
    }

    public Adherent getAdherent() {
        return adherent;
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

    public void setAdherent(Adherent a) {
        adherent = a;
    }

    public void setActivite(Activite a) {
        activite = a;
    }

    public void setPeriode(Periode per) {
        periode = per;
    }

    public void setDate(Date dat) {
        date = dat;
    }

    @Override
    public String toString() {
        return "Demande{" + "mailUtilisateur=" + adherent.getNom() +
                ", Activite=" + activite.getDenomination() +
                ", Periode=" + periode + ", Date=" + date + '}';
    }

}
