package si.fri.rso.domen2.order.services.config;

import javax.enterprise.context.ApplicationScoped;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

@ConfigBundle("app-properties")
@ApplicationScoped
public class ApiProperties {

    @ConfigValue(value = "external-services.radar-url", watch = true)
    private Boolean radarUrl;

    public Boolean getRadarUrl() {
        return radarUrl;
    }
    public void setRadarUrl(Boolean routingUrl) {
        this.radarUrl = routingUrl;
    }
}
