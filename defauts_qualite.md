# Défauts de qualité identifiés – carApp

---

## Conducteur.java

1. **Seuil d'âge adulte incorrect** : `estAdulte()` retourne `true` si `age >= 10`,
   alors que l'âge légal pour conduire est 18 ans.

2. **Variable inutilisée** : `int vitesseActuelle` est déclarée dans `changerVitesse()`
   mais n'est jamais utilisée → code mort.

3. **Nommage incohérent des paramètres du constructeur** : les paramètres s'appellent
   `name` et `years` (anglais) alors que les attributs sont `nom` et `age` (français).
   Le reste du code est en français → mélange de langues.

4. **`demarrerVoiture()` et `arreterVoiture()` ne font rien sur la voiture** :
   le paramètre `Voiture voiture` est reçu mais jamais utilisé → paramètre fantôme.

5. **Pas de vérification dans `changerVitesse()`** : aucune vérification que
   `nouvelleVitesse` est dans les bornes valides (0–120), ce qui peut provoquer
   une boucle infinie si la vitesse cible dépasse 120.

---

## Voiture.java

6. **Responsabilité mélangée (SRP violé)** : les méthodes `accelerer()` et `ralentir()`
   mélangent la logique métier (modifier la vitesse) et l'affichage console
   (`System.out.println`) → difficile à tester et à maintenir.

7. **Pas de getters pour `modele` et `couleur`** : les attributs `modele` et `couleur`
   sont privés sans accesseurs, ce qui empêche toute consultation de l'état de la voiture
   depuis l'extérieur.

8. **Pas de setter / méthode de réinitialisation** : aucun moyen de changer le modèle,
   la couleur ou de remettre la vitesse à 0 après un arrêt.

9. **Valeurs magiques** : `10`, `120`, `0` sont codées en dur dans `accelerer()` et
   `ralentir()`. Il faudrait des constantes nommées :
   `ACCELERATION_STEP`, `VITESSE_MAX`, etc.

10. **Nommage incohérent du constructeur** : paramètres `model` et `color` (anglais)
    alors que les attributs sont `modele` et `couleur` (français).

---

## Application.java

11. **Pas de package déclaré** : aucune instruction `package` dans aucune classe →
    toutes les classes sont dans le package par défaut, mauvaise pratique en Java.

12. **Couplage fort dans `main()`** : `Application` instancie et orchestre directement
    tout, sans aucune abstraction. Difficile à faire évoluer.

---

## Tests (ConducteurTest, VoitureTest, VoitureAppTest)

13. **Tests incomplets dans `ConducteurTest`** : `testDemarrerVoiture()` et
    `testArreterVoiture()` ne contiennent aucune assertion (`// How to assert...`) →
    les tests passent toujours sans rien vérifier.

14. **`VoitureAppTest.testMain()` est vide** : le test ne contient aucune assertion,
    il ne teste rien du tout.

15. **Pas de test des cas limites** :
    - Pas de test pour `estAdulte()` avec un conducteur mineur.
    - Pas de test pour `accelerer()` au-delà de 120 km/h.
    - Pas de test pour `ralentir()` en dessous de 0.
    - Pas de test pour `changerVitesse()` avec une vitesse invalide.

16. **`VoitureTest.testRalentir()` trop dépendant** : le test appelle `accelerer()`
    avant `ralentir()`, ce qui crée une dépendance entre deux tests unitaires
    → chaque test devrait être indépendant.



