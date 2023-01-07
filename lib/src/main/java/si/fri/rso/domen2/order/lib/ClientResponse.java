package si.fri.rso.domen2.order.lib;

import java.time.Instant;

public class ClientResponse {
    
    public int id;
    public String name;
    public String surname;
    public double lat;
    public double lng;
    public Instant created;
    public String street_number;
    public String street_name;
    public int postal_code;
    public String country;
    public String city;
    public String username;
    public String password;

    public ClientResponse() {
        this.id = -1;
        this.name = null;
        this.surname = null;
        this.lat = -1.0;
        this.lng = -1.0;
        this.created = null;
        this.street_number = null;
        this.street_name = null;
        this.postal_code = -1;
        this.country = null;
        this.city = null;
        this.username = null;
        this.password = null;
    }
}
