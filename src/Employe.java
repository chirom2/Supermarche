
public class Employe extends Thread {

    private static final int TEMPS_PASSAGER_PRODUIT = 30;

    private Caisse caisse;

    public Employe(Caisse caisse) {
        this.caisse = caisse;
    }


    @Override
    public void run() {
        System.out.println("Employé START");
        int p;
        while ((p = caisse.prendre()) != Caisse.MARQUEUR_CLIENT_SUIVANT) {
            System.out.println("Passage produit " + p);
            try {
                sleep(TEMPS_PASSAGER_PRODUIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Employé STOP");
    }

    public void effectuerReglement() {
        // todo ???
        System.out.println("Paiement effectué");
    }
}
