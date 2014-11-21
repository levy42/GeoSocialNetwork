package tools;

/**
 * Created by oleh on 28.10.2014.
 */
public class LocationInfo {
    private int id;
    private DBApi.Location location;

    public DBApi.Location getLocation() {
        return location;
    }

    public void setLocation(DBApi.Location location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
