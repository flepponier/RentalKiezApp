package de.app.rentalkiezapp.database;

import java.util.ArrayList;
import java.util.List;

import de.app.rentalkiezapp.entity.RentObject;

public class DatabaseDataOfRentObjects {
    private List<RentObject> list = new ArrayList<>();

    DatabaseDataOfRentObjects(){
        fillListWithData();
    }

    private void fillListWithData() {
        RentObject rentObject = new RentObject(1, "Bohrmaschine", "Weiß ja wie´s ist...wers braucht kann Leihen!", "Sehr gut", "florian.eppe@web.com", "Bohrmaschine", false);
        RentObject rentObject = new RentObject(1, "Bohrmaschine", "Weiß ja wie´s ist...wers braucht kann Leihen!", "Sehr gut", "florian.eppe@web.com", "Rohrzange", false);

    }
}
