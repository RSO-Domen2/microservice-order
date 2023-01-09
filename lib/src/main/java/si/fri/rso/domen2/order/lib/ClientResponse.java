package si.fri.rso.domen2.order.lib;

import java.time.Instant;

public class ClientResponse {
    
    public int id;
    public String name;
    public String surname;
    public double lat;
    public double lng;
    public Instant created;
    public String username;
    public String password;
    public String city;
    public String country;
    public int postalCode;
    public String streetName;
    public String streetNumber;

    public ClientResponse() {
        this.id = -1;
        this.name = null;
        this.surname = null;
        this.lat = -1.0;
        this.lng = -1.0;
        this.created = null;
        this.username = null;
        this.password = null;
        this.city = null;
        this.country = null;
        this.postalCode = -1;
        this.streetName = null;
        this.streetNumber = null;
    }
}
