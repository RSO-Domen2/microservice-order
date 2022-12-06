package si.fri.rso.domen2.order.api.v1.health;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import si.fri.rso.domen2.order.services.config.RestProperties;

@Liveness
@ApplicationScoped
public class ManualBreakHealthCheck implements HealthCheck {

    @Inject
    private RestProperties rp;

    @Override
    public HealthCheckResponse call() {
        if(rp.getBroken()) {
            return HealthCheckResponse.down(ManualBreakHealthCheck.class.getSimpleName());
        }
        else {
            return HealthCheckResponse.up(ManualBreakHealthCheck.class.getSimpleName());
        }
    }
}
