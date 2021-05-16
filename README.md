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

Es soll ein Quest System auf Basis des Observer Pattern hinzugefügt werden.
Quest sind Aufgaben die erfüllt werden müssen um Belohnungen zu erhalten.
Quest sollen geeignet dargestellt, sowie annehmbar oder ablehnbar sein.
Angenommene Quest sind im HUD sichtbar.

Desweiteren sollen Quest sinnvoll mit JUnit getestet werden.


# Ansatz und Modellierung

<!--
Bitte hier den Lösungsansatz kurz beschreiben:
-   Wie sollte die Aufgabe gelöst werden?
-   Welche Techniken wollten Sie einsetzen?
-   Wie sah Ihre Modellierung aus (UML-Diagramm)?
-   Worauf müssen Sie konkret achten?
-->

Es sollen mehrere `Quest`implementiert werden die sich jedoch in der Aufgabe und Belohnung unterscheiden.
Die Abstrakte Klasse Quest, soll den GrundbauPlan der neuen `Quest` vorgeben.

Quest bestehen so aus : 
`String questname`: Der Name der jeweiligen Quest
`Player player`: Der jeweilige Player der auch die Belohnungen bekommen soll.
`Item itemOnReward`: Falls die Quest ein Item zur Belohnung bereithält.
`int expOnReward`: Für den Fall das es als Belohnung Erfahrungspunkte gibt.

Da jede Quest auch eine Belohnung bereithält, hat die abstrakte Klasse Quest auch die Mehthode `onReward(Player p)`.
Diese gibt den Spieler Exp, ein Item oder beides, sobald der `boolean isCompleted` gesetzt wurde.

Hinzukommen ein paar getter die dem HUD die Strings übergibt.
-   `String  getTask` : Für die Aufgabe an sich.
-   `String onWork` : Den Fortschritt der Quest.
-   `String onComplete` : Die Belohnung der Quest.

Ein wichtiger Boolean is der `isCompleted` boolean der ausgelöst wird sobald eine Quest erfüllt wurde.

Beschreibung der eingefügten Quest:

1. `RQuestDungeonStage`
    -   Quest besteht darin die Treppe zu erreichen und eine Stage weiter zu kommen.
2.  `RQuestKillMonster`
    -   Es gilt eine gewisse Anzahl von Monster `(Creature)` zu töten
3.  `RQuestLevelUp`
    -   In dieser Aufgabe muss der Held eine bestimmte Anzahl von Level aufsteigen.


Um die Quest jetzt auch geeignet in das Level zu malen, erstellen wir eine Klasse `QuestDummy`.
Der QuestDummy erbt von Entity und kann so von der Funktion
`spawnEntity` in die Stage gemalt werden.
Er ist die Klasse, mit dem der Spieler im Dungeon interagiert.
Für die Darstellung haben wir ein Enum namens `QuestTypes`,
welches den Namen und den Namen der Texture speichert.
Hier wird im Konstruktor mithilfe des QuestType dann die richtige Textur zur richtigen Quest gemalt.

Hier gibt es boolean die gesetzt werden sobald der Spieler mit der Falle interagiert.
`boolean isActive`: Wird gesetzt wenn der Spieler eine Quest mit einer Taste aktiviert. Der Spieler erhält so Informationen über die spezifizierte Quest und auch die Möglichkeit an oder abzulehnen.
`boolean decide`: Entfernt die Quest von der Stage, wenn angenommen oder abgelehnt.

Interagieren kann der Held, wenn er auf der Flagge steht.
Wir überschreiben hier die Methode `onEntityCollision(Entity otherEntity)`.
Wenn der Spieler mit Q den `boolean isActive` aktiviert erhält er die Möglichkeit die Quest anzunehmen (mit J) oder abzulehnen (mit N).

Je nachdem welchen Quest der Dummy darstellt, bekommt der Held so seine Aufgabe hinzugefügt.
Die Quest wird im Player in eine Liste gespeichert.
Ist der `boolean isCompleted` gesetzt wird die Quest aus der Liste gelöscht.
Jede Quest implementiert auch einen `EntityEventListener`.
Dies ist der Listener unseres Observer Pattern, welches wir schon benutzen und hier ebenfalls Anwendung finden kann.
Wird ein bestimmtes Event gefeuert, welches mit der passenden ID korreliert wird die Methode `handleEvent` ausgeführt.

Ein sehr einfaches Beipsiel unseres `RQuestDungeonStage`:
```
	@Override
	public void handleEvent(EntityEvent event) {
		if(event.getEventID() == Player.EVENT_ID_DUNGEON_LEVEL_CHANGED)
		{
			setCompleted(true);
		}
	}
```



# Umsetzung

<!--
Bitte hier die Umsetzung der Lösung kurz beschreiben:
-   Was haben Sie gemacht,
-   an welchem Datum haben sie es gemacht,
-   wie lange hat es gedauert,
-   was war das Ergebnis?
-->

-   Erstellen der ersten Quest (5h) 14.05
-   Korrektur und Feinarbeiten an den Quest(3h) 14.05
-   Implementierung und letzten Feinschliff der     Quest(3h)16.05
-   Merge und Einbau in das Hud (2h) 16.05
-   Erstellen der TestCases(2h) 16.05

# Postmortem

<!--
Bitte blicken Sie auf die Aufgabe, Ihren Lösungsansatz und die Umsetzung
kritisch zurück:
-   Was hat funktioniert, was nicht? Würden Sie noch einmal so vorgehen?
-   Welche Probleme sind bei der Umsetzung Ihres Lösungsansatzes aufgetreten?
-   Wie haben Sie die Probleme letztlich gelöst?
-->

Das  erstellen der Lösung war diesmal nicht ein alzugroßer Aufwand. Erst war die Versuchung da, ein eigenes Observer Pattern allein für die Quest zu erstellen, aber die Möglichkeit auf das vorhandene EntityEvent ObserverPattern hat die Sache übersichtlich und leicht umsetzbar gemacht und so flog das 3. Observer wieder raus.

Die Test fielen uns dagegen schwer von der Hand.
Es hat noch keiner von uns mit ihnen gearbeitet und nachdem wir herausgefunden wie sie funktionieren, wussten wir einfach nicht was wir testen sollten.


