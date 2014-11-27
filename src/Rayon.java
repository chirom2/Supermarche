public class Rayon {

	private int id;// Différencie le type d'article stocke dans le rayon
					// (SUCRE, FARINE,...)
	private int nbCurrent;
	private int nbMaxArticle;

	public Rayon(int id, int nbMaxArticle) {
		super();
		this.id = id;
		this.nbMaxArticle = nbMaxArticle;
		this.nbCurrent = nbMaxArticle;
	}

	/**
	 * Prise d'un des article du rayon
	 */
	public synchronized void priseArticle() {

		while (nbCurrent <= 0) {
			try {
				System.out.println("d'attendre dans rayon n" + id);
				wait();// Si pas suffisament d'article, on attend
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Prendre article dans Rayon n " + id
				+ " (Nb article courant = " + nbCurrent + ")");
		nbCurrent--;
		notifyAll();
		System.out.println("Fin prise Rayon n " + id
				+ " (Nb article courant = " + nbCurrent + ")");
	}

	/**
	 * @param etatChg
	 *            Etat du chargement du chef de rayon Recharger le contenu d'un
	 *            rayon
	 */
	public synchronized int miseEnRayon(int etatChg) {
		System.out.println("Mise article dans rayon n" + id
				+ " (Nb article courant = " + nbCurrent + ")");
		while (nbCurrent < nbMaxArticle && etatChg > 0) {// Rechargement du
															// stocke du rayon
															// si le stock du
															// chef le permet et
															// nb art
															// issuffisant
			nbCurrent++;
			etatChg--;
		}
		notifyAll();
		System.out.println("Fin mise en rayon n" + id
				+ " (Nb article courant = " + nbCurrent + ")");
		return etatChg;
	}

	/**
	 * 
	 * @return nombre d'article courant dans le rayon
	 */
	public int getNbCurrent() {
		return nbCurrent;
	}

	public int getMaxArticles() {
		return nbMaxArticle;
	}

	public int getid() {
		return id;
	}

}
