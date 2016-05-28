package gperso.models;

import java.io.Serializable;
import javax.persistence.*;

/**
 *.
 */
@Entity
@Table(name = "gp_demandes_formation")
public class DemandeFormation extends BasedTableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_formation")
    private Formation formation;
    
    @ManyToOne
    @JoinColumn(name = "cin_per")
    private Personnel personnel;
    
    
    @Column (name ="etat_demande", length=50 )
    private String etatDemande;

    public DemandeFormation() {
    }

    public DemandeFormation(Formation formation, Personnel personnel, String etatDemande) {
        this.formation = formation;
        this.personnel = personnel;
        this.etatDemande = etatDemande;
    }
    

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public String getEtatDemande() {
        return etatDemande;
    }

    public void setEtatDemande(String etatDemande) {
        this.etatDemande = etatDemande;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
    
}
