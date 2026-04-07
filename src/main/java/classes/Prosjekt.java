package classes;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prosjekt")
public class Prosjekt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prosjektID")
    private Long prosjektId;

    @Column(nullable = false)
    private String prosjektNavn;

    @Column
    private String beskrivelse;

    @ManyToMany
    @JoinTable(
        name = "prosjekt_deltakere",
        joinColumns = @JoinColumn(name = "prosjektID"),
        inverseJoinColumns = @JoinColumn(name = "ansattID")
    )
    private Set<Ansatt> deltakere = new HashSet<>();

    public Prosjekt() {}

    public Prosjekt(String prosjektNavn, String beskrivelse) {
        this.prosjektNavn = prosjektNavn;
        this.beskrivelse = beskrivelse;
    }

    public Long getProsjektId() { return prosjektId; }

    public String getProsjektNavn() { return prosjektNavn; }
    public void setProsjektNavn(String prosjektNavn) { this.prosjektNavn = prosjektNavn; }

    public String getBeskrivelse() { return beskrivelse; }
    public void setBeskrivelse(String beskrivelse) { this.beskrivelse = beskrivelse; }

    public Set<Ansatt> getDeltakere() { return deltakere; }
    public void setDeltakere(Set<Ansatt> deltakere) { this.deltakere = deltakere; }

    public void leggTilDeltaker(Ansatt ansatt) {
        deltakere.add(ansatt);
    }

    public void fjernDeltaker(Ansatt ansatt) {
        deltakere.remove(ansatt);
    }
}