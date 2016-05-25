package gperso.models;

import gperso.models.BasedTableEntity;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 *.
 */
@Entity
@Table(name = "gp_services")
public class Service extends BasedTableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_service")
    private Integer id;
    
    @Column(name = "libelle_service", nullable = false, length = 100)
    private String libelle;
    
    @Column(name = "description_service" , length =200)
    private String description;
    
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "id_service")
    private List<Adresse> adresses = new ArrayList<Adresse>();

    public Service() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Adresse> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Adresse> adresses) {
        this.adresses = adresses;
    }

    
}
