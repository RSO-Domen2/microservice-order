package si.fri.rso.domen2.order.services.config;

import javax.enterprise.context.ApplicationScoped;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

@ConfigBundle("external-services")
@ApplicationScoped
public class ApiProperties {

    @ConfigValue(value = "radar.url")
    private String radarUrl;

    @ConfigValue(value = "radar.secret", watch = true)
    private String radarSecret;

    @ConfigValue(value = "deliveryman.url")
    private String deliverymanUrl;

    @ConfigValue(value = "client.url")
    private String clientUrl;
    
    @ConfigValue(value = "restaurant.url")
    private String restaurantUrl;

    public String getRadarUrl() {
        return radarUrl;
    }
    public void setRadarUrl(String radarUrl) {
        this.radarUrl = radarUrl;
    }

    public String getRadarSecret() {
        return radarSecret;
    }
    public void setRadarSecret(String radarSecret) {
        this.radarSecret = radarSecret;
    }

    public String getDeliverymanUrl() {
        return deliverymanUrl;
    }
    public void setDeliverymanUrl(String deliverymanUrl) {
        this.deliverymanUrl = deliverymanUrl;
    }

    public String getClientUrl() {
        return clientUrl;
    }
    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public String getRestaurantUrl() {
        return restaurantUrl;
    }
    public void setRestaurantUrl(String restaurantUrl) {
        this.restaurantUrl = restaurantUrl;
    }
}
