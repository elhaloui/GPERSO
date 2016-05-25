package gperso.models;

import gperso.models.BasedTableEntity;

import javax.persistence.*;

/**
 *.
 */
@Entity
@Table(name = "gp_postes")
public class Poste extends BasedTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column (name ="nom_poste", length=50 )
    private String nomPoste;
    
    @Column (name = "fonction_poste" , length =50 )
    private String fonctionPoste ;
    
    @Column (name ="cout_enf" )
    private Integer  coutEnfant;
    
    @Column (name ="cout_marie" )
    private Integer  coutMarie;
    @Column (name ="cout_celebataire")
    private Integer  coutCelibataire;
    
    @Column (name ="cout_note" )
    private Integer  coutNote;
    
    @Column (name ="cout_anciennete" )
    private Integer  coutAnciennete;
    
    @Column (name ="cout_formation" )
    private Integer  coutFormation;
    
    @Column (name ="cout_absence" )
    private Integer  coutAbsence;
    
    @OneToOne(fetch = FetchType.LAZY)
    private Personnel personnel;
    

    public Poste() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomPoste() {
        return nomPoste;
    }

    public void setNomPoste(String nomPoste) {
        this.nomPoste = nomPoste;
    }

    public String getFonctionPoste() {
        return fonctionPoste;
    }

    public void setFonctionPoste(String fonctionPoste) {
        this.fonctionPoste = fonctionPoste;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Integer getCoutEnfant() {
        return coutEnfant;
    }

    public void setCoutEnfant(Integer coutEnfant) {
        this.coutEnfant = coutEnfant;
    }

    public Integer getCoutMarie() {
        return coutMarie;
    }

    public void setCoutMarie(Integer coutMarie) {
        this.coutMarie = coutMarie;
    }

    public Integer getCoutCelibataire() {
        return coutCelibataire;
    }

    public void setCoutCelibataire(Integer coutCelibataire) {
        this.coutCelibataire = coutCelibataire;
    }

    public Integer getCoutNote() {
        return coutNote;
    }

    public void setCoutNote(Integer coutNote) {
        this.coutNote = coutNote;
    }

    public Integer getCoutAnciennete() {
        return coutAnciennete;
    }

    public void setCoutAnciennete(Integer coutAnciennete) {
        this.coutAnciennete = coutAnciennete;
    }

    public Integer getCoutFormation() {
        return coutFormation;
    }

    public void setCoutFormation(Integer coutFormation) {
        this.coutFormation = coutFormation;
    }

    public Integer getCoutAbsence() {
        return coutAbsence;
    }

    public void setCoutAbsence(Integer coutAbsence) {
        this.coutAbsence = coutAbsence;
    }

   
}
