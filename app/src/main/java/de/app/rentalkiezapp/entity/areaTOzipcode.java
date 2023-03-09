package de.app.rentalkiezapp.entity;


//areaTOzipcode.class returns range of zipcodes depending on selected Area

public class areaTOzipcode {
    private final static String BERLIN = "Berlin";
    private final static String CHARLOTTENBURG = "Charlottenburg";
    private final static String FENNPFUHL ="Fennpfuhl";
    private final static String FRIEDENAU = "Friedenau";
    // alle übrigen Stadteile Berlins .... ,
    // abhängig von den Daten in meiner RENTABLES_TABLE reicht Charlottenburg
    // deshalb spare ich mir an dieser Stelle unnötige Mehrarbeit

    public static String [] get_query_for_zipcode_range_dependent_on_area(String selection){
        switch(selection){
            case BERLIN:
                String [] args = {"10115", "14199"};
                return args;
            case CHARLOTTENBURG:
                String [] args1 = {"10585", "14059"};
                return args1;
            case FENNPFUHL:
                //usw. und sofort
                break;
            default:
        }


        return null;
    }
}
