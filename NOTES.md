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

---

## Question 4 – Analyse CheckStyle (Conducteur.java – 21 warnings)

CheckStyle a remonté 21 avertissements sur `Conducteur.java`. En regardant ça de plus près,
on peut les regrouper en 5 grands types de problèmes :

---

### 1. Javadoc absent partout

C'est de loin la catégorie la plus représentée. Aucun attribut, aucune méthode, aucun
constructeur n'a de commentaire Javadoc. CheckStyle le signale systématiquement :
- attributs `nom` et `age` (lignes 2 et 3)
- constructeur et toutes les méthodes publiques (lignes 5, 10, 14, 22, 26)

Et en plus, il n'y a pas de fichier `package-info.java` parce qu'il n'y a tout simplement
pas de package déclaré (ligne 1).

---

### 2. Paramètres qui devraient être `final`

CheckStyle signale que tous les paramètres de méthodes devraient être déclarés `final`
pour éviter qu'on les réassigne par erreur. Ça concerne :
- `name` et `years` dans le constructeur
- `voiture` dans `demarrerVoiture`, `arreterVoiture` et `changerVitesse`
- `nouvelleVitesse` dans `changerVitesse`

6 occurrences en tout.

---

### 3. DesignForExtension – problème d'héritage

La classe `Conducteur` n'est pas `final`, et aucune de ses méthodes ne l'est non plus.
CheckStyle considère ça comme un risque : si quelqu'un hérite de la classe et surcharge
une méthode, il n'a aucune indication sur ce qu'il peut ou ne peut pas faire.
La solution serait soit de passer la classe en `final`, soit d'ajouter un Javadoc
qui explique le comportement attendu en cas d'héritage.

Ça touche les 4 méthodes publiques : `estAdulte`, `demarrerVoiture`, `arreterVoiture`,
`changerVitesse`.

---

### 4. Nombre magique

Le `10` dans `age >= 10` (ligne 11) est signalé comme "magic number". Il faudrait
le remplacer par une constante avec un nom parlant, genre `AGE_MINIMUM_CONDUITE`.
Ce qui est d'autant plus pertinent qu'en plus, la valeur est fausse (18 ans, pas 10).

---

### 5. Lignes trop longues

Deux lignes dépassent les 80 caractères autorisés :
- ligne 18 : 83 caractères
- ligne 27 : 90 caractères

---

### Est-ce que ça concorde avec mon analyse manuelle ?

Dans l'ensemble, oui. Les points que j'avais relevés à la main se retrouvent bien dans
le rapport CheckStyle :

- Le `10` dans `estAdulte()` → retrouvé via `MagicNumber` (point 1 et 9 de mon analyse)
- L'absence de package → retrouvé via `JavadocPackage` (point 11)
- Les paramètres `name`/`years` mal nommés → retrouvés via `FinalParameters` (point 3)
- Le paramètre `voiture` inutilisé → aussi via `FinalParameters` (point 4)
- La ligne 27 trop longue correspond bien à la déclaration de `vitesseActuelle`
  qui est inutilisée (point 2)

Ce que j'avais raté en lisant le code :
- L'absence totale de Javadoc → CheckStyle le détecte automatiquement, moi pas
- Le problème de `DesignForExtension` → c'est plus une règle de style qu'un bug,
  difficile à voir sans outil

**En résumé** : CheckStyle confirme bien ce que j'avais trouvé, et en plus il
attrape les problèmes de forme (documentation, style) que je n'avais pas pensé
à vérifier à la main.
