import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class ChefRayon extends Thread {
	/**
	 * Nombre de rayon à la charge du chef de rayon
	 */
	List<Rayon> listRayons;

	/**
	 * chargement du chef de rayon: <N rayon, nb article>
	 */
	private HashMap<Integer, Integer> chgt;
	/**
	 * chargement courant du chef de rayon
	 */
	int etatChgt;
	/**
	 * Chargement maximum supporté par le che f de rayon
	 */
	int MaxChgt;

	public ChefRayon(List<Rayon> listRayon, int MaxChgt) {
		this.listRayons = listRayon;
		this.MaxChgt = MaxChgt;
		this.chgt = new HashMap<Integer, Integer>();
		this.setDaemon(true);
	}

	/**
	 * Recharge le contenu du chariot du chef de rayon
	 * 
	 * @return chargement
	 */
	private HashMap<Integer, Integer> faireChargement() {
		for (int i = 0; i < listRayons.size(); i++) {
			chgt.put(i, MaxChgt);
		}
		return chgt;
	}

	@Override
	public void run() {
		System.out.println("Chef de rayon ");
		while (true) {
			attendre(500);// temps de chargement
			chgt = faireChargement();// Fais le chargement apres chaque tour
										// dans les rayons
			ListIterator<Rayon> itRayon = listRayons.listIterator();// Les
																	// rayons a
																	// parcourir
																	// pour le
																	// chef de
																	// rayon
			Rayon r;
			while (itRayon.hasNext()) {
				attendre(200);// deplacement entre deux rayons
				r = itRayon.next();
				int id = r.getid();// Id du rayon courant
				int prod = chgt.get(id);
				System.out
						.println("Chargement Chef rayon avant mise en rayon id "
								+ id + "= " + chgt.toString());
				prod = r.miseEnRayon(prod);// On recharge le rayon
				chgt.put(id, prod);// Mise a jour du chargement
			}
			attendre(200);// Retour a l'entrepot
		}
	}

	private void attendre(long tps) {
		try {
			Thread.sleep(tps);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
