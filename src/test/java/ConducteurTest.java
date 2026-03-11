import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConducteurTest {

    private Conducteur conducteur;

    @BeforeEach
    public void setUp() {
        conducteur = new Conducteur("John", 20);
    }

    @Test
    public void testEstAdulte() {
        assertThat(conducteur.estAdulte()).isTrue();
    }

    // un conducteur de 16 ans ne devrait pas être considéré adulte
    @Test
    public void testEstAdulteMineur() {
        Conducteur mineur = new Conducteur("Paul", 16);
        assertThat(mineur.estAdulte()).isFalse();
    }

    // demarrerVoiture ne modifie pas la vitesse, la voiture doit rester à 0
    @Test
    public void testDemarrerVoiture() {
        Voiture voiture = new Voiture("Sedan", "Bleu");
        conducteur.demarrerVoiture(voiture);
        assertThat(voiture.getVitesse()).isEqualTo(0);
    }

    // idem pour arreterVoiture
    @Test
    public void testArreterVoiture() {
        Voiture voiture = new Voiture("Sedan", "Bleu");
        conducteur.arreterVoiture(voiture);
        assertThat(voiture.getVitesse()).isEqualTo(0);
    }

    @Test
    public void testChangerVitesse() {
        Voiture voiture = new Voiture("Sedan", "Bleu");
        conducteur.changerVitesse(voiture, 80);
        assertThat(voiture.getVitesse()).isEqualTo(80);
    }

    // réduire la vitesse fonctionne aussi
    @Test
    public void testChangerVitesseRalentir() {
        Voiture voiture = new Voiture("Sedan", "Bleu");
        conducteur.changerVitesse(voiture, 80);
        conducteur.changerVitesse(voiture, 40);
        assertThat(voiture.getVitesse()).isEqualTo(40);
    }
}
