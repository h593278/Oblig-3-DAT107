package no.hvl.dat107;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "Oblig3")
public class Ansatt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ansNr;
	private String brukernavn;
	private String fornavn;
	private String etternavn;
	private LocalDate ansettelsesdato;
	
	//@OneToOne(mappedBy = "Sjef_Id", fetch = FetchType.EAGER)
	
	@ManyToOne
    @JoinColumn(name = "Avd_Id")
	private Avdeling stilling;
	
	private Double maanedslonn;
	
	public Ansatt() {}
	
	public Ansatt(String brukernavn, String fornavn, String etternavn, 
			LocalDate ansettelsesdato, Double maanedslonn,  Avdeling stilling) {
		this.brukernavn = brukernavn;
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.ansettelsesdato = ansettelsesdato;
		this.maanedslonn = maanedslonn;
		this.stilling=stilling;
		this.stilling.leggTilAnsatt(this);
	}
	
	public int getAnsNr() {
		return ansNr;
	}

	public void setAnsNr(int ansNr) {
		this.ansNr = ansNr;
	}

	public String getBrukernavn() {
		return brukernavn;
	}

	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
	}

	public String getFornavn() {
		return fornavn;
	}

	public void setFornavn(String fornavn) {
		this.fornavn = fornavn;
	}

	public String getEtternavn() {
		return etternavn;
	}

	public void setEtternavn(String etternavn) {
		this.etternavn = etternavn;
	}

	public LocalDate getAnsettelsesdato() {
		return ansettelsesdato;
	}

	public void setAnsettelsesdato(LocalDate ansettelsesdato) {
		this.ansettelsesdato = ansettelsesdato;
	}

	public Avdeling getStilling() {
		return stilling;
	}

	public void setStilling(Avdeling stilling) {
		this.stilling = stilling;
	}

	public Double getMaanedslonn() {
		return maanedslonn;
	}

	public void setMaanedslonn(Double maanedslonn) {
		this.maanedslonn = maanedslonn;
	}

	@Override
	public String toString() {
		return "Ansatt [ansNr = " + ansNr + ", brukarnavn = " + brukernavn + ", fornavn = " + fornavn + 
				", etternavn = " + etternavn + ", ansattelsesdato = " + ansettelsesdato + 
				", månedslønn = " + maanedslonn + " stilling = " + stilling.getAvdelingsNavn() + "]";
	}
	public void skrivUt() {
		System.out.println(this.toString());
	}
}
