package si.fri.rso.domen2.order.lib;

import java.time.Instant;

public class DeliverymanResponse {
    
    public int id;
    public String name;
    public String surname;
    public String vehicle;
    public double lat;
    public double lng;
    public double hourlyPay;
    public double transportPrice;
    public Instant created;

    public DeliverymanResponse() {
        this.id = -1;
        this.name = null;
        this.surname = null;
        this.vehicle = null;
        this.lat = -1.0;
        this.lng = -1.0;
        this.hourlyPay = -1.0;
        this.transportPrice = -1.0;
        this.created = Instant.now();
    }
}
