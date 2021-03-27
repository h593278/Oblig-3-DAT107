package no.hvl.dat107;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class AnsattDAO {
	private EntityManagerFactory emf;
	
	public AnsattDAO(){
		 emf = Persistence.createEntityManagerFactory("persistence");
	}
	
	public Ansatt finnAnsattMedId(int id) {
		EntityManager em = emf.createEntityManager();
		
        try {
        	
        	return em.find(Ansatt.class, id);
        } finally {
        	em.close();
        }
	}
	
	public Ansatt finnAnsattMedBrukernavn(String brukernavn) {
		EntityManager em = emf.createEntityManager();
		
        try {
        	
        	TypedQuery<Ansatt> query = em.createQuery("SELECT t FROM Ansatt t WHERE t.brukernavn = :brukernavn", Ansatt.class);
			query.setParameter("brukernavn", brukernavn);	
			List<Ansatt> a = query.getResultList();
			return a.get(0);	//Rekner med at der er kunn eit element, og det returnere me.
        	
        } finally {
        	em.close();
        }
	}
	public List<Ansatt> finnAlleAnsatt() {
		
		EntityManager em = emf.createEntityManager();
		
		try {
			TypedQuery<Ansatt> query = em.createQuery("SELECT t FROM Ansatt t", Ansatt.class);
			return query.getResultList();
		
		} finally {
			em.close();
		}
	}
	
	public void skrivUtAlle() {
		EntityManager em = emf.createEntityManager();
		
		try {
			List<Ansatt> a = this.finnAlleAnsatt();
			
			for (int i=0; i<a.size(); i++) {
				System.out.println(a.get(i).toString());
				
			}
		
		} finally {
			em.close();
		}
	}
	
	public void setLonn(int id, double nyLonn) {
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
        	tx.begin();
        	
        	Ansatt a = em.find(Ansatt.class, id);

        	a.setMaanedslonn(nyLonn);

        	tx.commit();
        } finally {
        	em.close();
        }
	}
	
	public void nyAnsatt(String brukernavn, String fornavn, 
			String etternavn, LocalDate ansettelsesdato, 
			Double maanedslonn, Avdeling stilling) {
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
        	tx.begin();
        	if (skjekkBrukernavn(brukernavn)) {
        		Ansatt a = new Ansatt(brukernavn, fornavn, etternavn, 
        			ansettelsesdato, maanedslonn, stilling);
        		em.persist(a);
        	}
        	tx.commit();
        } finally {
        	em.close();
        }
	}
	
	public void slettAnsatt(int id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			
			Ansatt a = em.find(Ansatt.class, id);
			if (a.getStilling().getSjef_Id().equals(a)) {
	        	System.out.println("Kan ikkje slette for personen er sjef");
			} else {
				
				TypedQuery<ProsjektDeltakelse> query = em.createQuery("SELECT t FROM ProsjektDeltakelse t WHERE t.ansNr = :ansNr", ProsjektDeltakelse.class);
				query.setParameter("ansNr", a);
				List<ProsjektDeltakelse> prosjektDeltakelse = query.getResultList();
				
				if (prosjektDeltakelse.size()>0){
		        	System.out.println("Kan ikkje slette for personen er registrert i eit prosjekt");
				} else {
					a.getStilling().fjernAnsatt(a);
					em.remove(a);
		        	System.out.println("Sletting utført");
				}
			}
			
			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
	}
	
	private boolean skjekkBrukernavn(String brukernavn) {
		EntityManager em = emf.createEntityManager();
		
		try {
			List<Ansatt> a = this.finnAlleAnsatt();
			if (brukernavn.length() > 4 || brukernavn.length() < 3) {
				return false;
			}
			for (int i=0; i<a.size(); i++) {
				if (a.get(i).getBrukernavn().equals(brukernavn)) {
					return false;
				}
			}
			return true;
		
		} finally {
			em.close();
		}
	}
	
	public boolean byttAvdeling(Avdeling nyavdeling, int id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
        	tx.begin();
        	 	
        	Ansatt ansatt = em.find(Ansatt.class, id);
       
			if(ansatt.getAnsNr()!=ansatt.getStilling().getSjef_Id().getAnsNr())	{	//Skjekke om han er sjef
				ansatt.getStilling().fjernAnsatt(ansatt);	//Fjerne fra gamel avdeling
				ansatt.setStilling(nyavdeling);		//Han jobber nå i den nye avdelingen
				nyavdeling.leggTilAnsatt(ansatt);		//Legger han til i den nye avdelingen
				em.merge(nyavdeling);
	        	tx.commit();
				return true;
			} else {
				return false;
			}
		
		} finally {
			em.close();
		}
	}
	
	public boolean nyAvdeling(String namn, int id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
        	tx.begin();
        	 	
        	Ansatt ansatt = em.find(Ansatt.class, id);
        	
			if(ansatt.getAnsNr()!=ansatt.getStilling().getSjef_Id().getAnsNr())	{	//Skjekke om han er sjef
				Avdeling nyAvdeling = new Avdeling(namn, ansatt);	//Oprette nye avdeling
				
				ansatt.getStilling().fjernAnsatt(ansatt);	//Fjerne fra gamel avdeling
				ansatt.setStilling(nyAvdeling);				//Han jobber nå i den nye avdelingen
				nyAvdeling.leggTilAnsatt(ansatt);			//Legger han til i den nye avdelingen
				em.persist(nyAvdeling);						
	        	tx.commit();
				return true;
			} else {
				return false;
			}
		
		} finally {
			em.close();
		}
	}
	
	
	
	
}
