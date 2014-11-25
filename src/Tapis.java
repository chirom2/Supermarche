import java.util.*;
import java.util.concurrent.Semaphore;

public class Tapis {
	public Vector<Integer> tapis;
	public int tailleTapis;
	public Semaphore sem;

	public Tapis(int tailleTapis) {
		this.tailleTapis = tailleTapis;
		this.tapis = new Vector<Integer>(tailleTapis);
		sem = new Semaphore(1);// mutex
	}

	/**
	 * Permet de prendre un article du tapis
	 */
	public synchronized void prendreArt() {
		while (tapis.isEmpty()) {
			try {
				wait();// Si tapis vide
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		tapis.remove(0);
		notifyAll();
	}

	/**
	 * Permet de deposer un article sur le tapis // Cette action met 20ms a etre
	 * effectuee
	 * 
	 * @param art
	 */
	public synchronized void deposerArt(int art) {
		while (tapis.size() >= tailleTapis) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			wait(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tapis.add(art);
		notifyAll();
	}

}
