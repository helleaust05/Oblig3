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
    private String Fornavn;

    @Column
    private String Etternavn;

    @Column(name = "TidAnsettelse")
    private java.time.LocalDate AnsettelseDato;

    @Column
    private String stilling;

    @Column
    private double Månedslonn;

  //Tom konstruktør
    public Ansatt() {}

  //Konstruktør
    public Ansatt() {

    }


}
