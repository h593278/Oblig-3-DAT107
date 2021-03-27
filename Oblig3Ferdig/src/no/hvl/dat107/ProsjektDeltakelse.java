package no.hvl.dat107;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "Oblig3")
public class ProsjektDeltakelse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prosjektDeltakelseNr;
	
	@ManyToOne
    @JoinColumn(name = "AnsNr")
	private Ansatt ansNr;
	
	@ManyToOne
    @JoinColumn(name = "ProsjektNr ")
	private Prosjekt prosjektNr;
	
	private Integer arbeidsTimer;
	private String rolle;
	
	public ProsjektDeltakelse() {}
	
	public ProsjektDeltakelse(Ansatt ansNr, Prosjekt prosjektNr, Integer arbeidsTimer, String rolle) {
		this.ansNr = ansNr;
		this.prosjektNr = prosjektNr;
		this.arbeidsTimer = arbeidsTimer;
		this.rolle = rolle;
    	prosjektNr.leggTilAnsatt(this);
	}

	public Integer getArbeidsTimer() {
		return arbeidsTimer;
	}

	public void setArbeidsTimer(Integer arbeidsTimer) {
		this.arbeidsTimer = arbeidsTimer;
	}
	
	public Ansatt getAnsatt() {
		return ansNr;
	}

	@Override
	public String toString() {
		return "ProsjektDeltakelse [prosjektDeltakelseNr=" + prosjektDeltakelseNr + ", ansNr=" + ansNr + ", prosjektNr="
				+ prosjektNr + ", arbeidsTimer=" + arbeidsTimer + ", rolle = " + rolle + "]";
	}
	
	
	
}
