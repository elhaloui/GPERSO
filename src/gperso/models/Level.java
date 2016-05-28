package gperso.models;

/**
 * Created by dimMaryanto on 1/15/2016.
 */
public enum Level {
    ADMIM("CHEF DE CORPS"),
    CHEF("CHEF DE SERVICE"),
    PERSONNEL("PERSONNEL");
    

    private String value;

    Level(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
