package gperso.models;

import java.sql.Date;

import javax.persistence.*;

/**
 *.
 */
@Entity
@Table(name = "gp_absences")
public class Absence extends BasedTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     @Column (name ="id_absence")
    private Integer id;
    
    @Column (name ="date_absence")
    private Date dateAbsence;
    
    @Column (name ="duree_absence")
    private Integer dureeAbsence;
    
    @Column (name ="motif_absence" , length = 200)
    private String  motifAbsence;
    
    @Column (name ="decision_chef" , length = 200)
    private String  decisionChef;
    
    
    @ManyToOne
    @JoinColumn(name = "cin_per")
    private Personnel personnel;

    public Absence() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateAbsence() {
        return dateAbsence;
    }

    public void setDateAbsence(Date dateAbsence) {
        this.dateAbsence = dateAbsence;
    }

    public Integer getDureeAbsence() {
        return dureeAbsence;
    }

    public void setDureeAbsence(Integer dureeAbsence) {
        this.dureeAbsence = dureeAbsence;
    }

    public String getMotifAbsence() {
        return motifAbsence;
    }

    public void setMotifAbsence(String motifAbsence) {
        this.motifAbsence = motifAbsence;
    }

    public String getDecisionChef() {
        return decisionChef;
    }

    public void setDecisionChef(String decisionChef) {
        this.decisionChef = decisionChef;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    
}
