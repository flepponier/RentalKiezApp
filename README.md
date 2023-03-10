# RentalKiezApp

Bedienungshilfe

1. RENTALKIEZ.db in Verzeichnis von Emulator/Android Handy speichern /data/data/de.app.rentalkiezapp/databases/RENTALKIEZ.db
    DB via SQLite Browser erstellt, da programmatische Erstellung im Kontext meiner App keinen Sinn macht
    1. WICHTIG: man muss ggf. die Datenbankversion in den Klassen "DataSourceRegistry" und "DataSourceRentables" inkrementieren, wenn die Fehlermeldung "E/SQLiteLog: (1) no such table: RENTABLES_TABLE" auftritt
    2. Das funktioniert leider nicht immer, aus mir nicht erklärlichen Gründen...sqlite db...es muss auf jeden Fall funktionieren
2. Zugangsdaten für Benutzer, welche Korrespondierende RentObjects in DB haben
    1. USER: florian.eppe@web.de PW: 123456
    2. USER: Max.Mustermann@web.de PW: 123456
3. RentActivity (login -> Rent): Hinweise für Filter
    1. Drop Down Menü: Berlin, Charlottenburg und Friedenau haben korrespondierende RentObjects in DB, bei Auswahl von anderen kommt ordnungsgemäß eine Fehlermeldung
    2. Suche: nach Zipcode -> von 1. USER: 10711, von 2. USER: 10369; nach Text -> Eingabe "Lam" für Lampe oder "Hamm" für Hammer, usw. möglich
4. Emulator bzw. Handy braucht Internetzugang für Firebase-Funktionalitäten
  

Weitere Informationen:

1. Filter-Funktionalität etwas weiter ausgebaut, als vorab besprochen (siehe Ablaufdiagramm_UPDATED)
   1. Suche nimmt sowohl zipcode, als auch Text-input
   2. Drop-Down Menü: zeigt Bezirke und filtert in DB auf Postleitzahlbereiche
3. Nicht implementiert (wie besprochen sowieso optional): BLOB -> png , Image-Quelle ist der drawable Ordner, nicht die DB
4. Visuell ansprechend ist die App nicht unbedingt, ich habe mir Mühe gegeben :D
5. Holy das war ein ordentliches Stück Arbeit


Bekannter "Fehler":

1. Ausgangspunkt LendObjectActivity (login -> lend -> choose some Object): WENN Änderung der Verfügbarkeit des RentObjekts durch CheckBox und drücken auf "Back"-Button,
DANN listView wird nicht direkt geupdated. Erst duch erneutes Drücken des "Back"-Buttons und Auswahl -> lend wird die listView geupdated

Hat Spaß gemacht, danke fürs Semester! :D
