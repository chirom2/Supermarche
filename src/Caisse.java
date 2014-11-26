

import java.util.concurrent.Semaphore;

public class Caisse {

    public static final int MARQUEUR_CLIENT_SUIVANT = -1;
    private static final int TAILLE_TAPIS = 5;

    private Semaphore libre = new Semaphore(1, true); // passage des clients en caisse FIFO
    private int[] tapis = new int[TAILLE_TAPIS];
    private int nbProduit = 0;
    private int idDepose = 0;
    private int idPrise = 0;

    public Caisse() {
    }

    public Employe passerEnCaisse() {
        try {
            libre.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Passage en caisse");
        Employe e = new Employe(this);
        e.start();
        return e;
    }

    public void liberer() {
        System.out.println("Libération de la caisse");
        libre.release();
    }

    public synchronized void deposer(int idProduit) {
        if (nbProduit == TAILLE_TAPIS) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Depot produit " + idProduit);
        nbProduit++;
        tapis[idDepose] = idProduit;
        idDepose = (idDepose + 1) % TAILLE_TAPIS;
        notify();
    }

    public synchronized int prendre() {
        if (nbProduit == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nbProduit--;
        int idProduit = tapis[idPrise];
        idPrise = (idPrise + 1) % TAILLE_TAPIS;
        System.out.println("Prise produit " + idProduit);
        notify();
        return idProduit;
    }
}

