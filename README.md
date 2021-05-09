---
title:  'Lerntagebuch zur Bearbeitung von Blatt 04
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

Das Inventar soll als HUD grafisch dargestellt werden. Ebenso soll auch die Anzahl der Lebenspunkte des Spielers angezeigt werden.
Der Spieler soll in der Lage sein, Erfahrungspunkte zu sammeln und Level aufzusteigen. Für das Aufsteigen von Level
soll es Belohnungen und zusätzliche Fähigkeiten geben, wie zum Beispiel mehr Schaden.
Es soll Fallen im Dungeon geben, die verschiedene Effekte auf den Spieler ausüben, wenn er drauftritt.
Manche Fallen sollen nur durch Wirkung eines Trankes sichbar sein.


# Ansatz und Modellierung

<!--
Bitte hier den Lösungsansatz kurz beschreiben:
-   Wie sollte die Aufgabe gelöst werden?
-   Welche Techniken wollten Sie einsetzen?
-   Wie sah Ihre Modellierung aus (UML-Diagramm)?
-   Worauf müssen Sie konkret achten?
-->

Um das Inventar anzuzeigen, soll das HUD verwendet werden. Dies besteht aus einer Reihe von Objekten, die jeweils
einen Gegenstand im Inventar anzeigen. Wenn sich das Inventar ändert, zum Beispiel durch herausnehmen eines Gegenstandes,
muss das HUD aktualisiert werden. Dazu nehmen wir ein EventListener-Modell, nach welchem das Inventar das HUD benachrichtigt,
wenn im Inventar eine änderung stattfindet. Das HUD passt sich dann entsprechend an.
Die Lebenspunkte und Erfahrung können wir auch mit HUD-Objekten anzeigen lassen.
In diesem Fall machen wir die HUD-Objekte aber aktiv, im vergleich zum Inventar-HUD.
Dafür greifen wir auf LibGDX zu und zeichnen die Herzen, die die Lebenspunkte darstellen, mit nur einem Objekt selbst.
So ist kein Observer-Pattern nötig und die Grafik aktualisiert sich von alleine.
Das selbe Vorgehen benutzen wir bei der Anzeige der Erfahrungspunkte.

Um ein Erfahrungspunktesystem zu implementieren, haben wir uns überlegt, sollte jede Kreatur
eine Variable bekommen, die die kompletten Erfahrungspunkte zählt. Es gibt keine Variable, welche das aktuelle Level speichert.
Das aktuelle Level wird mithilfe einer Funktion berechnet, die aus den kompletten Erfahrungspunkten das aktuelle Level berechnet.
Somit kann man theoretisch unendlich viele Level aufsteigen, ohne diese vorher zu konfigurieren.
Außerdem ist es möglich, die kompletten Erfahrungspunkte auf einen beliebigen Wert zu setzen,
ohne dass irgendwelche Zähler durcheinander kommen.
Die Statuswerte jeder Kreatur sind abhängig von dem Level der Kreatur. Somit lassen sich leicht
bestimmte Statuswerte bei einem Levelaufstieg erhöhen.

Fallen sind bei uns ein eigener Entity-Zweig. Sie nutzen die eingebaute Kollisionserkennung,
um zu erkennen, wenn ein Spieler auf sie tritt. Die Effekte einer Falle werden nach dem OOP-Prinzip,
in den Unterklassen der Fallen festgehalten.


# Umsetzung

<!--
Bitte hier die Umsetzung der Lösung kurz beschreiben:
-   Was haben Sie gemacht,
-   an welchem Datum haben sie es gemacht,
-   wie lange hat es gedauert,
-   was war das Ergebnis?
-->

Wir haben wieder in etwa 20 Stunden insgesamt gebracht, was aber nur eine Schätzung
ist, da wir die Zeit nicht zählen.
Auch haben wir wieder vereinzelt im Laufe der Woche gearbeitet, anstatt uns auf besonderen
Tagen zu verabreden.
Die Aufgaben aus diesem Blatt konnte man ziemlich unabhängig voneinander machen,
weshalb wir die Aufgaben gut aufteilen konnten, so dass wir alles gleichzeitig machen konnten
und am Ende zusammentragen konnten.

# Postmortem

<!--
Bitte blicken Sie auf die Aufgabe, Ihren Lösungsansatz und die Umsetzung
kritisch zurück:
-   Was hat funktioniert, was nicht? Würden Sie noch einmal so vorgehen?
-   Welche Probleme sind bei der Umsetzung Ihres Lösungsansatzes aufgetreten?
-   Wie haben Sie die Probleme letztlich gelöst?
-->

Alles hat ganz gut funktioniert und mache der Grafiken sehen überraschend gut aus.
Wir mussten zum ersten mal mit der Text-API arbeiten, über welche wir immer noch
etwas lernen müssen, wie sie funktioniert.
Auch durch das arbeiten mit Git entstehen manchmal Schwierigkeiten, da
wir alle Git-Neulinge sind. Aber durch etwas Recherche konnten viele Probleme
geöst werden. 
