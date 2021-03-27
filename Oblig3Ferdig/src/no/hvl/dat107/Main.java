package no.hvl.dat107;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		//persistence
		AnsattDAO aadao = new AnsattDAO();
		AvdelingDAO avdao = new AvdelingDAO();
		ProsjektDAO prdao = new ProsjektDAO();
		ProsjektDeltakelseDAO pddao = new ProsjektDeltakelseDAO();
		
		int igjenn = 1;
		while (igjenn==1) {
			
			System.out.println("1: Søk på id, 2: Søk på brukernavn, 3: Skriv ut alle, \n"
					+ "4: Endre lonn, 5: Ny ansatt, 6: Ansatte i Avdeling, 7: Endre avdeling \n"
					+ "8: Ny avdeling, 9: Nytt Prosjekt, 10: Registert prosjektdeltagelse\n"
					+ "11: Registrer timer, 12: Slett Ansatt, 13: Skriv ut prosjekt, 14: slett prosjekt.");
			Integer val = scanner.nextInt();
			scanner.nextLine();
			
			switch (val) {
			case 1:
				System.out.println("Id på person du vil søke etter");
				int id = scanner.nextInt();
				scanner.nextLine();
				aadao.finnAnsattMedId(id).skrivUt();
				break;
			case 2:
				System.out.println("Brukarnamn på person du vil søke etter");
				String brukernavn = scanner.nextLine();
				aadao.finnAnsattMedBrukernavn(brukernavn).skrivUt();
				break;
			case 3:
				aadao.skrivUtAlle();
				break;
			case 4:
				System.out.println("Id på person du vil endre lønn på");
				id = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Ny lønn");
				double nylonn = scanner.nextDouble();
				scanner.nextLine();
				aadao.setLonn(id, nylonn);
				break;
			case 5:
				System.out.println("Brukernavn");
				brukernavn = scanner.nextLine();

				System.out.println("fornavn");
				String fornavn = scanner.nextLine();

				System.out.println("etternavn");
				String etternavn = scanner.nextLine();
				
				LocalDate ansettelsesdato = LocalDate.now();

				System.out.println("maanedslonn");
				double maanedslonn = scanner.nextDouble();
				scanner.nextLine();
				
				System.out.println("avdelingsnNr");
				int avdelingsnr = scanner.nextInt();
				Avdeling stilling = avdao.finnAvdelingMedId(avdelingsnr);
				scanner.nextLine();
				
				aadao.nyAnsatt(brukernavn, fornavn, etternavn, ansettelsesdato, maanedslonn, stilling);
				break;
			case 6:
				System.out.println("avdelingsNr");
				avdelingsnr = scanner.nextInt();
				avdao.skrivUtAnsatteIEinAvdeling(avdelingsnr);
				scanner.nextLine();
				break;
			case 7:
				System.out.println("ansatt nr");
				avdelingsnr = scanner.nextInt();
				scanner.nextLine();
				
				System.out.println("Id på avdeling du vil endre til");
				id = scanner.nextInt();
				scanner.nextLine();
				Avdeling a = avdao.finnAvdelingMedId(id);

				aadao.byttAvdeling(a, avdelingsnr);
				break;
			case 8:
				System.out.println("Namn på ny avdeling");
				String nyAvdeling = scanner.nextLine();
				
				System.out.println("ansatt nr til ny avdelingssjef");
				int ansattNr = scanner.nextInt();
				scanner.nextLine();

				aadao.nyAvdeling(nyAvdeling, ansattNr);
				break;
			case 9:
				System.out.println("Namn på nytt prosjekt");
				String nyProsjekt = scanner.nextLine();
				
				System.out.println("Beskrivelse:");
				String beskrivelse = scanner.nextLine();
				prdao.nyttProsjekt(nyProsjekt, beskrivelse);
				break;
			case 10:
				System.out.println("ansatt nr som skal registeres på prosjekt");
				Ansatt ansatt = aadao.finnAnsattMedId(scanner.nextInt());
				scanner.nextLine();
				
				System.out.println("Prosjektnr til registrering av ny ansatt");
				Prosjekt prosjekt = prdao.finnProsjektMedId(scanner.nextInt());
				scanner.nextLine();
				
				System.out.println("Antall arbeidstimer");
				Integer arbeidsTimer = scanner.nextInt();
				scanner.nextLine();
				
				System.out.println("Rolle:");
				String rolle = scanner.nextLine();
				
				pddao.ProsjektDeltakelseDAO(ansatt, prosjekt, arbeidsTimer, rolle);
				break;
			case 11:
				System.out.println("ansatt nr som skal ha fleire timer");
				ansatt = aadao.finnAnsattMedId(scanner.nextInt());
				scanner.nextLine();
				
				System.out.println("Prosjektnr som ansatt har jobba på");
				prosjekt = prdao.finnProsjektMedId(scanner.nextInt());
				scanner.nextLine();
				
				System.out.println("Nye arbeidstimer");
				arbeidsTimer = scanner.nextInt();
				scanner.nextLine();
				
				pddao.leggTilTimerDAO(ansatt, prosjekt, arbeidsTimer);
				break;
			case 12:
				System.out.println("Id på person du vil Slette");
				id = scanner.nextInt();
				scanner.nextLine();
				aadao.slettAnsatt(id);
				break;
			case 13:
				System.out.println("Id på prosjekt du vil skrive ut");
				id = scanner.nextInt();
				scanner.nextLine();
				prdao.skrivUtProsjekt(id);
				break;
			case 14:
				System.out.println("Id på prosjekt du vil Slette");
				id = scanner.nextInt();
				scanner.nextLine();
				prdao.slettProsjekt(id);
				break;
			default:
				System.out.println("Ugyldig verdi");
			}
						
			System.out.println("Trykk 1 for å forsette og 0 for å avslutte");
			igjenn = scanner.nextInt();
			scanner.nextLine();
		}
		
		scanner.close();
	}
}
