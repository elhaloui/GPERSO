package gperso.models;

import gperso.models.BasedTableEntity;

import javax.persistence.*;

/**
 *.
 */
@Entity
@Table(name = "gp_adresses")
public class Adresse extends BasedTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column (name ="lieu", length=50 , nullable =false)
    private String lieu;
    
    @Column (name = "ville" , length =100 , nullable = false)
    private String ville ;
    @ManyToOne
    @JoinColumn(name = "cin_per")
    private Personnel personnel;

    public Adresse() {
    }

    public Adresse(Integer id, String lieu, String ville, Personnel personnel) {
        this.id = id;
        this.lieu = lieu;
        this.ville = ville;
        this.personnel = personnel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    
}
