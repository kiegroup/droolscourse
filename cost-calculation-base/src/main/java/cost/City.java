package cost;

public class City {

    public static String ShangaiCityName = "Shangai";
    public static String RotterdamCityName = "Rotterdam";
    public static String TournaiCityName = "Tournai";
    public static String LilleCityName = "Lille";
    private String name;

    public City(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City [name=" + name + "]";
    }


}
