package si.fri.rso.domen2.order.services.clients;

import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import si.fri.rso.domen2.order.lib.Coordinate;
import si.fri.rso.domen2.order.services.config.ApiProperties;

/* 
 * Client for Radar API
 * Documentation: https://radar.com/documentation/api
 */
public class RadarClient {

    private Logger LOG = Logger.getLogger(RadarClient.class.getSimpleName());

    @Inject
    private ApiProperties ap;

    private Client httpClient;

    @PostConstruct
    private void init() { 
        httpClient = ClientBuilder.newClient();
    }

    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    @CircuitBreaker(requestVolumeThreshold = 5)
    @Fallback(fallbackMethod = "getDistanceFallback")
    public Double getDistance(Coordinate start, Coordinate end) throws Exception {
        Double result = null;
        try {
            this.httpClient.target(ap.getRadarUrl() + "/v1/route/distance")
                .queryParam("origin", start.getLat(), start.getLng())
                .queryParam("destination", end.getLat(), end.getLng())
                .queryParam("modes", "car").queryParam("units", "metric")
                .request().get(); // TODO response is JSON, parse it and use it
        } catch(Exception e) {
            LOG.severe(e.getMessage());
            throw new Exception();
        }
        return result;
    }

    final Double R = 6378.137;
    public Double getDistanceFallback(Coordinate start, Coordinate end) {
        /* Calculates Haversine distance */
        double phi1 = start.getLat();
        double phi2 = end.getLat();
        double lambda1 = start.getLng();
        double lambda2 = end.getLng();
        double inter1 = Math.pow(Math.sin((phi2-phi1)/2), 2);
        double inter2 = Math.pow(Math.sin((lambda2-lambda1)/2), 2) * Math.cos(phi1) * Math.cos(phi2);
        return 2*this.R*Math.asin(Math.sqrt(inter1 + inter2));
    }
}
