package classes;

import jakarta.persistence.*;

@Entity
@Table(name = "avdeling")
public class Avdeling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avdelingid")
    private Long avdelingid;

    @Column(name = "avdelingnavn")
    private String avdelingNavn;

    @OneToOne
    @JoinColumn(name = "sjefid")
    private Ansatt sjef;

    public Avdeling() {}

    public Avdeling (String avdelingNavn, Ansatt sjef) {
        this.avdelingNavn = avdelingNavn;
        this.sjef = sjef;
    }

    public Long getAvdelingId() {return avdelingid;}

    public String getAvdelingNavn() {return avdelingNavn;}
    public void setAvdelingNavn(String avdelingNavn) {this.avdelingNavn = avdelingNavn;}

    public Ansatt getSjef() {return sjef;}
    public void setSjef(Ansatt sjef) {this.sjef = sjef;}
}
