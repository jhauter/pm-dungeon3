---
title:  'Lerntagebuch zur Bearbeitung von Blatt 03
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

Wir sollten Heiltränke implementieren, sowie Waffen und Zaubersprüche, die ins Inventar gelegt werden können und auch wieder fallen gelassen werden können. Das Invertar soll per
Tastendruck geloggt werden. Unser Held kann aktiv Monster angreifen und besiegen. Verschiedene Items sollen unterschiedliche Wirkungen haben.
Im Dungeon sollen Schatzkisten verteilt sein, die zufällige Items beinhalten, welche man herausnehmen kann. Außerdem soll es Taschen geben, welche man ins Inventar legen kann, 
die wiederum Items eines bestimmten Typen beinhalten können.


# Ansatz und Modellierung

<!--
Bitte hier den Lösungsansatz kurz beschreiben:
-   Wie sollte die Aufgabe gelöst werden?
-   Welche Techniken wollten Sie einsetzen?
-   Wie sah Ihre Modellierung aus (UML-Diagramm)?
-   Worauf müssen Sie konkret achten?
-->

Bei der Umsetzung der Items sollte besonders auf den OOP-Gedanken Wert gelegt werden. Außerdem sollte beim Loggen des Inventares das Visitor-Pattern genutzt werden.
Zu erst haben wir erkannt, dass es 3 verschiedene Arten gibt, in denen Items im Spiel auftreten können:

* als Drop im Dungeon
* als Item-Instanz im Inventar
* als einzigartiger Datensatz pro Item im Spiel

Unser Ansatz ist es also eine `Item`-Klasse zu erstellen, die statische Referenzen zu im Spiel registrierten Items enthält.
Diese statischen Objekte haben Callback-Methoden, die zum Beispiel immer ausgeführt werden, wenn ein Wesen ein Item benutzt.

Da wir bei Taschen Generics benutzen sollen, hilft dies auch dabei, die entsprechenden Item-Typen für die Generics zu definieren.
Die Item-Instanzen im inventar stellen wir mit dem Interface `InventoryItem` dar, welche zu Inventaren hinzugefügt werden können,
die wir mit dem Interface `Inventory` dar stellen. Diese beiden Interfaces sind generische Typen, da sie auch als Inventare für Truhen und Taschen genutzt werden sollen,
welche ebenfalls generisch sind. Dadurch, dass Items im Inventar eigene Instanzen sind, ist es möglich, für die Taschen eine eigene Unterklasse zu erstellen,
welche ein eigenes Inventar besitzt. So hat man praktisch Inventare in Inventaren.

Nützlich ist dieser Ansatz auch bei der Erstellung von den Drop-Entities, da diese bloß eine einzige Klasse benötigen, welche nur eine `InventoryItem`-Instanz
besitzen. Diese `InventoryItem`-Instanz ist dann sozusagen das Item, das auf dem Boden liegt. Das Entity ist dabei sozusagen ein Wrapper. Und das funktioniert
dann sogar für Taschen, in denen Items drin sind.

Als letztes bleiben noch die Truhen, welche wir als Entity mit einem Inventar erstellen können. Unser am Anfang ersteller `AnimationHandler` hilft
uns hierbei, eine schöne "öffnen"-Animation anzuzeigen.

Die Steuerung, mit welcher der Spieler mit dem Inventar und Truhen interagieren kann, bauen wir in die `Player`-Klasse ein, die eine Oberklasse vom Helden ist.


# Umsetzung

<!--
Bitte hier die Umsetzung der Lösung kurz beschreiben:
-   Was haben Sie gemacht,
-   an welchem Datum haben sie es gemacht,
-   wie lange hat es gedauert,
-   was war das Ergebnis?
-->

Die Umsetzung hat insgesamt schätzungsweise 20 Stunden gedauert.
Wir haben an vereinzelten Tagen der ganzen Woche gearbeitet und nicht nur in einem bestimmten Zeitraum.
Zu erst haben wir die statischen Items und deren Verhalten eingebaut, da diese die Grundlage sind.
Danach haben wir das Inventar erstellt und zum Schluss die Item-Drops und Truhen.

# Postmortem

<!--
Bitte blicken Sie auf die Aufgabe, Ihren Lösungsansatz und die Umsetzung
kritisch zurück:
-   Was hat funktioniert, was nicht? Würden Sie noch einmal so vorgehen?
-   Welche Probleme sind bei der Umsetzung Ihres Lösungsansatzes aufgetreten?
-   Wie haben Sie die Probleme letztlich gelöst?
-->

Es hat im Großen und Ganzen gut funktioniert. Nur die Generics haben an manchen Stellen Schwierigkeiten bereiten,
da es ungewohnt war, in so einem Umfang mit ihnen zu arbeiten.
