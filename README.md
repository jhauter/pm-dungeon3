---
title:  'Lerntagebuch zur Bearbeitung von Blatt 01
author:
- VORNAME NAME (EMAIL)
Malte Kanders (malte_theodor.kanders@fh-bielefeld.de)
- VORNAME NAME (EMAIL)
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

Die gegebenen Ressourcen in die eigene IDE einbetten.
Danach die Dokumentation durchlesen und sich in die API einfinden.
Gegenfalls die JavaDoc als Hilfe nehmen.
Einen eigenen ersten Helden erstellen und diesen zum Laufen bringen.
Dem Helden werden entsprechende Textures und Animationen hinzufügen.
Gegebenenfalls eigene Textures suchen.

In das Git System einfinden und damit vertraut machen.


# Ansatz und Modellierung

<!--
Bitte hier den Lösungsansatz kurz beschreiben:
-   Wie sollte die Aufgabe gelöst werden?
-   Welche Techniken wollten Sie einsetzen?
-   Wie sah Ihre Modellierung aus (UML-Diagramm)?
-   Worauf müssen Sie konkret achten?
-->

Die bereitgestellten Dateien herunterladen und diese extrahieren.
Diese sollen als Dateien in die IDE eingepflegt werden und gegebenenfalls
Einstellungen konfiguriert.
Die passenden Texturen hinzugefügt, um sich mit der Dokumentation in die API
einzufinden
Mithilfe der Dokumentation dann die ersten Schritte des Helden unternehmen.
Der erste eigene Held wird in den Dungeon implementiert, „Hello Dungeon“.
Als Letztes sollen die Animationen des Helden eingefügt und erweitert werden.
Der Held soll zwischen Lauf- und Idle- Animationen unterscheiden und auch nach links
laufen können.

Ein Grundgerüst mithilfe von einem Klassendiagramm aufbauen.
Dafür an spätere Implementationen denken.
Es soll damit verhindert, das kein replizierter Code geschrieben wird oder es zu Fehlern
kommt, die nur mit aufwendigem Refactorn zu beheben sind.
Gegebenenfalls Design Pattern für bestimmte Methoden um „Clean Code“ zu erstellen.
Dies verhindert ebenfalls nötiges Refactorn.


# Umsetzung

<!--
Bitte hier die Umsetzung der Lösung kurz beschreiben:
-   Was haben Sie gemacht,
-   an welchem Datum haben sie es gemacht,
-   wie lange hat es gedauert,
-   was war das Ergebnis?
-->

Das Dokument und die Vorkehrungen wurden nach Beschreibung und Tutorials abgearbeitet.
In GitLab wurden die Mitglieder hinzugefügt und die Java Projekte vereinheitlicht.
Erste Tests mit GitLab wurden umgesetzt und so erprobt.
Beispielsweise wurde ein Ordner von einem nicht Admin als Pull Request hinzugefügt.
Die Readme verändert und Commits geschrieben.

Aus der UML kristallisierte sich eine
Grund Entity Klasse, ein InputListener und ein Animationsloader.
Diese Stellen nachher das Gerüst für Bewegung, das Laden der Animationen, sowie
für bewegte, sowie unbewegte Objekte, dar.
Des Weiteren werden schon Aufgabenfelder verteilt, die mithilfe der UML sichtbar werden.
Jeder Entwickler wird so bereits ein Spezialist für ein Feld, dieser muss sicherstellen das seine Methoden von den Teammitgliedern verstanden und benutzt werden können!
Die ersten Implementationen werden umgesetzt.

Tutorials, Selbstlernen = 4 Stunden.
Meeting am 13.04.21 von 13 Uhr - 22 Uhr.
Feinarbeiten und Eigenarbeiten ~

# Postmortem

<!--
Bitte blicken Sie auf die Aufgabe, Ihren Lösungsansatz und die Umsetzung
kritisch zurück:
-   Was hat funktioniert, was nicht? Würden Sie noch einmal so vorgehen?
-   Welche Probleme sind bei der Umsetzung Ihres Lösungsansatzes aufgetreten?
-   Wie haben Sie die Probleme letztlich gelöst?
-->

Umsetzung der zusammenarbeit mit Git funktioniert unglaublich gut und vereinfacht die Zusammenarbeit.
Es hakt an manchen Stellen, da Git noch neu ist, aber ist es erstmal konfiguriert klappt das
Arbeiten damit wunderbar.
Gemerged wurden größere Projektabschnitte über eine Online Sitzung, um etwaige Probleme und Verbesserungen anzusprechen.
Große relevante Probleme bei der Umsetzung der Aufgabe traten nicht auf.
Vorgehensweise aus meiner Sicht: Sehr gut, durch die Vorbereitung und dem aufteilen der Aufgaben kam sich niemand in die 
Quere. Während dem Verteilen der Aufgaben wurde bereits Funktionalität bestimmter Klassen/Packages skizziert.
Bei einer späteren Sitzung wurden diese dann dem jeweiligen anderem auch präsentiert, damit jeder vom gesamten ein 
Eindruck hat.

