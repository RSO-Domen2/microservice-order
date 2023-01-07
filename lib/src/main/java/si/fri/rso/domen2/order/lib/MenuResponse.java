package si.fri.rso.domen2.order.lib;

public class MenuResponse {

    public int id;
    public int restaurant_id;
    public String name;
    public double price;
    
    public MenuResponse() {
        this.id = -1;
        this.restaurant_id = -1;
        this.name = null;
        this.price = -1.0;
    }
}
