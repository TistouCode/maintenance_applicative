public final class Conducteur {
    private String nom;
    private int age;
    private static final int AGE_MINIMUM_CONDUITE = 18;

    public Conducteur(final String name, final int years) {
        this.nom = name;
        this.age = years;
    }

    public boolean estAdulte() {
        return age >= AGE_MINIMUM_CONDUITE;
    }

    public void demarrerVoiture(final Voiture voiture) {
        if (estAdulte()) {
            System.out.println(nom + " démarre la voiture.");
        } else {
            final String message = "Le conducteur n'est pas assez âgé pour conduire.";
            System.out.println(message);
        }
    }

    public void arreterVoiture(final Voiture voiture) {
        System.out.println(nom + " arrête la voiture.");
    }

    public void changerVitesse(final Voiture voiture, final int nouvelleVitesse) {
        System.out.println(
            nom + " change la vitesse de la voiture à " + nouvelleVitesse
        );
        if (voiture.getVitesse() >= nouvelleVitesse) {
            while (voiture.getVitesse() > nouvelleVitesse) {
                voiture.ralentir();
            }
        } else {
            while (voiture.getVitesse() < nouvelleVitesse) {
                voiture.accelerer();
            }
        }
    }
}
