package classes;

import jakarta.persistence.*;

@Entity
@Table(name = "ansatt")
public class Ansatt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ansattid")
    private Integer ansattId;

    @Column(name = "brukernavn", unique = true)
    private String brukernavn;

    @Column(name = "fornavn", nullable = false)
    private String fornavn;

    @Column(name = "etternavn" , nullable = false)
    private String etternavn;

    @Column(name = "ansattdato")
    private java.time.LocalDate ansettelseDato;

    @Column(name = "stilling")
    private String stilling;

    @Column(name = "manedslonn")
    private double manedslonn;

    @ManyToOne
    @JoinColumn(name = "avdelingid")
    private Avdeling avdelingid;

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

    public java.time.LocalDate getAnsettelseDato() {return ansettelseDato;}
    public void setAnsettelseDato(java.time.LocalDate ansettelseDato) {this.ansettelseDato = ansettelseDato;}

    public void setAvdeling(Avdeling avdelingid) {this.avdelingid = avdelingid;}

    @Override
    public String toString() {
        return "[AnsattID: " + ansattId + "\nBrukernavn: " + brukernavn + "\nNavn: " + fornavn + " " + etternavn + "\nStilling: " + stilling + "\nMånedslønn: " + "\nDato: " + ansettelseDato + "]";
    }
}

