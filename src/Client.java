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
	
	private static final int NB_MAX_ART = 6;
		
	public Client(int id, FileChariots fileChariots, List<Rayon> listRayon) {
		this.id = id;
		this.fileChariots = fileChariots;
		this.listRayon = listRayon;
		Random rand = new Random();
		this.listeCourse = new HashMap<Integer, Integer>();
		for (int i = 0; i < listRayon.size(); i++) {// Generation aleatoire liste de course
			int nbAleatoire = rand.nextInt(NB_MAX_ART * 2);
			listeCourse.put(i, nbAleatoire);
		}
	}

	/**
	 * Demarre le thread
	 */
	@Override
	public void run() {
		System.out.println("Client n" + id + ".");		
		fileChariots.prendreChariot();// Prend un charriot	
		int nbArt=0;		
		for(int j=0; j<5; j++){
			System.out.println("Client ID"+getIdClient()+" Key"+j);
			if(j < listeCourse.size()){
				nbArt=listeCourse.get(j);
				Rayon rayon = listRayon.get(j);
				System.out.println("Client "+id+" dans Rayon"+rayon.getid()+" "+nbArt+" art a prendre");
				if(nbArt != 0){
					for(int i=0; i<nbArt; i++){
						rayon.priseArticle();
					}
				}
			}			
		}
		// TODO Passe a la caisse
		
		fileChariots.remettreChariot();// Restitue le chariot
		System.out.println("Client n" + id + " Fin des courses");
	}
	/**
	private void attendre(long tps){
		try {
			Thread.sleep((tps));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

	
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
