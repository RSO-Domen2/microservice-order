package si.fri.rso.domen2.order.lib;

import java.time.Instant;

public class OrderMetadata {
    
    private Integer id;
    private Integer clientId;
    private Integer restaurantId;
    private Integer menuId;
    private Integer deliverymanId;
    private Double cost;
    private Instant created;

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getMenuId() {
        return menuId;
    }
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getDeliverymanId() {
        return deliverymanId;
    }
    public void setDeliverymanId(Integer deliverymanId) {
        this.deliverymanId = deliverymanId;
    }

    public Double getCost() {
        return cost;
    }
    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Instant getCreated() {
        return this.created;
    }
    public void setCreated(Instant created) {
        this.created = created;
    }
}
