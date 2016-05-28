package gperso.models;

import gperso.models.BasedTableEntity;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 *.
 */
@Entity
@Table(name = "gp_formations")
public class Formation extends BasedTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name ="id_formation")
    private Integer id;
    
    @Column (name ="nature_formation", length=50 )
    private String natureFormation;
    
    @Column (name ="debut_formation")
    private Date debutFormation;
    
    @Column (name ="fin_formation")
    private Date finFormation;
    
    @Column (name ="lieu_formation" , length=50)
    private String lieuFormation;
    
    @Column (name ="diplome_formation" , length=50)
    private String diplomeFormation;
    
    @Column (name ="observation_formation" , length=60)
    private String observationFormation;
    
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "id_formation")
    private List<DemandeFormation> demandes = new ArrayList<DemandeFormation>();
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNatureFormation() {
        return natureFormation;
    }

    public void setNatureFormation(String natureFormation) {
        this.natureFormation = natureFormation;
    }

    public Date getDebutFormation() {
        return debutFormation;
    }

    public void setDebutFormation(Date debutFormation) {
        this.debutFormation = debutFormation;
    }

    public Date getFinFormation() {
        return finFormation;
    }

    public void setFinFormation(Date finFormation) {
        this.finFormation = finFormation;
    }

    public String getLieuFormation() {
        return lieuFormation;
    }

    public void setLieuFormation(String lieuFormation) {
        this.lieuFormation = lieuFormation;
    }

    public String getDiplomeFormation() {
        return diplomeFormation;
    }

    public void setDiplomeFormation(String diplomeFormation) {
        this.diplomeFormation = diplomeFormation;
    }

    public String getObservationFormation() {
        return observationFormation;
    }

    public void setObservationFormation(String observationFormation) {
        this.observationFormation = observationFormation;
    }

    public List<DemandeFormation> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<DemandeFormation> demandes) {
        this.demandes = demandes;
    }
    
    

}
