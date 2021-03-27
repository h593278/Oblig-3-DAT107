package no.hvl.dat107;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "Oblig3")
public class Avdeling {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int AvdelingNr;
	
	@OneToMany(mappedBy = "stilling", fetch = FetchType.EAGER)
	private List<Ansatt> ansatte;
	
	private String AvdelingsNavn;
	
	@OneToOne
    @JoinColumn(name = "Sjef_Id")
    private Ansatt sjef_Id;
	
	public Avdeling() {}
	
	public Avdeling(String AvdelingsNavn, Ansatt sjef_Id) {
		this.AvdelingsNavn = AvdelingsNavn;
		this.sjef_Id = sjef_Id;
		ansatte = new ArrayList<Ansatt>();  
	}
	
	public void leggTilAnsatt(Ansatt a) {
		ansatte.add(a);
	}
	
	public List<Ansatt> getAnsatte() {
		return ansatte;
	}
	
	public String getAvdelingsNavn() {
		return AvdelingsNavn;
	}
	
	public Ansatt getSjef_Id() {
		return sjef_Id;
	}
	
	public void fjernAnsatt(Ansatt ansatt) {
		
		System.out.println("Fjernet fra Avdeling? " + ansatte.remove(ansatt));
	}
	
}
