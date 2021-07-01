
# Detaillierter Ansatz und Modellierung
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
- Klasse `BossBattle` extends `Entity`
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

Um die verschiedenen Angriffsmuster und Aktionen des Bosses darzustellen, erstellen wir eine abstrakte Klasse `BossAction`, von der die unterschiedlichen Aktionen erben können.
- Klasse `BossAction` implements `AIStrategy`
    - `int duration`: Dauer des Aktion
    - `int cooldown`: Abklingzeit der Aktion
    - `boolean completed`: wird auf true gesetzt, wenn die Aktion abgeschlossen ist
    - `abstract void execute()`: was beim Ausführen der Aktion passiert (wie z.B. Projektile spawnen, Bodeneffekte erscheinen lassen, weitere Gegner spawnen, etc.)
    - `abstract void onActionEnd()`: was am Ende der Aktion passiert (wie z.B. Projektile despawnen, Bodeneffekte verschwinden, etc.)

### Ideen für Angriffpatterns und Bossmechaniken:
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

Aufgetretende Probleme:
- Der Despawn der `ProjectileSpawner` funktioniert nicht wie gedacht

## Erstellen der BattleActions

Es soll mehrere Bodeneffekte geben (AoE), deshalb wird eine abstrakte Klasse `GroundAoe` erstellt, die das Interface `DamageSource` implementiert und von der die verschiedenen AoEs erben sollen.
- Klasse `GroundAoe` extends `Entity` implements `DamageSource`
    - `int ticksUntilAction`: wie viel Zeit soll nach dem Spawnen vergehen, bis der eigentliche Effekt stattfindet?
    - `int actionCounter`: Zähler für die Dauer
    - `int radius`: Größe (Radius) des Bodeneffekts
    - `boolean shouldDespawn`: Despawn-Flag
    - `abstract void onRoam()`: Wird jeden Frame ausgeführt nach dem der Bodeneffekt gespawnt, aber noch nicht aktiviert wurde
- `abstract void onTrigger()`: Wird ausgeführt wenn genug ticks bis zur Ausführung des Effekts vergangen sind.

Der Golem soll einen "Knockback-Angriff" ausführen, der auf dem Boden einen Effekt auslöst, von dem der Spieler zurückworfen wird, sollte er sich in Reichweite befinden. Dafür wird eine Klasse `KnockbackGroundAOE` erstellt, die von `GroundAoe` erben soll.
- Klasse `KnockbackGroundAOE`

## Konstruieren der konkreten Bosskämpfe

Der erste Bosskamp (Der Golem) soll als Demonstration für das Bossystem im Allegemien dienen:
    - Spawnen von Projektilpatterns
    - Wechseln von Phasen und Bossaktionen
    - Spawnen von AOE-Bodeneffekten
    - Buffen des Bosses

Da jeder Boss sehr individuelle Fähigkeiten und Aktionen bekommen soll, bekommt auch jeder Boss eine eigene Klasse, die von `BossBattle` erbt. Beim implementieren der `GolemBossBattle`-Klasse haben wir auch noch einige Änderungen der abtrakten Klasse `BossBattle` vorgenommen, sodass sich nun folgender Aufbau ergibt.
- Klasse `GolemBossBattle` extends `BossBattle`
    - `HashMap<String, BossPhase> phases`: HashMap in der die Phasen des Bosses gespeichert werden
    - `BossPhase currentPhase`: gibt die aktuelle Bossphase an
    - `boolean isBattleOver`: gibt an, ob der Boss-Kampf vorbei ist (wichtig zum despawnen der Entities)
    - `void switchPhase()`: Wechsel in eine andere Phase

Die `BossBattle`-Klassen für weitere Bosse werden mit der gleichen Struktur erstellt, allerdings mit anderen Phasen.

Als Phasen für den Golem werden folgende Klassen erstellt:

- Klasse `GolemStartPhase` extends `BossPhase`: In der ersten Phase des Golem-Bosses sollen mehrere `ProjectileSpawner` platziert werden. Einer davon hat selbst keine Textur und folgt dem Golem, sodass es wirkt, als würde der Golem selbst die Projektile schießen. Zwei weitere drehende Varianten werden im Bossraum platziert. Außerdem führt der Golem als `KnockbackGroundAOE` in regelmäßigen intervallen einen "Knockback-Angriff" aus, der den Spieler zurückwirft, wenn er sich in Reichweite des Angriffs befindet.

- Klasse `GolemSecondPhase` extends `BossPhase`: In der zweiten Phase des Golem-Bosses soll er sich zu einem Stein zusammenziehen, unverwundbar werden, neue Projektilmuster ausgeben und weitere Entities als Adds spawnen. Die Phase ist nicht an die HP des Bosses gebunden sondern wechselt nach einer festen Anzahl Ticks

- Klasse `GolemThirdPhase` extends `BossPhase`: Die dritte Phase beginnt damit, dass sich der Boss in die Mitte der Arena telportiert und "seinen Arm verliert" (Einen Add spawnt) der den Spieler angreift. Zusätzlich werden Mechaniken aus den vorherigen beiden Phasen miteinander verbunden und der Golem erhält einen schwachen Verteidigungsbuff

- Klasse `GolemLastPhase` extends `BossPhase`: In der vierten Phase werden Projektile in zufällige Richtungen geschossen und der Knockback wird wieder regelmäßig ausgeführt.

