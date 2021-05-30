---
title:  'Lerntagebuch zur Bearbeitung von Blatt 05
author:
- Andreas Wegner (andreas.wegner@fh-bielefeld.de)
- Malte Kanders (malte_theodor.kanders@fh-bielefeld.de)
...

<!--
Führen Sie zu jedem Aufgabenblatt und zum Projekt (Stationen 3-9) ein
Lerntagebuch in Ihrem Team. Kopieren Sie dazu diese Vorlage und füllen
Sie den Kopf entsprechend aus.

Im Lerntagebuch sollen Sie Ihr Vorgehen bei der Bearbeitung des jeweiligen
Aufgabenblattes vom ersten Schritt bis zur Abgabe der Lösung dokumentieren,
d.h. wie sind Sie die gestellte Aufgabe angegangen (und warum), was war
Ihr Plan und auf welche Probleme sind Sie bei der Umsetzung gestoßen und
wie haben Sie diese Probleme gelöst. Beachten Sie die vorgegebene Struktur.
Für jede Abgabe sollte ungefähr eine DIN-A4-Seite Text erstellt werden,
d.h. ca. 400 Wörter umfassen. Wer das Lerntagebuch nur ungenügend führt
oder es gar nicht mit abgibt, bekommt für die betreffende Abgabe 0 Punkte.

Checken Sie das Lerntagebuch mit in Ihr Projekt/Git-Repo ein.

Schreiben Sie den Text mit [Markdown](https://pandoc.org/MANUAL.html#pandocs-markdown).

Geben Sie das Lerntagebuch stets mit ab. Achtung: Wenn Sie Abbildungen
einbetten (etwa UML-Diagramme), denken Sie daran, diese auch abzugeben!

Beachten Sie auch die Hinweise im [Orga "Bewertung der Aufgaben"](pm_orga.html#punkte)
sowie [Praktikumsblatt "Lerntagebuch"](pm_praktikum.html#lerntagebuch).
-->


# Aufgabe

<!--
Bitte hier die zu lösende Aufgabe kurz in eigenen Worten beschreiben.
-->
Im Spiel soll es möglich sein, ein einigermaßen realistisches Fernkampf System
zu nutzen. Beispielsweise nehmen Projektile mit der Zeit an Schadenspunkten ab.
Dazu sollen die Monster auch auf schlaue weise mit diesem Fernkampf System kämpfen.

Eine weitere Aufgabe ist das Project aufzuräumen (Refactor).
Das bedeutet beispielsweise einen durchgehenden Style im gesamten Projekt zu realisieren.

Zuletzt soll eine eigene Hashtable implementiert werden, die alte Kollisionserkennung ersetzt.



# Ansatz und Modellierung

<!--
Bitte hier den Lösungsansatz kurz beschreiben:
-   Wie sollte die Aufgabe gelöst werden?
-   Welche Techniken wollten Sie einsetzen?
-   Wie sah Ihre Modellierung aus (UML-Diagramm)?
-   Worauf müssen Sie konkret achten?
-->

Es soll ein Fernkampfsystem entwickelt werden.
Wir haben bereits eine abstrakte Klasse namens `Item`von der auch schon `ItemWeapon` und `ItemWeaponMelee` erbt.
Wir fügen diesem Vererbungssystem jetzt noch den unterzweig `ItemWeaponRange` hinzu.
Diese Klasse beinhalten zusätzlich die zwei abstrakten Methoden:
- `Projectile spawnProjectile` : welche es der jeweiligen Waffe ermöglicht ein Projectile zu spawnen
- `float getProjectileSpeed` : welche die Geschwindigkeit des Projektil setzt.
Wird nun ein Item genutzt mit der bereits vorhandenen `onUse` Methode wird ein Projektil gespwant.
Von dieser Klasse erben dann normale Fernkampf Waffen. Beispielsweise unser `ItemSimpleBow`.
Eine ergänzende abstrakte Klasse `ItemWeaponMagic` erbt von der `ItemWeaponRange`.
Von hier erben unsere bisher zwei Magischen Stäbe.

Das eigentliche Projektil, welches das Fernkampfsystem ermöglicht, ist ein neu angelegtes Package in Entity.
Die Klasse `Projectile` bildet die Spitze der Hierachie.
Im Prinzip funktioniert diese Klasse ganz ähnlich wie andere Entities.
Sie besitzt einen Startpunkt, eigene `CreatureStats` für Event´s und auch Animationen.
Die Oberklasse schaut durch die `onEntityCollision` ob das Projektil etwas getroffen hat, das ein Entity ist.
Allerdings nicht wenn es der eigene Besitzer ist oder wenn Npc's auf Npc's schießen, hir bricht die Methode ab.
Ist das Ziel ein gewolltes Ziel ruft die Oberklasse `onProjectileImpactCreature` auf.
Hier wird nun der errechnete Schaden aus den Stats direkt an das entsprechende Creature weitergeleitet.
Jedes Projektil erbt aber nicht von `Projectile` direkt sondern erbt je nach Typ von `ProjectileMagic`
für magische Projektile oder `ProjectilePhysical` für normale Projektile.
Hier unterscheiden sich die `DamageType` von den Projektilen.
Magischer Schaden für Magische Waffen.
Physischer Schaden für Physische Waffen.
Zudem sollen magische Waffen auch Statuseffekte auslösen können.
Der `IceBlast` verlangsamt zum beispiel sein Ziel um eine gewisse Zeit.

Jedes von `Projectile` erbende Projektil muss folgende Methoden überschreiben:
- `int getTicksLasting` : Dies bestimmt die Zeit solange ein Projektil existieren soll, außer es trifft ein Entity oder die Wand
- `float getDamgaeDecreasePverTime` : der Wert der über die Zeit bestimmt wieviel weniger Schaden eine Waffe nach einer bestimmten Zeit machen soll.

Refactor:
Das komplette Input System wurde neu aufgesetzt.
Wir benutzen immernoch ein Observer Pattern um die entsprechende Events weiterzuleiten.
Allerdings wurde das gesamte System jetzt auf ein Strategy Pattern umgemünzt.
Dadurch haben wir es geschafft das es in Player beispielsweise keine ewig lange Methode `updateLogic` gibt
die durch aberviele if's läuft.
Dies wurde aber und aber mehr unübersichtlicher und ist teils sehr umständlich zu lesen geworden.
Auch sind Methoden die sich langsam über das Projekt verteilt haben in ein zentrales Package gewandert.
So ist das komplette Input System ein eigener Kern und abgekoppelt von anderen.
Beispielsweise konnte der InputListener aus unserer Main entfernt werden und der QuestDummy (unser Entity zum Interagieren mit Quest Angeboten), enthält ebenfalls keine dem Input betreffende Methoden mehr.
Ausserdem ist es dadurch übersichtlich geworden welche InputEvents wir bereits haben und wir können den Default Eingabe Wert mit ein paar Klicks und ohne zu suchen ändern.

Dazu haben wir jetzt eigene Key's.
Jeder Key trägt ein `String event` wodurch ein entsprechendes Event identifiziert werden kann.
Dazu besitzt ein Key ein `int key` welches das entsprechende Gdx Key entspricht.

Key ist die Oberklasse von der weitere Keys erben.
Wir haben momentan so :
- `Key` : die OberKlasse von der alle Keys erben.
- `KeyJustPressed` : für Keys die nur einmal beim drücken getriggert werden sollen.
- `KeyMouseButton` : für Maus Klicks
- `KeyMovement`: Dies sind Keys die zusätzlich noch einen Vektor für die Bewegung behinhalten. 

Für diese Key's gibt es eine Klasse namens `KeyList`. 
Hier findet sich eine ArrayList mit einer Default eingestellten Liste aus Key's
Man hätte die möglichkeit von dieser Klasse zu erben und entsprechende Key's zu überschreiben um sich eine Modifizierte Liste zu erstellen.

In unserem `DungeonInputHandler`wird durch diese Liste iteriert und ein Event ausgelöst wenn einer der Key's den Boolean 
`isPressed` als true makiert.
Ist es eine `KeyMovement` Taste wird `notifyMovement` gefeuert.
Ist es ein anderer Key wird `notifyEvent` gefeuert.

Dafür gibt es in Player eine HashMap die als SchlüsselWert einen String beinhaltet und eine `InputStrategy`
Die Abstrakte Klasse `InputStrategy` braucht immer den Auslöser und besitzt eine Methode `handle` die je nach ankommenden Event gefeuert wird.
Von ihr erben dann alle unseren zentrierten Event's

# Umsetzung

<!--
Bitte hier die Umsetzung der Lösung kurz beschreiben:
-   Was haben Sie gemacht,
-   an welchem Datum haben sie es gemacht,
-   wie lange hat es gedauert,
-   was war das Ergebnis?
-->

-	Fernkampf System (5h) 21.05.
-   Erstellen des neuen InputSystem (6h) 25.05.
-   Logbuch schreiben (1h) 26.05
-   Hash Grid (5h) 28.05.
-   Refactor (5h) 29.05.

# Postmortem

<!--
Bitte blicken Sie auf die Aufgabe, Ihren Lösungsansatz und die Umsetzung
kritisch zurück:
-   Was hat funktioniert, was nicht? Würden Sie noch einmal so vorgehen?
-   Welche Probleme sind bei der Umsetzung Ihres Lösungsansatzes aufgetreten?
-   Wie haben Sie die Probleme letztlich gelöst?
-->

Die Aufgabe Refactorn Sie, ist natührlich anders als andere Aufgabe.
Am Ende ist man vlt nie 100 % zu frieden und will immer verbessern.
Dadurch ist es eine nahezu unlösbare Aufgabe, vor allem weil der eine einen anderen Stil hat als man selber, so muss es oft zu kompromissen kommen.

Aufgaben wie das Fernkampfsystem waren eher leichter umzusetzen da schon ein bestimmtes EntityCollision System existierte.
Wir eine Hiearachie für Items besaßen und durch die voher trockene Strukturierung eig gut auf dem bisherigen aufbauen können.
