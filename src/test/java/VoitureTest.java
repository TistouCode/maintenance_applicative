import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VoitureTest {

    private Voiture voiture;

    @BeforeEach
    public void setUp() {
        voiture = new Voiture("Sedan", "Bleu");
    }

    @Test
    public void testAccelerer() {
        voiture.accelerer();
        assertThat(voiture.getVitesse()).isEqualTo(10);
    }

    // on s'assure que la vitesse ne dépasse pas 120
    @Test
    public void testAccelererVitesseMax() {
        for (int i = 0; i < 13; i++) {
            voiture.accelerer();
        }
        assertThat(voiture.getVitesse()).isEqualTo(120);
    }

    @Test
    public void testRalentir() {
        voiture.accelerer();
        voiture.ralentir();
        assertThat(voiture.getVitesse()).isEqualTo(0);
    }

    // ralentir quand la voiture est déjà à l'arrêt ne doit pas passer en négatif
    @Test
    public void testRalentirDejaArrete() {
        voiture.ralentir();
        assertThat(voiture.getVitesse()).isEqualTo(0);
    }
}
