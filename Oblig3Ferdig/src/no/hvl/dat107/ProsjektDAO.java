package no.hvl.dat107;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ProsjektDAO {
	private EntityManagerFactory emf;
	
	public ProsjektDAO(){
		 emf = Persistence.createEntityManagerFactory("persistence");
	}
	
	public void nyttProsjekt(String prosjektNavn , String beskrivelse) {
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
        	tx.begin();
        	Prosjekt p = new Prosjekt(prosjektNavn, beskrivelse);
        	em.persist(p);
        	tx.commit();
        } finally {
        	em.close();
        }
	}
	
	public Prosjekt finnProsjektMedId(int id) {
		EntityManager em = emf.createEntityManager();
		
        try {
        	
        	return em.find(Prosjekt.class, id);
        } finally {
        	em.close();
        }
	}
	
	public void skrivUtProsjekt(int id) {
		EntityManager em = emf.createEntityManager();
		
        try {
        	int sumTimer = 0;
        	Prosjekt p = em.find(Prosjekt.class, id);
    		List<ProsjektDeltakelse> pd = p.getProsjektDeltakelse();
    		
    		System.out.println(p.toString());
    		for (int i=0; i<pd.size(); i++) {
    			
        		System.out.println(pd.get(i).getAnsatt().toString() + ", arbeidstimer: " + pd.get(i).getArbeidsTimer());
        		
        		sumTimer += pd.get(i).getArbeidsTimer();
    		}
    		System.out.println("Totalt arbeids timer er: " + sumTimer);


        } finally {
        	em.close();
        }
	}
	public void slettProsjekt(int id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			
			Prosjekt p = em.find(Prosjekt.class, id);
			
			List<ProsjektDeltakelse> liste = p.getProsjektDeltakelse();
		
			if (liste.size() > 0) {
				System.out.println("Kan ikkje slette for prosjektet inneholder ansatte.");
			} else {
				em.remove(p);
				System.out.println("Sletting utført");
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
}
