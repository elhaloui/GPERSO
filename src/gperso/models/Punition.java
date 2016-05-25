package gperso.models;

import java.sql.Date;

import javax.persistence.*;

/**
 *.
 */
@Entity
@Table(name = "gp_punitions")
public class Punition extends BasedTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     @Column (name ="id_punition")
    private Integer id;
    
    @Column (name ="debut_punition")
    private Date debutPunition;
    
    @Column (name ="fin_punition")
    private Date finPunition;
    
    @Column (name ="nature_punition" , length=100)
    private String naturePunition;
    
    @Column (name ="motif_punition" , length = 100)
    private String  motifPunition;
    
    @Column (name ="punisseur" , length = 100)
    private String  punisseur;
    
    
    @ManyToOne
    @JoinColumn(name = "cin_per")
    private Personnel personnel;

    public Punition() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDebutPunition() {
        return debutPunition;
    }

    public void setDebutPunition(Date debutPunition) {
        this.debutPunition = debutPunition;
    }

    public Date getFinPunition() {
        return finPunition;
    }

    public void setFinPunition(Date finPunition) {
        this.finPunition = finPunition;
    }

    public String getNaturePunition() {
        return naturePunition;
    }

    public void setNaturePunition(String naturePunition) {
        this.naturePunition = naturePunition;
    }

    public String getMotifPunition() {
        return motifPunition;
    }

    public void setMotifPunition(String motifPunition) {
        this.motifPunition = motifPunition;
    }

    public String getPunisseur() {
        return punisseur;
    }

    public void setPunisseur(String punisseur) {
        this.punisseur = punisseur;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }
  
}
