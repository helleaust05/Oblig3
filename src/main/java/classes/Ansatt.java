package classes;

import jakarta.persistence.*;

@Entity
@Table(name = "Ansatt")
public class Ansatt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String brukernavn;

    @Column
    private String fornavn;

    @Column
    private String etternavn;

    @Column(name = "TidAnsettelse")
    private java.time.LocalDate ansettelseDato;

    @Column
    private String stilling;

    @Column
    private double manedslonn;

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

    public Integer getId() {return id;}

    public String getBrukernavn() {return brukernavn;}
    public void setBrukernavn(String brukernavn) {this.brukernavn = brukernavn;}

    public String getFornavn() {return fornavn;}
    public void setFornavn(String fornavn) {this.fornavn = fornavn;}


    public String getEtternavn() {return etternavn;}
    public void setEtternavn(String etternavn) {this.etternavn = etternavn;}

    public String getStilling() {return stilling;}
    public void setStilling() {this.stilling = stilling;}

    public double getManedslonn() {return manedslonn;}
    public void setManedslonn(Double manedslonn) {this.manedslonn = manedslonn;}

    public java.time.LocalDate getAnsettelseDato() {return ansettelseDato;}
    public void setAnsettelseDato(java.time.LocalDate) {this.ansettelseDato = ansettelseDato;}
}

