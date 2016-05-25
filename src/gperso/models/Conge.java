package gperso.models;

import gperso.models.BasedTableEntity;
import java.sql.Date;

import javax.persistence.*;

/**
 *.
 */
@Entity
@Table(name = "gp_conges")
public class Conge extends BasedTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column (name ="debut_conge")
    private Date debutConge;
    
    @Column (name ="fin_conge")
    private Date finConge;
    
    @Column (name ="date_demande")
    private Date dateDemande;
    
    @Column (name = "nature_conge" , length =30 )
    private String natureConge ;
    
    @Column (name = "contenu_conge" , length =200 )
    private String contenuConge ;
    
    @Column (name = "commentaire_chef" , length =200 )
    private String commentaireChef ;
    
    @Column (name = "etat_conge" , length =10 )
    private String etatConge ;
    
    @ManyToOne
    @JoinColumn(name = "cin_per")
    private Personnel personnel;

    public Conge() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDebutConge() {
        return debutConge;
    }

    public void setDebutConge(Date debutConge) {
        this.debutConge = debutConge;
    }

    public Date getFinConge() {
        return finConge;
    }

    public void setFinConge(Date finConge) {
        this.finConge = finConge;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getNatureConge() {
        return natureConge;
    }

    public void setNatureConge(String natureConge) {
        this.natureConge = natureConge;
    }

    public String getContenuConge() {
        return contenuConge;
    }

    public void setContenuConge(String contenuConge) {
        this.contenuConge = contenuConge;
    }

    public String getCommentaireChef() {
        return commentaireChef;
    }

    public void setCommentaireChef(String commentaireChef) {
        this.commentaireChef = commentaireChef;
    }

    public String getEtatConge() {
        return etatConge;
    }

    public void setEtatConge(String etatConge) {
        this.etatConge = etatConge;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

   
}
