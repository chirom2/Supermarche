public class Employe extends Thread {

	/**
	 * Delai pour le passage d'un produit
	 */
	private static final int TEMPS_PASSAGE_PRODUIT = 30;

	private Caisse caisse;

	public Employe(Caisse caisse) {
		this.caisse = caisse;
	}

	@Override
	public void run() {
		System.out.println("EMPLOYE : thread start");
		int p;
		while ((p = caisse.prendre()) != Caisse.MARQUEUR_CLIENT_SUIVANT) {// Prise
																			// des
																			// produits
																			// jusqu'au
																			// marqueur
																			// CLIENT_SUIVANT
			System.out.println("passage produit " + p);
			try {
				/*
				 * ajout d'un delai entre chaque prise de produit non demandé
				 * dans le sujet, le sleep peut être commenté, le programme
				 * s'executera de la même façon
				 */
				sleep(TEMPS_PASSAGE_PRODUIT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("EMPLOYE : thread stop");
	}

	public void effectuerReglement() {
		System.out.println("EMPLOYE : paiement effectué");
	}
}
