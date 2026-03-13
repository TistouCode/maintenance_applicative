public class Voiture {
    private String modele;
    private String couleur;
    private int vitesse;

    private static final int PAS_VITESSE = 10;
    private static final int VITESSE_MAX = 120;

    public Voiture(final String model, final String color) {
        this.modele = model;
        this.couleur = color;
        this.vitesse = 0;
    }

    public int getVitesse() {
        return this.vitesse;
    }

    public void accelerer() {
        if (vitesse + PAS_VITESSE <= VITESSE_MAX) {
            vitesse += PAS_VITESSE;
            afficherEtat();
        } else {
            System.out.println("La vitesse maximale est atteinte.");
        }
    }

    public void ralentir() {
        if (vitesse - PAS_VITESSE >= 0) {
            vitesse -= PAS_VITESSE;
            afficherEtat();
        } else {
            System.out.println("La voiture est déjà à l'arrêt.");
        }
    }

    private void afficherEtat() {
        System.out.println("Modèle : " + modele);
        System.out.println("Couleur : " + couleur);
        System.out.println("Vitesse actuelle : " + vitesse);
    }
}
