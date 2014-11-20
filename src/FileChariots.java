public class FileChariots {

	private int nb;

	public FileChariots(int nb) {
		this.nb = nb;
	}

	public synchronized void prendreChariot() {
		if (nb == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Prise Chariot");
		nb--;
	}

	public synchronized void remettreChariot() {
		System.out.println("Remise chariot");
		nb++;
		notify();
	}

	public int getNb() {
		return nb;
	}

}
