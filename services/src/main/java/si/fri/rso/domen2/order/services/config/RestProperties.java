package si.fri.rso.domen2.order.services.config;

import javax.enterprise.context.ApplicationScoped;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

@ConfigBundle("rest-properties")
@ApplicationScoped
public class RestProperties {
    
    @ConfigValue(value = "maintenance-mode", watch = true)
    private Boolean maintenanceMode;

    @ConfigValue(value = "broken", watch = true)
    private Boolean broken;

    public Boolean getMaintenanceMode() {
        return this.maintenanceMode;
    }
    public void setMaintenanceMode(final Boolean maintenanceMode) {
        this.maintenanceMode = maintenanceMode;
    }

    public Boolean getBroken() {
        return this.broken;
    }
    public void setBroken(Boolean broken) {
        this.broken = broken;
    }
}
