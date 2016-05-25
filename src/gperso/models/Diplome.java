package gperso.models;

import java.sql.Date;

import javax.persistence.*;

/**
 *.
 */
@Entity
@Table(name = "gp_diplomes")
public class Diplome extends BasedTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     @Column (name ="id_diplome")
    private Integer id;
    
    @Column (name ="date_obtention")
    private Date dateObtention;
    
    
    @Column (name ="libelle_diplome" , length = 200)
    private String  libelleDiplome;
    
    
    @ManyToOne
    @JoinColumn(name = "cin_per")
    private Personnel personnel;

    public Diplome() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateObtention() {
        return dateObtention;
    }

    public void setDateObtention(Date dateObtention) {
        this.dateObtention = dateObtention;
    }

    public String getLibelleDiplome() {
        return libelleDiplome;
    }

    public void setLibelleDiplome(String libelleDiplome) {
        this.libelleDiplome = libelleDiplome;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

}
