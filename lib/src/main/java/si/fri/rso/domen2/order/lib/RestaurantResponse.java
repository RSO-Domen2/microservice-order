package si.fri.rso.domen2.order.lib;

import java.time.Instant;

public class RestaurantResponse {

    public int id;
    public String name;
    public double lat;
    public double lng;
    public Instant created;
    public String country;
    public String city;
    public int postalCode;
    public String streetName;
    public String streetNumber;

    public RestaurantResponse() {
        this.id = -1;
        this.name = null;
        this.lat = -1.0;
        this.lng = -1.0;
        this.country = null;
        this.city = null;
        this.postalCode = -1;
        this.streetName = null;
        this.streetNumber = null;
        this.created = Instant.now();
    }
}
