import java.util.concurrent.Semaphore;

public class Caisse {

	public static final int MARQUEUR_CLIENT_SUIVANT = -1;
	private static final int TAILLE_TAPIS = 5;

	private Semaphore libre = new Semaphore(1, true); // passage des clients en
														// caisse FIFO
	/**
	 * Tapis représenté par un buffer circulaire de taille
	 * {@value #TAILLE_TAPIS}
	 */
	private int[] tapis = new int[TAILLE_TAPIS];
	/**
	 * nombre de produits présents sur le tapis
	 */
	private int nbProduit = 0;
	/**
	 * index où sera déposé le prochain produit
	 */
	private int idDepose = 0;
	/**
	 * index où prendre le prochain produit
	 */
	private int idPrise = 0;

	public Caisse() {
	}

	/**
	 * Représente l'arrivée en caisse d'un client Bloque le thread si aucune
	 * place n'est disponible
	 * 
	 * @return le thread Employé qui passera le produit et avec qui il faudra se
	 *         synchroniser pour effectuer le paiement.
	 */
	public Employe passerEnCaisse() {
		try {
			libre.acquire();// Un seul client à la fois.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("CAISSE : Passage en caisse");
		Employe e = new Employe(this);// Démarrage d'un nouveau thread Employe à
										// chaque client
		e.start();
		return e;
	}

	/**
	 * Libère la place à la caisse
	 */
	public void liberer() {
		System.out.println("CAISSE : Libération de la caisse");
		libre.release();// Libération de la caisse
	}

	/**
	 * Dépose un produit sur le tapis. Bloque le thread si il n'y a pas de place
	 * sur le tapis.
	 * 
	 * @param idProduit
	 */
	public synchronized void deposer(int idProduit) {
		if (nbProduit == TAILLE_TAPIS) {// Evite l'écrasement de cases non-lues
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("CAISSE : Depot produit " + idProduit);
		nbProduit++;
		tapis[idDepose] = idProduit;
		idDepose = (idDepose + 1) % TAILLE_TAPIS;
		notify();
	}

	/**
	 * Prendre un produit sur le tapis Bloque le thread si le tapis est vide.
	 * 
	 * @return id du produit pris
	 */
	public synchronized int prendre() {
		if (nbProduit == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		nbProduit--;
		int idProduit = tapis[idPrise];
		idPrise = (idPrise + 1) % TAILLE_TAPIS;
		System.out.println("CAISSE : Prise produit " + idProduit);
		notify();
		return idProduit;
	}
}
