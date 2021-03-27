package no.hvl.dat107;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ProsjektDeltakelseDAO {
	private EntityManagerFactory emf;
	
	public ProsjektDeltakelseDAO(){
		 emf = Persistence.createEntityManagerFactory("persistence");
	}
	
	public void ProsjektDeltakelseDAO(Ansatt ansatt, Prosjekt prosjekt, Integer arbeidsTimer, String rolle) {
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
        	tx.begin();
        	ProsjektDeltakelse pd = new ProsjektDeltakelse(ansatt, prosjekt, arbeidsTimer, rolle);
        	em.persist(pd);
        	tx.commit();
        } finally {
        	em.close();
        }
	}
	
	//Ingen test metode for denne
	public void leggTilTimerDAOId(int id, Integer nyeTimer) {
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
        	tx.begin();
        	ProsjektDeltakelse p = em.find(ProsjektDeltakelse.class, id);
        	p.setArbeidsTimer(p.getArbeidsTimer() + nyeTimer);
        	System.out.println(p.toString());
        	tx.commit();
        } finally {
        	em.close();
        }
	}
	
	
	public void leggTilTimerDAO(Ansatt ansatt, Prosjekt prosjekt, Integer nyeTimer) {
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
        	tx.begin();
        	ProsjektDeltakelse p = getProsjektDeltakelse(ansatt, prosjekt);
        	p.setArbeidsTimer(p.getArbeidsTimer() + nyeTimer);
        	em.merge(p);
        	System.out.println("Nye Arbeidstimer: " + p.getArbeidsTimer());
        	tx.commit();
        } finally {
        	em.close();
        }
	}
	
	public ProsjektDeltakelse getProsjektDeltakelse(Ansatt ansatt, Prosjekt prosjekt) {
		EntityManager em = emf.createEntityManager();

        try {
			TypedQuery<ProsjektDeltakelse> query = em.createQuery("SELECT t FROM ProsjektDeltakelse t WHERE t.ansNr = :ansNr AND t.prosjektNr = :prosjektNr", ProsjektDeltakelse.class);
			
			query.setParameter("ansNr", ansatt);
			query.setParameter("prosjektNr", prosjekt);
			
			List<ProsjektDeltakelse> a = query.getResultList();
			return a.get(0);
        } finally {
        	em.close();
        }
	}
	
	
}
