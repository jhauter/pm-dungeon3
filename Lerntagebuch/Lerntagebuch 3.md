## Dynamisches Speichern
### Feature
- Der Held/Spieler speichert nicht von sich aus. "Dieses Spiel speichert an bestimmten Stellen selber".
- Eine kleine Animation am rechten Bildschirm Rand das signalisiert das gespeichert wurde.
- Die Speicher Datei in ein passendes Dateiformat, mit Zeit Stempel sichern.
- Die Möglichkeit gespeicherte Spielstände zu laden.
- Möglichkeit Spielstände bei Bedarf zu löschen oder zu überschreiben ?

### Technik
Zum Speichern benutzen wir eine Klasse `MemoryData`die alle nötigen Informationen des Spielers speichert.

Das sind :
- Der DisplayName
- Der KlassenTyp
- Die Position
- Das Inventar und seine Größe
- Die Ausgerüsteten Items und die Größe
- Die Status Werte

Damit auch die zufälligen Items samt zufälligen Statuswerten und Displayname gespeichert werden soll gibt es eine Klasse
ItemInformation.
Sie speichert drei wesentliche Punkte:
- Der DisplayName
- Den KlassenTypen
- Die ItemStats

Um diese Informationen jetzt in eine Datei zu speichern benutzten wir das mitgelieferte Gson.
Dadurch lässt sich die gesamte Klasse, die wir jetzt erstellt haben, in einen String parsen und so in eine Lokale Datei speichern.
Zum Laden wird der String dann von Gson mittels Reflection wieder zu der zuvor erstellten `MemoryData`.
Dies lässt uns den alten Spieler einfach wieder aufbauen und im benötigten Fall laden.

Jedes Attribut lässt sich so einfach wieder herstellen.
Um den Player selber zu laden benutzen wir den gespeicherte KlassenTyp und erzeugen mit Reflection so den alten Player und auch seinen HeldenTyp.
Die meisten anderen Daten können einfach gesetzt werden.

Dafür wird die `MemoryData`genommen und einfach das jeweilige Attribut gesetzt.

Bspw : `player.setPosition(memoryData.position)`

Für die Items die jetzt durch die zufälligen Items 3 zusätzliche Daten haben wird hier geprüft ob der `String Classtyp` von

`ItemInformation` zu einem der registrierten Items passt.

Wenn ja wird das item zu diesem und der Name sowie die `attackstats` werden zu den gespeicherten Daten gesetzt.
Das Level selber wird abgezählt und je nach Zahl wird ein String für den Player in die MemoryData gespeichert welches der
LevelController laden kann.

### Probleme

Zuerst gabs das Serialisationsproblem. Dafür funktionierten die Preferencen.
Seit die Items zufällig wurden funktionierten die Preferencen nicht mehr akkurat.
Mit Gson haben wir nun eine einfache und sehr beliebte Lösung gefunden die sogar Lesbarkeit bietet und kleine DatenMengen benutzt.

Ein Vorteil könnte es jetzt sein eine so einfach Klasse mit den nötigen Daten zu haben die auch Serialisierbar bleibt und ebenfalls leicht verändert/ vergrößert werden könnte.

Bspw gehört bis auf die Items nahezu alles auch zu jedem anderen Creature das damit ausgestattet werden könnte.

Gezeigt hat sich das beim Speichern schnell kleine Änderungen zu größeren störende Probleme führen kann.

## Credits und Game Over-Screen
### Feature
Wenn man den Endboss besiegt, soll ein Abspann eingeblendet werden, in dem die Namen der Mitwirken angezeigt werden.
Außerdem soll ein Game Over-Screen eingeblendet werden, wenn der Spieler besiegt wird.

### Technik
Beide Screens haben im Grunde genommen die selbe Funktionsweise. Es gibt eine Variable
`float alpha`, welche die Transparenz des Screens angibt. Wird der Screen eingeblendet, zählt diese Variable hoch, bis sie `1.0` erreicht hat, was für komplett sichtbar steht. Bei den Credits ist der Hintergrund bloß ein schwarzes Bild. Danach werden horizontal zentrierte Label angezeigt, welche unsere Namen beinhalten, angezeigt. Diese Labels sind untereinander angeordnet und haben alle einen relativen Y-Abstand zueinander und einen absoluten Y-Offset.
Dieser Offset ist so eingestellt, dass die Labels am Anfang nicht sichtbar sind. Sobald der `alpha`-Wert `1.0`erreicht hat, beginnt der Y-Offset zu steigen, bis die Credits den oberen Bildschirmrand erreicht haben. Ab dann sinkt der `alpha`-Wert wieder auf `0.0`ab.

Bei dem Game Over-Screen funktioniert das mit dem `alpha`-Wer genau so, nur dass anstelle eines schwarz gefüllten Bildes ein Bild angezeigt wird, das den Schriftzug "Game Over" beinhaltet. Dieses Bild ist komplett schwarz gefüllt, mit Ausnahme des Schriftzuges. Der Schriftzug hat keine Farbe, sondern ist durchsichtig. Dieses Bild wird auch mit zunehmender Sichtbarkeit angezeigt. Sobald das Bild komplett sichtbar ist, wird ein einfarbiges rotes Bild hinterlegt, dessen `alpha`-Wer genau so funktioniert, wie bisher beschrieben.

Diese Screens werden mithilfe eines Phasensystems programmiert, welches den `alpha`-Wert des Hintergrundes misst, und bei `1.0` in die nächste Phase wechselt. Während der ersten Phase steigt der Wert an. Jede Phase erhöht im Prinzip einen Wert so lange bis er das Maximum erreicht hat, und wechselt dann in die nächste Phase.