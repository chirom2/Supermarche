public class FileChariots {

	/**
	 * Nombre de chariot de la file
	 */
	private int nb;

	public FileChariots(int nb) {
		this.nb = nb;
	}

	/**
	 * Prise de chariot
	 */
	public synchronized void prendreChariot() {
		if (nb == 0) {
			try {
				wait();// Attente si pas suffisament de chariot disponible
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Prise Chariot");
		nb--;
	}

	/**
	 * Remettre un chariot dans la file, prévient les thread en attente
	 */
	public synchronized void remettreChariot() {
		System.out.println("Remise chariot");
		nb++;
		notify();
	}

	public int getNb() {
		return nb;
	}

}
