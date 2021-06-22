---
title:  'Lerntagebuch zum Projekt'
author:
- Paul Nagel (paul_merlin.nagel@fh-bielefeld.de)
- Adrian Koß (adrian.koss@fh-bielefeld.de)
- Michael Herber (michael.herber@fh-bielefeld.de)
...


# Aufgabe

Wir haben uns vorgenommen drei Features zu implementieren. Das erste Feature ist, dass Gegner mit jeder Ebene stärker werden, und dass 
im Verlauf des Dungeons neue Gegnertypen erscheinen. D.h. dass nicht alle Gegnertypen von Anfang an erscheinen, sondern erst später in
stärkerer Form.

Das zweite Feature ist, dass der Spieler den Namen des Helden am Anfang des Spieles angeben soll. Hierbei gibt es gültige und ungültige
Spielernamen. So darf der Name, z.B. nur aus Buchstaben und wenigen Leerzeichen bestehen.

Das dritte Feature sorgt dafür, dass Items zufällig generiert werden können. Dabei sind der Name und die Werte des Items variabel.


# Ansatz und Modellierung

Das erste Feature wird mithilfe der Level der Gegner umgesetzt. So wird bei jedem Ebenenübergang für jede Ebene die Methode `void spawnMonsters()`
aufgerufen, die alle Monster der vorherigen Ebene löscht und neue Monster erstellt, die ein höheres Level haben und damit auch stärker sind. 
So wird also für jede Ebene vorgegebenen welche, wie viele und wie starke Monster gespawnt werden.

Das zweite Feature funktioniert so, dass eine Klasse `playernameWindow`, in der main-Methode, aufgerufen wird. Diese Klasse erbt von JFrame und 
definiert somit das Fenster, in welchem der Name des Helden eingegeben werden soll. Dieses Fenster besteht aus einem Label, einem Textfeld und 
einem Button. Nachdem der Name in das Textfeld eingegeben wurde, bestätigt man die Eingabe mit dem Button. Daraufhin wird die `checkName()` Methode 
aufgerufen, welche prüft, ob der Name akzeptiert wird. Dies wird mithilfe eines RegEx gemacht. Ist der Name akzeptiert worden, so wird der Name des 
Helden darauf gesetzt und das Fenster geschlossen.

Das dritte Feature wird mit einer Klasse `RandomItemGenerator` implementiert. Hierin gibt es vier Methoden:

-	`Item generateRandomItem(int levelCount)`
-	`Item generateMeleeItem(int levelCount)`
-	`Item generateRangedItem(int levelCount)`
-	`Item generatePotion(int levelCount)`

LevelCount(die Ebenenzahl) wird dazu benötigt die Stärke des Items zu berechnen.

Für den Namen des Items gibt es drei Stringlisten mit den verschiedenen Adjektiven in drei verschiedenen Ausprägungen: schwach, normal und stark.
Der Name des Items ergibt sich aus den Klassennamen. Diese werden dann zusammengefügt zu einem gesamten Itemnamen bestehend aus Adjektiv und Itemname.
Für die Stats sind in jedem Item Standardwerte vorgesehen, die je nach Level mit einem Faktor multipliziert werden, um dann die Werte des generierten
Items zu bestimmen. 

Für die Auswahl, welche Items generiert werden können, werden, mithilfe von Reflection, die Unterklassen der Itemkategorien erfasst und zu einer Liste
gepackt. Welche Klasse dann genau instanziiert wird, wird dann zufällig ausgewählt. 

# Umsetzung

Am Dienstag, dem 08.06.2021, wurde mit dem zweiten Feature angefangen. Dies hat ca. 5 Stunden gedauert.
Am Mittwoch, dem 16.06.2021, wurde das zweite Feature weitergeführt und das dritte angefangen. Dies hat ca. 5 Stunden gedauert.

# Postmortem

Aktuell ist rückblickend noch nichts zu sagen, da noch kein Feature vollständig fertiggestellt wurde und somit noch aussteht, ob die Lösungsansätze gut
oder schlecht waren.
