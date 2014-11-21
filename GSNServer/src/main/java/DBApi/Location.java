package DBApi;

import javax.persistence.*;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",columnDefinition = "MEDIUMINT NOT NULL AUTO_INCREMENT")
    private int id;
    @Column(name = "longitude")
    private float longitude;
    @Column(name = "latitude")
    private float latitude;

    public Location(){}

    public Location(float latitude,float longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
