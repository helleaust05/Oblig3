package classes;

import jakarta.persistence.*;

@Entity
@Table(name = "Ansatt")
public class Ansatt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String brukernavn; //Unikt**?
    private String navn;

    public Ansatt() {}

    public Ansatt() {

    }


}
