```


## Dynamisches Speichern

### Feature

-   Der Held/Spieler speichert nicht von sich aus. "Dieses Spiel speichert an bestimmten Stellen selber".
-   Eine kleine Animation am rechten Bildschirm Rand das signalisiert das gespeichert wurde.
-   Die Speicher Datei in ein passendes Dateiformat, mit Zeit Stempel sichern.
-   Die Möglichkeit gespeicherte Spielstände zu laden.
-   Möglichkeit Spielstände bei Bedarf zu löschen oder zu überschreiben ?

### Technik

Bei Recherchen lief uns das GDX eigenes Preference System über den Weg.
Zum speichern gibt es eine Klasse die wir `MemoryData` genannt haben.
Diese Klasse bietet nach außen eine öffentliche Methode `void : save()`.
Save führt intern die Speicherung des Heldens aus.
Dazu werden :
-   Klassen Type
-   Positionsdaten in FLießkommazahlen
-   Ein Zähler für die DungeonWorld
-   Lebenspunkte
-   Erfahrungspunkte
-   Inventar sowie ausgerüstete Items,

in die Preference Map gepackt.

Die Methode save wird von der Klasse `MemoryDataHandler` aufgerufen.
Diese Klasse soll die eigentliche Schnittstelle für den User darstellen.
Der sicherheitswegen folgt diese Klasse dem Singleton Pattern schema.
Diese Klasse besitzt nach außen drei bisher relevanten Methoden:
-   `void: save` : um den User speichern zu lassen.
-   `Creature : getSavedPlayer()`: gibt eine Kopie des gespeicherten Player zurück
-   `String : getSavedLevel()` : gibt den String für den LevelController, zum laden, zurück.

Der einfachheitshalber wird in der Klasse `MemoryData` bisher nur der Spieler gespeichert.
Allerdings ist das System leicht auf alle Creature erweiterbar weshalb die Methode `getSavedPlayer`
bereits ein Creature zurückgibt, denn bis auf das Inventar (welches bereits nur bei einer Instance Typ Player gespeichert wird), werden auch bereits alles notwendige Daten zum 
kopieren von NPC's gespeichert. 

Um die richtige Instanz des Creature muss sich dank Reflection nicht gekümmert werden, so das wir nicht zusätzlich jedem Entity eine ID geben mussten. 

Um mit dem LevelController auch das richtige Level zu laden Configurieren wir die Kamara und den Controller selber nach und schalten das SetUp um das uns zwingt den ersten Dungeon zu laden.


### Probleme

Wir hatten vor einen Data/Object Out/Inputstream zu verwenden.
Als das Grundgerüst für die Serialiserung gebaut war bermerkten wir das es während des laufenden Spiels nicht einfach so verwendet werden kann.
Bei Recherchen trafen wir auf das Preference System von GDX.
Vorteil dieses System ist die unglaublich einfache Handhabung, da es intern einfach eine Art HashMap mit String als Key und einem primitiven DatenTyp, einschließlich Strings, verwendet.
Dadurch werden wirklich nur die nötigsten Daten gespeichert und der Datensatz bleibt sehr klein.
Ausserdem bleibt die Datei für jeden lesbar, es sei denn man möchte dies verhindern.

Nun bleibt bisher nur noch das Problem, das es eine Gui geben muss, die uns entscheiden lässt ob wir einen gespeicherten Spielstand oder aber einen neuen Helden haben möchten.
In unserer Main wird es bisher so gehandhabt das wenn es einen gespeicherten Player gibt dieser auch in das entsprechende Level mit all seinen gespeicherten Daten geladen wird.

Ein WorkAround ohne Gui ist aber bereits in der Hinterhand und könnte ohne Probleme ähnlich unserem QuestDummy eingefügt werden.
Ein NPC/Entity welches angesprochen werden kann.
Dieses steht im ersten Level wenn es einen alten Helden gibt.
Nachdem es angesprochen wurde, kann der Player entscheiden, ob er in den alten Speicherstand wechseln will oder mit dem neuen Helden weiterspielen will.

Wir könnten diesen Interaktiven NPC's eine Schnittstelle geben die bspw. Interactive heißt und so die vorhandenen `InputSrategy`s, die bisher nur für den QuestDummy relevant sind, erweitern. 
```