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
    private String secret;

    public String getRadarUrl() {
        return radarUrl;
    }
    public void setRadarUrl(String radarUrl) {
        this.radarUrl = radarUrl;
    }

    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
}
