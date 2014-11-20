import java.util.ArrayList;
import java.util.List;

public class Supermarche {

	/**
	 * NB ELEM PAR CHGT nombre dâ€™exemplaires de chaque article que le chef de
	 * rayon peut transporter dans sa tourn Ì�ee de remplissage des rayons
	 */
	static final int NB_ELEM_PAR_CHGT = 5;
	/**
	 * RAYON STOCK INIT : le stock initial present dans les rayons `a
	 * lâ€™ouverture du magasin
	 */
	static final int RAYON_STOCK_INIT = 10;
	/**
	 * RAYON STOCK MAX : nombre maximum dâ€™exemplaires dâ€™un produit dans un
	 * rayon ; â€“ * Supermarche.TPS* : les temps, en ms, des diverses op
	 * Ì�erations d Ì�ecrites dans le sujet pour lesquelles le temps nâ€™est pas
	 * n Ì�eglig Ì�e
	 */
	static final int RAYON_STOCK_MAX = 10;

	/**
	 * TPS* : les temps, en ms, des diverses op Ì�erations d Ì�ecrites dans le
	 * sujet pour lesquelles le temps nâ€™est pas neglige
	 */
	static final int TPS_REMPLIR_RAYON = 500;
	static final int TPS_DEPLACEMENT = 200;

	/**
	 * TAILLE TAPIS : nombre maximum dâ€™objets pr Ì�esents sur le tapis de
	 * caisse
	 */
	static final int TAILLE_TAPIS = 5;

	/**
	 * NB CHARIOTS : nombre de chariots dans la file `a lâ€™ouverture du
	 * magasin.
	 */
	static final int NB_CHARIOTS = 8;

	/**
	 * NB_RAYON : nombre maximum de rayon dans le supermarche
	 */
	static final int NB_RAYON = 5;
	
	/**
	 * Nombre de client présent dans le supermarché
	 */
	static final int NB_CLIENT = NB_CHARIOTS * 2;

	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileChariots fileChariot = new FileChariots(NB_CHARIOTS);//Initialisation chariots
		List<Rayon> listRayon = new ArrayList<Rayon>();
		for(int i=0; i < NB_RAYON; i++){
			listRayon.add(new Rayon(i, RAYON_STOCK_INIT));//Initialisation rayon
		}		
		ChefRayon chefRayon = new ChefRayon(listRayon, NB_ELEM_PAR_CHGT);
		chefRayon.start();	
		List<Client> clients = new ArrayList<Client>();
		for (int i = 0; i < NB_CLIENT; i++) {
			clients.add(new Client(i, fileChariot,listRayon));//Creation des clients
			System.out.println(clients.get(i).getListeCourse().toString());
			clients.get(i).start();
		}

		
		
		for (int i = 0; i < NB_CLIENT; i++) {
			try {
				clients.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Nombre chariot final" + fileChariot.getNb());
	}

}
