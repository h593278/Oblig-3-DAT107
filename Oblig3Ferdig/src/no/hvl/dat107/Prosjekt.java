package no.hvl.dat107;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "Oblig3")
public class Prosjekt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prosjektNr;
	
	private String prosjektNavn;
	private String beskrivelse;
	
	@OneToMany(mappedBy = "prosjektNr", fetch = FetchType.EAGER)
	private List<ProsjektDeltakelse> prosjektDeltakelse;

	public Prosjekt() {}
	
	public Prosjekt(String prosjektNavn, String beskrivelse) {
		this.prosjektNavn = prosjektNavn;
		this.beskrivelse = beskrivelse;
		prosjektDeltakelse = new ArrayList<ProsjektDeltakelse>();  
	}
	
	public void leggTilAnsatt(ProsjektDeltakelse p) {
		prosjektDeltakelse.add(p);
	}
	
	public List<ProsjektDeltakelse> getProsjektDeltakelse() {
		return prosjektDeltakelse;
	}

	public int getProsjektNr() {
		return prosjektNr;
	}

	public void setProsjektNr(int prosjektNr) {
		this.prosjektNr = prosjektNr;
	}

	@Override
	public String toString() {
		return "Prosjekt [prosjektNavn: " + prosjektNavn + ", beskrivelse: " + beskrivelse + "]";
	}
}
