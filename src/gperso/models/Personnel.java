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
@Table(name = "gp_personnels")
public class Personnel extends BasedTableEntity {
    @Id
    @Column(name = "cin", length = 10, nullable = false , unique = true)
    private String cin;
    
    @Column (name ="matricule", length=10 , nullable =false)
    private String matricule;
    
    @Column (name = "nom" , length =40 , nullable = false)
    private String nom ;
    
    @Column (name = "prenom" , length=40 , nullable =false)
    private String prenom ;
    
    @Column(name="sexe" , length = 1)
    private String sexe;
    
    @Column(name="grade" , length=30)
    private String grade;
    
    @Column(name="lieu_naissance" , length=30)
    private String lieuNaissance;
    
    @Column(name="date_naissance")
    private Date dateNaissance;
    
    @Column (name="situation_familliale" , length = 10)
    private String situationFamilliale ;
    
    @Column(name="telephone" , length = 12)
    private String telephone;
    
    @Column(name="nombre_enfants")
    private Integer nombreEnfants;
    
    @Column(name="date_engagement")
    private Date dateEngagement;
    
    @Column(name="note")
    private Double note;
    
    @Column(name="level")
    private Integer level;
    
    @Column(name="email" , length = 40)
    private String email;
         
    
    
    
    
    
    
    // List de adresses
    
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "cin_per")
    private List<Adresse> adresses = new ArrayList<Adresse>();
    
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "cin_per")
    private List<DemandeFormation> demandes = new ArrayList<DemandeFormation>();
    
    // Membres de famille
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "cin_per")
    private List<Membre> membres = new ArrayList<Membre>();
    
    // List des conges
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "cin_per")
    private List<Conge> conges = new ArrayList<Conge>();
    // List des conges
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "cin_per")
    private List<Absence> absences = new ArrayList<Absence>();
    
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "cin_per")
    private List<Formation> formations = new ArrayList<Formation>();
    
    @ManyToOne
    @JoinColumn(name = "id_service")
    private Service service;
    
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "personnel", cascade = CascadeType.ALL)
    private Poste poste;

    public Personnel() {
    }

    public Personnel(String cin) {
        this.cin = cin;
    }
    

    

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
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

    public List<Adresse> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Adresse> adresses) {
        this.adresses = adresses;
    }

    public List<DemandeFormation> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<DemandeFormation> demandes) {
        this.demandes = demandes;
    }
    
    

    public List<Membre> getMembres() {
        return membres;
    }

    public void setMembres(List<Membre> membres) {
        this.membres = membres;
    }

    public List<Conge> getConges() {
        return conges;
    }

    public void setConges(List<Conge> conges) {
        this.conges = conges;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSituationFamilliale() {
        return situationFamilliale;
    }

    public void setSituationFamilliale(String situationFamilliale) {
        this.situationFamilliale = situationFamilliale;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getNombreEnfants() {
        return nombreEnfants;
    }

    public void setNombreEnfants(Integer nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }

    public Date getDateEngagement() {
        return dateEngagement;
    }

    public void setDateEngagement(Date dateEngagement) {
        this.dateEngagement = dateEngagement;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public List<Formation> getFormations() {
        return formations;
    }

    public void setFormations(List<Formation> formations) {
        this.formations = formations;
    }
    
    @Override
    public String toString()
    {
        return this.cin;
    }

}
