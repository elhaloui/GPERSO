package gperso.models;

import gperso.models.BasedTableEntity;
import java.sql.Date;

import javax.persistence.*;

/**
 *.
 */
@Entity
@Table(name = "gp_membres_famille")
public class Membre extends BasedTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column (name ="nom", length=50 , nullable =false)
    private String nom;
    
    @Column (name = "prenom" , length =50 , nullable = false)
    private String prenom ;
    
    @Column (name = "date_naissance")
    private Date dateNaissance ;
    
    @Column (name = "degre_parent" , length=10)
    private String  degreParente ;
    
    @Column (name = "fonction" , length=50)
    private String  fonction ;
    
    @ManyToOne
    @JoinColumn(name = "cin_per")
    private Personnel personnel;

    public Membre() {
    }

    public Membre(Integer id, String nom, String prenom, Date dateNaissance, String degreParente, String fonction, Personnel personnel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.degreParente = degreParente;
        this.fonction = fonction;
        this.personnel = personnel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getDegreParente() {
        return degreParente;
    }

    public void setDegreParente(String degreParente) {
        this.degreParente = degreParente;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

   
}
