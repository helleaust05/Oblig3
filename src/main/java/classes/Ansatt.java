package classes;

import jakarta.persistence.*;

@Entity
@Table(name = "Ansatt")
public class Ansatt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ansattId;

    @Column(unique = true)
    private String brukernavn;

    @Column(nullable = false)
    private String fornavn;

    @Column(nullable = false)
    private String etternavn;

    @Column(name = "TidAnsettelse")
    private java.time.LocalDate ansettelseDato;

    @Column
    private String stilling;

    @Column
    private double manedslonn;

    @ManyToOne
    @JoinColumn(name = "avdelingID")
    private Avdeling avdeling;

  //Tom konstruktør
    public Ansatt() {}

  //Konstruktør
    public Ansatt(String brukernavn, String fornavn, String etternavn, String stilling, double manedslonn) {
        this.brukernavn = brukernavn;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.stilling = stilling;
        this.manedslonn = manedslonn;
        this.ansettelseDato = java.time.LocalDate.now();
    }

    public Integer getId() {return ansattId;}

    public String getBrukernavn() {return brukernavn;}
    public void setBrukernavn(String brukernavn) {this.brukernavn = brukernavn;}

    public String getFornavn() {return fornavn;}
    public void setFornavn(String fornavn) {this.fornavn = fornavn;}


    public String getEtternavn() {return etternavn;}
    public void setEtternavn(String etternavn) {this.etternavn = etternavn;}

    public String getStilling() {return stilling;}
    public void setStilling(String stilling) {this.stilling = stilling;}

    public double getManedslonn() {return manedslonn;}
    public void setManedslonn(Double manedslonn) {this.manedslonn = manedslonn;}

    public Avdeling getAvdeling() {return avdeling;}
    public void setAvdeling(Avdeling avdeling) {this.avdeling = avdeling;}

    public java.time.LocalDate getAnsettelseDato() {return ansettelseDato;}
    public void setAnsettelseDato(java.time.LocalDate ansettelseDato) {this.ansettelseDato = ansettelseDato;}

    @Override
    public String toString() {
        return "[AnsattID: " + ansattId + "\nBrukernavn: " + brukernavn + "\nNavn: " + fornavn + " " + etternavn + "\nStilling: " + stilling + "\nMånedslønn: " + manedslonn + "\nAvdeling: " + (avdeling == null ? "Ingen" : avdeling.getAvdelingNavn()) + "\nDato: " + ansettelseDato + "]";
    }
}

