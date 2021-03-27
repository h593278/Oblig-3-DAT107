package no.hvl.dat107;

import java.awt.Font;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

public class AvdelingDAO {
private EntityManagerFactory emf;
	
	public AvdelingDAO(){
		 emf = Persistence.createEntityManagerFactory("persistence");
	}
	
	public Avdeling finnAvdelingMedId(int id) {
		EntityManager em = emf.createEntityManager();
		
        try {
        	
        	return em.find(Avdeling.class, id);
        	
        } finally {
        	em.close();
        }
	}
	public void skrivUtAnsatteIEinAvdeling(int id) {
		EntityManager em = emf.createEntityManager();
		
		try {
			Avdeling avdeling = em.find(Avdeling.class, id);
			List<Ansatt> a = avdeling.getAnsatte();
			System.out.println("Antall ansatte 	i avdelingen er: " + a.size());

			
			for (int i=0; i<a.size(); i++) {
				if (a.get(i).getAnsNr()==avdeling.getSjef_Id().getAnsNr()) {
					System.out.println("Sjef: " + a.get(i).toString());
				} else {
					System.out.println(a.get(i).toString());
				}
			}

		
		} finally {
			em.close();
		}
	}
}
