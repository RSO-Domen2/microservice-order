package si.fri.rso.domen2.order.models.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "order_metadata")
@NamedQueries(value = {
    @NamedQuery(name = "OrderMetadataEntity.getAll",
        query = "SELECT ome FROM OrderMetadataEntity ome")
})
public class OrderMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "clientId")
    private Integer clientId;

    @Column(name = "restaurantId")
    private Integer restaurantId;

    @Column(name = "menuId")
    private Integer menuId;

    @Column(name = "deliverymanId")
    private Integer deliverymanId;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "created")
    private Instant created;

    public Integer getId() {
        return id;
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
        return created;
    }
    public void setCreated(Instant created) {
        this.created = created;
    }
}
