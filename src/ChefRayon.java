
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class ChefRayon extends Thread {

	List<Rayon> listRayons;//Nombre de rayon à la charge du chef de rayon
	/**
	 * chargement du chef de rayon: <N rayon, nb article>
	 */
	private HashMap<Integer, Integer> chgt;
	int etatChgt;//chargement courant du chef de rayon
	int MaxChgt;//Chargement maximum supporté par le che f de rayon
		
	public ChefRayon(List <Rayon> listRayon, int MaxChgt) {
		this.listRayons = listRayon;
		this.MaxChgt = MaxChgt;
		this.chgt = new HashMap<Integer,Integer>();
		this.setDaemon(true);

	}

	private HashMap<Integer, Integer> faireChargement(){			
		for(int i=0; i<listRayons.size(); i++ ){
			chgt.put(i, MaxChgt);
		}
		return chgt;
	}
	
	
	@Override
	public void run() {
		System.out.println("Chef de rayon ");
		while (true) {								
			attendre(500);//temps de chargement
			chgt = faireChargement();//Fais le chargement apres chaque tour dans les rayons
												
			ListIterator< Rayon> itRayon = listRayons.listIterator();//Les rayons a parcourir pour le chef de rayon
			Rayon r;
			while(itRayon.hasNext()){
				attendre(200);//deplacement entre deux rayons
				r = itRayon.next();
				int id = r.getid();//Id du rayon courant
				int prod = chgt.get(id);
				System.out.println("Cht Chef rayon avt mise en rayon id "+id+"= "+chgt.toString());
				prod = r.miseEnRayon(prod);//On recharge le rayon
				chgt.put(id, prod);//Mise a jour du chargement
			}	
			attendre(200);//Retour � l'entrepot	
		}
	}
	
	private void attendre(long tps){
		try {
			Thread.sleep(tps);					//Faire le plein si plus de chargement
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
