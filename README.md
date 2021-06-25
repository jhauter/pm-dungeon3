---
title: "Projekt Pitch"
author: 

- Andreas Wegner (andreas.wegner@fh-bielefeld.de)
- Malte Kanders (malte_theodor.kanders@fh-bielefeld.de)
- Mathis Grabert (mathis.grabert@fh-bielefeld.de)

- Paul Nagel (paul_merlin.nagel@fh-bielefeld.de)
- Adrian Koß (adrian.koss@fh-bielefeld.de)
- Michael Herber (michael.herber@fh-bielefeld.de)

- Fabius Dölling (fabius.doelling@fh-bielefeld.de)
- Jonathan Hauter (jonathan.hauter@fh-bielefeld.de)
- Vivien Traue (vivien.traue@fh-bielefeld.de)
...

# Gruppe Andreas, Malte, Mathis

## Fog of War
### Feature

-   Es soll ein sogenanntes "Fog of War" System eingebaut werden.
-   Fog of War bedeutet :
    -   Die Karte ist nicht komplett einsehbar.
    -   Weder für den Spieler noch für Monster.
    -   Umrisse sind weiterhin sichtbar, aber Entitys verschwinden im Nebel.
    -   Es soll nach und "nebeliger" werden, dies könnte sich etwas ausgleichen durch:
        -   Der Spieler hat am Anfang eine bestimmte Sicht, welche aber verbesserbar ist:
            -   Tränke die die Sicht aufbessern.
            -   Durch höheres Level steigt die Sicht.
        -   Schlechtere Sicht durch:
            -   "Rushen" da das Level nicht steigt.
            -   Monster die einen Statuseffekt hinzufügen.
            -   Quest die es erschweren damit die Quest interessanter wird. (Next Dungeon Level Quest.)
-   Gimmicks :
    -   Glühwürmchen die trotzdem leuchten.
    -   Aufblitzen von Truhen.
    -   Aufblitzen von roten Augen (Monster)
    -   Feuerwaffen erhellen ihren Weg durch den Dungeon

### Technik

Das Fog of War-System (FoW) könnte man umsetzen, in dem man ein zweidimensionales float-Array über das Level spannt. Die Größe in X- und Y-Richtung müsste ein bestimmtes Vielfaches der Levelgröße sein, da die Auflösung des FoW-Arrays höher sein sollte, als die der Bodenfliesen. So würde zum Beispiel der Faktor 4 dafür sorgen, dass die Seitenlänge eines "FoW-Feldes" $\frac14$ einer Bodenfliese ist. Jeder Wert des Arrays soll von 0 bis 1 gehen, wobei 0 komplett klar ist und 1 komplett neblig ist. Für jedes FoW-Feld wird ein schwarzes oder graues Rechteck an die entsprechende Stelle gezeichnet. Die Rechtecke sind bei FoW-Werten von 0 komplett durchsichtig und bei FoW-Werten von 1 nur noch halb durchsichtig, so dass man zwar noch Räume erkennen kann, aber nicht gut. Entities, die sich im Fog of War befinden sollen nicht mehr angezeigt werden. So viel zum Visuellen. Der FoW soll aber auch das Spielgeschehen beeinflussen. So sollen Gegner nicht in der Lage sein, den Spieler zu sehen, wenn diese sich im FoW befinden. Somit gibt es auch einen Überraschungseffekt, wenn auf dem FoW plötzlich Gegner erscheinen.
Der Fog of War soll in einer bestimmten Umgebung des Spielers aufgedeckt werden. Sobald der Spieler weitergeht, kommt auch der FoW an die ursprüngliche Stelle zurück. Der FoW wird also nicht permanent aufgedeckt. Um die Aufdeckung des FoW durch den Spielers zu realisieren, müssen die Werte des FoW-Arrays in der Umgebung reduziert Werden. Da der Beginn des FoW nicht Abgehackt erscheinen soll, müssen die Array-Werte in Abhängigkeit von der Entfernung des Spielers zum Feld berechnet werden.

### Probleme

Die Aufdeckung des FoW soll nicht durch Wände hindurch passieren. Somit muss ein Algorithmus geschrieben werden, der zurückgibt, welche Felder um den Spieler herum um welchen Wert reduziert werden sollen, ohne dass bloß die Distanz in Betracht gezogen werden soll. Der Algorithmus soll eine Verbreitung des Lichts simulieren, welches an Wänden stoppt.

## Credits am Ende des Spiels

### Feature

-   Am Ende des Spiels werden die Namen der Schöpfer eingeblendet.
-   Ende des Spiels ist eine kleine Animation wie der Spieler aus dem Dungeon "crawlt".
-   Um es interessanter zu machen könnte der Abspann :
    -   Star Wars ähnlich sein der den Text über den Bildschirm trägt.
    -   Ein paar Easter Eggs in den Namen/Text/Abspann
        oder ähnliches. (Minigames)
    -   (Eventuell anzeigen wie viele Kills gemacht wurden)
    -   (Wie viele Tränke benutzt wurden)
    -   (Genauigkeit beim Fernkampf)
    -   (Verlorene Leben)
    -   (Eingesammelte Gegenstände / gefundene Easter Eggs)

### Technik

Sobald das Spiel zu Ende ist, wird der Bildschirm ausgeschwärzt, z.B. indem man ein Schwarzes Bild mit abnehmender Transparenz über das Spiel rendert. Sobald das Bild komplett schwarz ist, werden die Textzeilen unserer Namen untereinander gerendert. Der Y-Wert des Gerenderten ist zu Beginn so klein, dass der Text unter dem Bildschirm gerendert wird (außerhalb nicht sichtbar). Dann wird der Y-Wert so lange erhöht bis die letzte Zeile des Textes ganz oben angekommen ist.

### Probleme

Wir haben überlegt, ob der Text im "Star Wars"-Style gerendert werden soll. Dazu müsste man aber eventuell in 3D rendern und das würde den Rahmen sprengen. Somit lassen wir das erstmal offen. Sonst sollte es hierbei keine Probleme geben.

## Dynamisches Speichern

### Feature

-   Der Held/Spieler speichert nicht von sich aus. "Dieses Spiel speichert an bestimmten Stellen selber".
-   Eine kleine Animation am rechten Bildschirm Rand das signalisiert das gespeichert wurde.
-   Die Speicher Datei in ein passendes Dateiformat, mit Zeit Stempel sichern.
-   Die Möglichkeit gespeicherte Spielstände zu laden.
-   Möglichkeit Spielstände bei Bedarf zu löschen oder zu überschreiben ?

### Technik

Zum speichern würden wir eine binäre Datei benutzen. Damit das Speichern objektorientiert ist, würde jedes Entity `readData(ByteBuffer)`und `writeData(OutputStream)` o.ä. Methoden bekommen. Dann wird eine Datei zum schreiben geöffnet und es wird über jedes Entity iteriert, wessen Speichermethode dann aufgerufen wird. Der Parameter ist dann der Datei-Stream. Beim Laden funktioniert das genau so, nur umgekehrt. Damit beim Laden der Datei wieder erkannt wird, um welches Entity es sich handelt, muss vor den jeweiligen Entitydaten eine Art von ID gespeichert werden. Nach dem Einfügen der Entitydaten könnte eine Stoppsequenz kommen, um vor Fehlern abzusichern. Entweder das oder jedes Entity weiß, wie viele Bytes es gespeichert hat und darf dann auch nur so viele Bytes lesen.
Das Format könnte dann evtl. so aussehen:

```
level|entityID|entityBytes|entityData...|entityID|entityBytes|<entityData...
```

Beim laden würde dann von jedem Entity die `readData(ByteBuffer)` Methode nur mit den zugewiesenen Bytes ausgeführt werden.

### Probleme

Entities brauchen IDs, um das oben beschriebene umzusetzen.
Dazu könnte man eine zentrale Entity-Registrierungs-Klasse erstellen, welche eine Map
mit allen Entityklassen und den jeweiligen IDs beinhaltet.
Ein weiteres Problem ist, dann man beim Laden der Entities eine Entity-Instanz braucht, um die `readData()` Methode aufzurufen. Dies könnte man mithilfe von Reflection lösen, indem man auf die in der Registrierungsklasse vorhandenen Einträge zugreift, welche die entsprechenden Entity-Klassen beinhalten.
Ein Entity-Eintrag könnte zum Beispiel so aussehen:

```
registerEntity(5, Demon.class, "Demon");
```

So kann man aus der gespeicherten `5` eine Instanz von `Demon.class` erzeugen und die `readData()` Methode aufrufen.

## Game Over-Bildschirm

### Feature

-   Wenn ein Spieler vor dem erreichen des Ausgangs stirbt soll ein Game Over Screen angezeigt werden.
-   Neustart des Spiels sollte hier implementiert werden.
-   (Vlt mehrere verschiedene Game Over Screens)

### Technik

Sobald der Spieler besiegt wird, wird ein Bild mit abnehmender Transparenz auf der Spiel gerendert. Dieses Bild beinhaltet die Game-Over-Nachricht. Das Bild kann je nach Belieben animiert werden. Zum Beispiel könnte man ein Schwarzes Bild mit transparentem Text benutzen und sobald das Bild vollständig angezeigt wird, ein einfarbiges Bild mit abnehmender Transparenz dahinter rendern.

# Gruppe Jonathan, Vivien, Fabius

## Zu implementierende Features:
    - Bosssystem
    - Zufällige Items
    - Währung und Händler


## Überlegungen Bossystem
- Bosse müssen deutlich anspruchsvoller sein als normale Gegner
    - Kämpfe müssen abseits von Gear und Level gewisse Anforderungen an den Spieler stellen
        - Verschiedene Phasen mit unterschiedlichen, anspruchsvolleren Patterns
        - Bullet-Hell änhliches Projektilsystem
        - Spawnen von anderen, kleineren Entities (Schleim der sich aufteilt? Viecher das Eier spawnt die nach einer Weile "aufgehen")
        - Beinflussung von Boden (Lava? Gift)
        - Statuseffekte (Verwirrung -> Beinflussung der Lauf/Angriffsrichtung?)
    - Zusätzliche UI-Elemente:
        - Lebensanzeige des Gegners mit Namen
    - Bosse sollten besonderen Loot droppen
        - Einzigartige Waffen (Siehe zufällige Items)
        - Waffen mit besonders hohen Stats
- Bosse müssen ab einen gewissen Punkt erscheinen, aka nach X abgeschlossenen Ebenen
- Es sollze spezielle Bossarenen geben, es kann sein dass wir den "DungeonGenerator" etwas hijacken müssen (?) (OPTIONAL)
- Phasen von Bossen könnten zufällig generiert werden aus einem Pool von Fähigkeiten    

### Überlegungen Bullethell
- Unsichtbare BulletSpawner können sich bewegen, drehen nach bestimmten Pattern, schießen Bullets in "Blickrichtung", Initialposition abhängig vom Boss
    - Spawnerpattern können durch einfache "Phasen" in einer Queue realisiert werden, wechseln nach X ticks oder bei Event
    - So lassen sich einfache Muster relativ einfach realisieren
- Projektile selbst könnten auch einfaches Eventsystem besitzen falls sie auf bestimmte Umstände reagieren sollen oder spontan die Richtung wechseln
- Möglicherweise verschiedene Projekttiltypen die zerschossen oder speziell gedodged werden können:
- Bossarenen (OPTIONAL) entweder unter Verwendung des DungeonLoader oder eigenes Überschreiben von DungeonWorld mit Rendern von Tiled2D Map
    - Falls nicht umsetzbar: Tutor stellt eine Art "Bossraum" zur Verfügung, die kann man auch nutzen
- Probleme:
  - Noch nicht sehr vertraut mit Projektilsystem des neuen Oberprojekts
  - Fine-Tuning von Beschleunigung, Richtung von Projektilen könnte sehr langwierig und unübersichtlich werden
### Überlegungen Bossphasen:
- Aufteilende Gegner: Einfaches Spawnen von Gegnern 
- Bossphasen umsetzbar mit Strategy-Pattern ähnlichem System
- Implementieren spezieller Debuffs die bspw Laufrichtung oder Sicht (Siehe Pitch: Fog-Of-War) beinflussen möglicherweise auch über Strategy-Pattern

### Zufällige Items:
- Stats von Items in Chests/Händlern sollen - abhängig von Fortschritt in Dugenon - zufällig generiert werden:
    - Rohe Stats wie "Angriffskraft"
    - Vielleicht Partikeleffekte, bei Projektilwaffen Range, Sprite etc.
    - Zufällige Namen abhängig von Stats: Zusammengesetzt aus vordefinierten Namenschnippseln: Beispiel: "Verzauberter Saphir Stab" oder "Schneller mörderischer Dolch"
    - Liste aus Namensprädikaten - Vielleicht speichern und auslesen in einer Textdatei/JSON zur einfacherere Bearbeitung
    
### Währung und Händler:
- Währung (Gold): Seperate Resource die nicht im Inventar gesammelt wird
- Droppt bei Quests, Monstern. -> Semi-Zufällig oder fixe Mengen
- Währung kann bei speziellen Händernpcs die auf einigen Ebenen spawnen ausgegeben werden
    - Händler haben zufällige Items, Wert abhängig von Stärke.
    - Funktionieren eigentlich ähnlich wie Chests
    - HUD-Code muss leicht umgeschrieben werden damit Geldwerte angezeigt werden 

### Grundsätzlich
- Brauchen viele Animationen/Sprites für Bosse (Von itch.io, Opengameart?)
- Räume könnten schwierig weredn

# Gruppe Adrian, Paul, Michael

## Aufgabe

Wir haben uns vorgenommen zwei Features zu implementieren. Das erste Feature ist, dass Gegner mit jeder Ebene stärker werden, und dass 
im Verlauf des Dungeons neue Gegnertypen erscheinen. D.h. dass nicht alle Gegnertypen von Anfang an erscheinen, sondern erst später in
stärkerer Form.

Das zweite Feature ist, dass der Spieler den Namen des Heldens am Anfang des Spieles angeben soll. Hierbei gibt es gültige und ungültige
Spielernamen. So darf der Name, z.B. nur aus Buchstaben und wenigen Leerzeichen bestehen. 


## Ansatz und Modellierung

Das erste Feature wird mit Hilfe der Level der Gegner umgesetzt. So wird bei jedem Ebenenübergang für jede Ebene eine vorgegebene 
Methode aufgerufen, die alle Monster der vorherigen Ebene löscht und neue Monster erstellt, die ein höheres Level haben und damit
auch stärker sind. So wird also für jede Ebene vorgegebenen welche, wie viele und wie starke Monster gespawnt werden. Hierdurch
entsteht eine einfache Möglichkeit die Monster und jede Ebene für sich zu balancen und somit ein rundes Spielerlebnis zu ermöglichen.

Probleme sind hierbei nicht zu erwarten, da alle, für die Implementierung nötige, Grundlagen, wie das Level für Gegner, bereits
existieren und somit nurnoch diese Möglichkeiten zusammengeführt werden müssen, um das Feature zu erstellen.


Das zweite Feature soll mit Swing arbeiten, um den Namen des Spielers einzugeben. Ob der eingegebene Name gültig ist oder nicht, 
soll dann mit Hilfe eines RegEx ermittelt werden. Hierbei muss darauf geachtet werden, dass der RegEx wirklich nur die Namen 
akzeptiert, die auch akzeptiert werden sollen und keine Namen akzeptiert werden, die ungültig sind.

Der akzeptierte Name wird dann als simples Attribut dem Hero hinzugefügt, damit von überall einfach darauf zugegriffen werden kann.

## Zusatz

Wenn wir mit unseren Aufgaben fertig sind, bevor die letzte Abgabe stattfindet, werden wie die anderen beiden
ursprünglichen Teams in diesem Projekt bei ihren Aufgaben unterstützen, bzw. diese übernehmen, da sie sich mehr vorgenommen haben als
gefordert ist.
Besonders als Ziel gesetzt haben wir uns hierfür die Credits am Ende des Spiels und den Game Over-Bildschirm der Gruppe [Andreas, Malte, Mathis]
und die zufälligen Items und die Währung und Händler der Gruppe [Jonathan, Vivien, Fabius].

# Git-Workflow
- Wir arbeiten mit einem "develop"-Branch, auf welchen wir die Änderungen pushen. Erst wenn das Programm für einen Release / Abgabe bereit ist,
wird in den "master"-Branch gemerged.
- Der "master"-Branch wird immer stable gehalten.
- Der Branch, der am aktuellsten ist, ist der "develop"-Branch.
- Jedes Projektteammitglied forked dieses Repository, um einen verteilten Git-Workflow zu üben.
- Wir planen, ein Buildsystem, wie Ant, Maven oder Gradle zu benutzen, um das Projekt zu builden.
- Es gibt ein Teammitglied, das hauptsächlich die Merge-Requests übernimmt. Code-Reviews werden durchgeführt, wenn es angebracht ist.
