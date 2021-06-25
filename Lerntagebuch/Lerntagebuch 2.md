---
title: "Meilenstein 2"
...


## Fog of War-Verbesserung

### Ansatz und Modellierung

Das Licht, das auf den Spieler scheint und den Nebel verdrängt, soll nicht mehr durch Wände hindurch scheinen.
Dazu benutzen wir einen rekursiven Algorithmus, der jeden Block einzeln berechnet, der beleuchtet wird und in welcher Stärke.
Der Algorithmus nimmt eine Startposition und eine Startstärke.
Dann überprüft er, ob die benachbarten Blöcke Wände sind oder nicht. Wenn sie keine Wände sind, wird der Algorithmus auf den benachbarten
Positionen erneut ausgeführt, aber mit einer geringeren Stärke. Wenn die Stärke 0 erreicht, wird der Algorithmus beendet.
Vom Algorithmus werden außerdem Blöcke übersprungen, in denen der Nebel bereits weit genug zurückgewichen ist.

Wir haben festgestellt, dass der Algorithmus sehr viel Laufzeit benötigt. Somit sind einige Optimierungen nötig.
Zu erst stellen wir die Auflösung des Lichts runter. Der Algorithmus hat eine exponentiell steigende Laufzeit mit mehr zu berechnenden Lichtblöcken.
Somit macht es einen großen unterschied, wenn man die Auflösung etwas runterschraubt.

Wenn man die Auflösung weiter runterschraubt, sieht man aber die einzelnen Lichblöcke eher und es sieht zu blöckig aus.
Um die Lichtblöcke zu glätten haben wir uns eine Technik überlegt, bei der man die Lichtblöcke als Pixel in einer Maske in eine kleine Textur speichert.
Diese Maske wird auf die Größe des Bildschirms skaliert und zwar so, dass die Pixel der Lichtblöcke mit den echten Positionen übereinstimmen.
Durch die Skalierung wird die Maske geglättet. Nun wird die Nebeltextur so über die Maske gezeichnet, dass der Nebel nur im maskierten
Bereich gezeichnet wird. Dadurch erhält man einen glatten Lichteffekt.