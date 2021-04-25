---
title:  'Lerntagebuch zur Bearbeitung von Blatt 02
author:
- Andreas Wegner (andreas.wegner@fh-bielefeld.de)
- Malte Kanders (malte_theodor.kanders@fh-bielefeld.de)
- Mathis Grabert (mathis.grabert@fh-bielefeld.de)
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

Für die Aufgabe von Blatt 2 soll zunächst ein Logger erstellt werden, der uns bei der Ausgabe von Konsolenmeldungen unterstützt.
Es soll immer bei bestimmten Ereignissen eine Logmeldung ausgegeben werden, zum Beispiel beim betreten eines neuen Levels.
Aber auch wenn neue Sachen hinzukommen, soll das Logging nachgezogen werden. Natürlich muss eine Logmeldung die wesentlichen Eigenschaften
beinhalten, wie das Logging-Level und der Zeitstempel.

In unserem Dungeon sollen mindestens zwei neue Monsterarten vorkommen, die verschiedene Attribute besitzen. Sie sollen nicht nur herumstehen,
sondern sich auch selbstständig bewegen.

Zu guter letzt soll ein Kampfsystem eingebaut werden, damit man mit den Monstern auch kämpfen kann. Dieses Kampfsystem sollte so funktionieren,
wie man es von einem Spiel erwarten würde. Jede Einheit hat Lebenspunkte, welche durch Schaden reduziert werden können. Der Spieler kann den Monstern
schaden hinzufügen, in dem er sie angreift oder Schaden nehmen, in dem er sie berührt. Es gibt Statuswerte, die bestimmen, wie erfolgreich ein Angriff ist, oder wie sehr man zurückgestoßen wird. Außerdem sollen sich die Leben auch regenerieren können.


# Ansatz und Modellierung

<!--
Bitte hier den Lösungsansatz kurz beschreiben:
-   Wie sollte die Aufgabe gelöst werden?
-   Welche Techniken wollten Sie einsetzen?
-   Wie sah Ihre Modellierung aus (UML-Diagramm)?
-   Worauf müssen Sie konkret achten?
-->

Zu erst haben wir uns um den Logger gekümmert. Diesen kann man üblicherweise nach dem Singleton-Pattern umsetzen.

Wir brauchten auf jeden Fall zu erst eine Möglichkeit der Kollisionserkennung zwischen Entities. Praktisch wäre eine Klasse, die
Werkzeuge beinhalten, um Kollisionserkennung zu vereinfachen,

Für das Kampfsystem haben wir uns überlegt, nicht nur eine Variable für Lebenspunkte in unsere Creature
Klasse zu schreiben, sondern gleich ein Array für mehrere Statuswerte, die wir im Laufe der Entwicklung erweitern können.

Am schlausten wäre es, wenn wir in unserer Creature Klasse eine Methode erstellen, die allein für Angriffe verantwortlich ist,
welche man dann überall sehr einfach aufrufen kann, um einen Angriff zu starten.
Wir müssen auch beachten, dass Monster und Spieler nach einem Treffer kurz unverwundbar sein sollten, damit das Spiel nicht frustrierend wird.
Dazu sind mehrere Zählervariablen in den Creatures nötig. Auch ein Zufallsgenerator muss eingebaut werden, um zufällig verfehlen zu können.

Wir fanden dass Partikel, die z.B. den Schaden anzeigen hilfreich wären, da es sie auch in anderen Spielen gibt.
Leider bietet die PM-Dungeon-API keine bekannt Möglichkeit dafür, die Vernünftig aussehen würde,
also müssen wir selbst auf LibGDX zugreifen, um unsere Partikel zu zeichnen.

Damit man jetzt noch Logmeldungen ausgebeben kann, wenn der Spieler oder ein Monster stirbt, wäre ein Eventsystem für Entities ganz gut,
welches man nach dem Observer-Pattern gut umsetzen könnte. Damit wären wir auch für ein zukünftiges Questsystem gerüstet.


# Umsetzung

<!--
Bitte hier die Umsetzung der Lösung kurz beschreiben:
-   Was haben Sie gemacht,
-   an welchem Datum haben sie es gemacht,
-   wie lange hat es gedauert,
-   was war das Ergebnis?
-->

Der java.util.logging.Logger hat glücklicherweise alle grundgelegenen Dinge schon implementiert. Wir mussten nur noch die Formatierung der
Logmeldungen selbst übernehmen. Für das schreiben der Logmeldungen in eine Datei, gibt es auch schon eine vorgefertigte Klasse namens FileHandler.

Als nächsten wollten wir das Kampfsystem umsetzen. Die Kollision zwischen Entites konnten wir einfach in die IEntity.update() Methode einbauen.
Für fortgeschrittenere Kollisionserkennung haben wir auch eine BoundingBox Klasse erstellt. Dadurch erhalten die Entities auch Hitboxen.

Um nun tatsächlich Angriffe der Monster uns des Spielers auszuführen, haben wir Methoden erstellt, die das übernehmen.
Diese beinhalten gleich die Schadensberechnung und die Animationen dafür. Um das Kampfverhalten etwas natürlich zu gestalten, gibt es mehrere Zähler
in Monstern die für Unverwundbarkeit nach dem Treffer und Abklingzeit nach dem Treffer verantwortlich sind.
Da viele Zähler für Verwirrung sorgen können, wurden sie auch von entsprechenden Methoden berechnet.

Um das Partikelproblem anzugehen, haben wir uns LibGDX angeschaut und waren dann in der Lage selbst zu zeichnen.
Dadurch konnten wir tolle Partikeleffekte visualisieren. Das Partikelsystem ist so aufgebaut, wie das Entitysystem. Jeder Partikel
ist in einer Liste und es werden jedes Frame deren update() und draw() Methoden aufgerufen.

Für die Arbeit haben wir kein Datum vereinbart, sondern haben uns im Laufe der Woche öfters im Discord getroffen und zusammen daran gearbeitet.
Wir können die Zeit im Nachhinein schlecht einschätzen, aber für uns alle zusammengerechnet sind es sicher über 40 Stunden gewesen.

Als Ergebnis haben wir nun ein Spiel, in dem es sich relativ angenehm kämpfen lässt. Das Spiel ist nun viel lebhafter als vorher.

# Postmortem

<!--
Bitte blicken Sie auf die Aufgabe, Ihren Lösungsansatz und die Umsetzung
kritisch zurück:
-   Was hat funktioniert, was nicht? Würden Sie noch einmal so vorgehen?
-   Welche Probleme sind bei der Umsetzung Ihres Lösungsansatzes aufgetreten?
-   Wie haben Sie die Probleme letztlich gelöst?
-->

In Hinblick auf die Codequalität können wir definitiv zufrieden sein, finden wir.
Wir haben für die meisten Sachen versucht, abgekapselte Systeme zu programmieren, was auf jeden Fall bei
der Fehleranfälligkeit hilft, und vor allem dabei hilft, Struktur in den Code zu bringen.
Auch mussten wir lernen, dass es gerade bei der Arbeit mit Git hilfreich ist, abgekapselte Systeme zu haben,
da dadurch nicht so viele bereits vorhandene Dateien bearbeitet werden müssen, was sehr bei Merge Requests hilft.
Wir haben allerdings auch vieles gemacht, was gar nicht gefordert war.
Es hat zwar Spaß gemacht, diese nicht geforderten Elemente einzubauen, aber es hat wirklich viel Zeit geraubt.
Vor daher würden wir wohl versuchen, es wieder herunter zu schrauben. Aber wir haben dadurch sicher viel gelernt.

