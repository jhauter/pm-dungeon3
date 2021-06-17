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


## Bosse
### Detaillierter Ansatz und Modellierung
Um es in Zukunft einfacher zu Gestalten möglicherweise komplett eigene Bossdungeons zu Laden, haben wir einen einfachen, rudimentären `DungeonStageLoader` implementiert, der bislang Teile der Funktionalitäten von `DungeonStart` wrapped:
    - `loadNextStage()` lädt die nächste Stage aus den vorgegeben DungeonLeveln (Momentan Zufällig)
    - `onStageLoad()` wird ausgeführt wenn Stage geladen wurde, platziert Entitäten
Zusätzlich wird jeder Stage ein `StageType` zugewisen (Momentan Boss und Normal), wird eine BossStage geladen,
platzieren wir keine Gegner (Ändern wir in der Zukunft möglicherweise) und starten stattdessen ein Bosskampf
(s.u)

Wir erstellen eine Klasse `Boss` um den Boss darzustellen. Diese erbt von `NPC`.

- Klasse `Boss` extends `NPC` mit folgenden Eigenschaften und Funktionen:
    - `BossDifficulty difficulty`: Schwierigkeit des Bosses
    - `void build(BossBuilder builder)`: erstellt einen Boss mit Hilfe des `BossBuilder`s
    - `Boss child`

Um die Schwierigkeit des Bosses anpassen zu können, verwenden wir ein enum mit den entsprechenden Schwierigkeitsstufen.
- enum `BossDifficulty`: Die Schwierigkeit des Bosses (Easy, Normal, Hard, Nightmare)

Der Boss selbst soll in einer Klasse `BossBuilder` "gebaut" werden um einen riesigen Konstruktor zu vermeiden.
- Klasse `BossBuilder`
    - `Optional<Integer> level`: Level des Bosses
    - `Optional<BossDifficulty> difficulty`: Schwierigkeit des Bosses (s. enum `BossDifficulty`)
    - `Optional<Integer> maxHP`: Lebenspunkte des Bosses
    - `Optional<Vektor2> renderScale`: Scaling fürs Rendering
    - `List<BossAction> actions`: eine Liste mit allen `BossAction`s, die der Boss "können" soll

Im Boss-Fight sollen unterschiedliche Projektile spawnen und umherfiegen. Dafür wird eine Klasse `ProjectileSpawner` erstellt, die von Entity erbt.
- Klasse `ProjectileSpawner` extends `Entity`
    - `int ShootingIntervall`: Angriffsgeschwindigkeit, in der Projektile geschossen werden
    - `int ShootingIntervallTimer`: wird für die Angriffsgeschwindigkeit benötigt
    - `CreatureStats parentStats`: wird als Parameter für das Erstellen eines neuen `ArrowProjectile`s benötigt
    - `List<ProjectileMovementPattern> patterns`: Liste für die Bewegungen und Drehungen, die vom `ProjectileSpawner` ausgeführt werden.
    - `Vektor2 facing`: Blickrichtung in die die gespawnten Projektile fliegen
    - `float projectileSpeed`: Geschwindigkeit der Projektile
    - `void shoot()`: erzeugt ein `ArrowProjectile`
    - `void setFacing(float angle)`: verändert die Blickrichtung


Außerdem erstellen wir eine Klasse `BossBattle`, die alle benötigten Informationen, Variablen und Funktionalitäten beinhaltet, die für den Boss-Kampf benötigt werden.
- Klasse `BossBattle` extends `Entity` *(Detailierter Aufbau siehe UML_bosse.png)*
    - `enum BossState`: Stellt den aktuellen Zustand des Bosses dar (Normal, Low), abhängig von den aktuellen HP vom Boss oder der abgelaufenen Zeit im Boss-Fight und nicht von den "normalen" `BossActions` die durch die Queue durchlaufen werden. Dadurch ist beispielsweise ein "Ragemode" möglich, wenn der Boss wenig HP hat.
    - `enum ProjectilePatterns`: 
    - `BossState currentState`: momentaner Zustand es Bosses (Normal oder Low)
    - `Boss boss`: der Boss selbst
    - `Queue<BossAction> stages`: Queue mit den Aktionen des Bosses, die nacheinander ausgeführt werden.
    
    - `void spawnBoss(Boss boss)`: Boss spawnen
    - `void prepareArea()`: andere Gegner despawnen, Leitern deaktivieren, etc


Da der Boss erst spawnen soll, wenn der Spieler einen bestimmten Punkt im Dungeon erreicht, erstellen wir eine Klasse `Trigger`, die von `Entity` erbt. Der `Trigger` soll unsichtbar sein und ein Event auslösen, sobald der Spieler mit dem `Trigger` kollidiert. So können wir dann beispielsweise den Boss spawnen, andere Gegner despawnen und/oder eine Zwischensequenz abspielen. Außerdem sollten Leitern deaktiviert werden und erst wieder aktiviert werden sobald der Boss besiegt wurde.
- Klasse `Trigger` extends `Entity`


Um eventuelle Zwischensequenzen vor, während oder nach dem Boss-Kampf abspielen zu können, erstellen wir eine Klasse `BossCutsceneHandler`.
- Klasse `BossCutsceneHandler`

Jeder Boss soll verschiedene Phasen durchlaufen, die jeweils eigene Angriffsstrategien und Aktionen haben. Das wollen wir mit einer Klassen `BossPhase` umsetzen, die wiederum mehrere `BossActions` aggregiert

Um die verschiedenen Angriffsmuster und Aktionen des Bosses darzustellen, erstellen wir eine abstrakte Klasse `BossAction`, die das Interface `AIStrategy` implementiert und von der die unterschiedlichen Aktionen erben können.
- Klasse `BossAction` implements `AIStrategy`

#### Ideen für Angriffpatterns und Bossmechaniken:
- ProjectileSpawner (Feuertürme, etc.)
- Slimeboss, der sich teilt
- drehende Feuerprojektile
- Laser
- Heilung
- Schild
- AoE (Explosion?)
- Giftpfützen
- Adds spawnen
- Unverwundbarkeit
- zielsuchende Projektile (folgen dem Spieler)
```