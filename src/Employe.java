public class Employe extends Thread {
	public Tapis tapis;
	public boolean presenceClient;
	public boolean running= true;
	public Employe(Tapis tapis){
		this.tapis = tapis;
	}
	
	
	public void arret(){
		running = false;
	}
	
	@Override
	public void run(){
		while(running){
			tapis.prendreArt();//GERER l'arret de ce thread
			if( !Supermarche.presence){
				arret();
			}		
		}
		System.out.println("Fin EMPLOYE!!!!!!!!!!!!!!!");
	}

}
