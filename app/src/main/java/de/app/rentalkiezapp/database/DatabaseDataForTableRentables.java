package de.app.rentalkiezapp.database;

import java.util.ArrayList;
import java.util.List;

import de.app.rentalkiezapp.entity.RentObject;

public class DatabaseDataForTableRentables {
    private static List<RentObject> list = new ArrayList<>();

    DatabaseDataForTableRentables(){

    }

    public static List<RentObject> getRentablesArray(){
        RentObject rentObject1 = new RentObject(1, "Bohrmaschine", "Weiß ja wie´s ist...wers braucht kann Leihen!",  "florian.eppe@web.com", "Bohrmaschine", false);
        RentObject rentObject2 = new RentObject(2, "Fahrradwerkzeug", "Weiß ja wie´s ist...wers braucht kann Leihen! Retoure nach knapp einer Woche, je nach Absprache",  "Max.Mustermann", "Fahrradwerkzeug", false);
        RentObject rentObject3 = new RentObject(3, "Rohrzange", "Rohrzange zu verleihen. Ist einem passablen Zustand. Nimms oder lass liegen",  "florian.eppe@web.com", "Rohrzange", true);
        RentObject rentObject4 = new RentObject(4, "Hammer", "Verleihe meinen Hammer. Hammert sehr gut, auch für Anfänger geeignet. Bei Verletzungen kennen wir uns allerdings nicht!!!",  "Max.Mustermann@web.com", "Hammer", false);
        RentObject rentObject5 = new RentObject(5, "Toaster", "Liefert ausschließlich die feinsten Brotscheiben nach Toastvorgang. Selbst überzeugen: Ausleihen",  "florian.eppe@web.com", "Toaster", true);
        RentObject rentObject6 = new RentObject(6, "Lampe", "Falls uns bald ganz der Saft abgedreht wird: Verleihe eine lang leuchtende Lampe. Man weis ja nie heutzutage...",  "florian.eppe@web.com", "AlteLampe", false);
        RentObject rentObject7 = new RentObject(7, "Wasserflasche", "Das Feinste Wasser und andere Weichgetränke lassen sich hierraus genüsslich schlürfen. Bitte sauber wieder zurückgeben.",  "florian.eppe@web.com", "Wasserflasche", false);
        RentObject rentObject8 = new RentObject(8, "Zimmerpflanze", "Für etwas frische Luft auch in Ihren vier Wenden verleihe ich meine stets verpflegte Zimmerpflanze.",  "florian.eppe@web.com", "Zimmerpflanze", false);
        RentObject rentObject9 = new RentObject(9, "Joggingschuhe", "Joggingschuhe zu verleihen. Stinken weniger, wenn man die Nase zuhält. Sind auch schön luftig um die Zehen!",  "florian.eppe@web.com", "Joggingschuhe", false);
        RentObject rentObject10 = new RentObject(10, "Radio", "Letzens auf einen CD-Spieler als neue Nachrichtenquelle umgestiegen. Zeitweise verleihe ich deshalb mein altes Radio",  "florian.eppe@web.com", "Radio", false);


        list.add(rentObject1);
        list.add(rentObject2);
        list.add(rentObject3);
        list.add(rentObject4);
        list.add(rentObject5);
        list.add(rentObject6);
        list.add(rentObject7);
        list.add(rentObject8);
        list.add(rentObject9);

        return list;
    }
}
