import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Client extends Thread {
	/**
	 * id: Identifiant du client
	 */
	private int id;

	/**
	 * listeCourse: identifiant du client
	 */
	private HashMap<Integer, Integer> listeCourse;
	/**
	 * Nombre d'article maximum sur la liste d'un client
	 */

	private FileChariots fileChariots;
	private List<Rayon> listRayon;
	private Tapis tapis;
	private static final int NB_MAX_ART = 6;

	public Client(int id, FileChariots fileChariots, List<Rayon> listRayon,
			Tapis tapis) {
		this.id = id;
		this.fileChariots = fileChariots;
		this.listRayon = listRayon;
		this.tapis = tapis;

		Random rand = new Random();
		this.listeCourse = new HashMap<Integer, Integer>();
		for (int i = 0; i < listRayon.size(); i++) {// Generation aleatoire
													// liste de course
			int nbAleatoire = rand.nextInt(NB_MAX_ART * 2);
			listeCourse.put(i, nbAleatoire);
		}
	}

	/**
	 * Demarre le thread
	 */
	@Override
	public void run() {
		System.out.println("Client n" + id + " part faire les courses.");
		fileChariots.prendreChariot();// Prend un charriot
		
		faireCourses(listeCourse, listRayon);//faire le course
		
		passageEnCaisse(listeCourse);// Passage a la caisse

		fileChariots.remettreChariot();// Restitue le chariot
		System.out.println("Client n" + id + " a termier ses courses.");
	}

	/**
	 * Faire les course (prise d'article dans les differents rayons en fonction
	 * d'un liste de course)
	 */
	private void faireCourses(HashMap<Integer, Integer> listeCourse,List<Rayon> listRayon) {
		int nbArt=0;
		for (int j = 0; j < listRayon.size(); j++) {
			System.out.println("Client ID" + getIdClient() + " Key" + j);
			if (j < listeCourse.size()) {
				nbArt = listeCourse.get(j);
				Rayon rayon = listRayon.get(j);
				System.out.println("Client " + id + " est dans le rayon"
						+ rayon.getid() + ". Il a " + nbArt + " art a prendre");
				if (nbArt != 0) {
					for (int i = 0; i < nbArt; i++) {
						rayon.priseArticle();
					}
				}
			}
		}
	}

	/**
	 * Effectue le passage en caisse du client
	 * 
	 * @param listeCourse
	 */
	private void passageEnCaisse(HashMap<Integer, Integer> listeCourse) {
		try {
			tapis.sem.acquire();// Je prends la main sur le tapis
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int j = 0; j < listeCourse.size(); j++) {
			int nbArt = listeCourse.get(j);
			System.out.println("Client n" + id + " nb d'article a passe "
					+ nbArt);
			for (int i = 0; i < nbArt; i++)
				tapis.deposerArt(j);// j correspond au rayon et donc a l'id du
									// produit contenu
		}
		tapis.deposerArt(-1);// Marqueur client suivant
		tapis.sem.release();
	}

	public int getIdClient() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return liste de course du client
	 */
	public HashMap<Integer, Integer> getListeCourse() {
		return listeCourse;
	}

}
