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

	private Caisse caisse;

	private FileChariots fileChariots;
	private List<Rayon> listRayon;
	private static final int NB_MAX_ART = 6;

	public Client(int id, FileChariots fileChariots, List<Rayon> listRayon,
			Caisse caisse) {
		this.id = id;
		this.fileChariots = fileChariots;
		this.listRayon = listRayon;
		this.caisse = caisse;
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

		faireCourses(listRayon);// faire les courses

		passageEnCaisse();// Passage a la caisse

		fileChariots.remettreChariot();// Restitue le chariot
		System.out.println("Client n" + id + " a termier ses courses.");
	}

	/**
	 * Faire les course (prise d'article dans les differents rayons en fonction
	 * d'un liste de course)
	 */
	private void faireCourses(List<Rayon> listRayon) {
		int nbArt = 0;
		for (int j = 0; j < listRayon.size(); j++) {
			System.out
					.println("Client ID" + getIdClient() + " dans rayon " + j);
			nbArt = listeCourse.get(j);
			Rayon rayon = listRayon.get(j);
			System.out.println("Client " + id + " est dans le rayon"
					+ rayon.getid() + ". Il a " + nbArt + " art a prendre");
			for (int i = 0; i < nbArt; i++) {
				System.out.println(" Le client n"+id+" est en train de: ");
				rayon.priseArticle();//tentative de prise d'article
			}
		}
	}

	/**
	 * Effectue le passage en caisse du client
	 */
	private void passageEnCaisse() {
		// Attendre que la caisse soit libre
		Employe employe = caisse.passerEnCaisse();

		// Depot des produits
		for (int i = 0; i < listeCourse.size(); i++) {
			int nbArti = listeCourse.get(i);
			for (int j = 0; j < nbArti; j++) {
				System.out.println("CLIENT " + getIdClient()
						+ " : dépôt produit d'id " + i);
				caisse.deposer(i);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		// Marqueur suivant
		System.out.println("CLIENT " + getIdClient()
				+ " : dépôt marqueur CLIENT_SUIVANT");
		caisse.deposer(Caisse.MARQUEUR_CLIENT_SUIVANT);

		System.out.println("CLIENT " + getIdClient()
				+ " : attente de l'employé");
		// Attendre que l'employe ait fini de passer les produits
		try {
			employe.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("CLIENT " + getIdClient()
				+ " : synchronisation avec employé OK");

		// Effectuer le réglement
		employe.effectuerReglement();
		System.out.println("CLIENT " + getIdClient()
				+ " : paiement effectué");

		// Libérer la caisse pour le prochain client
		caisse.liberer();
		System.out.println("CLIENT " + getIdClient()
				+ " : caisse libérée");
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
