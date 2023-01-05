package si.fri.rso.domen2.order.services.clients;

import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import si.fri.rso.domen2.order.lib.RadarResponseDistance;
import si.fri.rso.domen2.order.lib.RadarShema.RadarResponseGeodesic;
import si.fri.rso.domen2.order.lib.RadarShema.RadarResponseMeta;
import si.fri.rso.domen2.order.lib.RadarShema.RadarResponseRoutes;
import si.fri.rso.domen2.order.lib.RadarShema.RadarResponseTransport;
import si.fri.rso.domen2.order.lib.RadarShema.RadarResponseValue;
import si.fri.rso.domen2.order.services.config.ApiProperties;

/* 
 * Client for Radar API
 * Documentation: https://radar.com/documentation/api
 */
@ApplicationScoped
public class RadarClient {

    private Logger LOG = Logger.getLogger(RadarClient.class.getSimpleName());

    @Inject
    private ApiProperties ap;

    private Client httpClient;

    @PostConstruct
    private void init() { 
        httpClient = ClientBuilder.newClient();
    }

    @Timeout(value = 2000, unit = ChronoUnit.MILLIS)
    //@Fallback(fallbackMethod = "fallbackGetDistance")
    public RadarResponseDistance getDistance(Double start_lat, Double start_lng, Double end_lat, Double end_lng) {
        Response response = httpClient.target(ap.getRadarUrl() + "/v1/route/distance")
            .queryParam("origin", start_lat+","+start_lng)
            .queryParam("destination", end_lat+","+end_lng)
            .queryParam("modes", "car,bike,foot")
            .queryParam("units", "metric")
            .request()
            .header("Authorization", ap.getSecret())
            .get();
        if(response.getStatus() == 200) {
            return response.readEntity(RadarResponseDistance.class);
        }
        return null;
    }

    final Double R = 6371000.0;

    /* Calculates Geodesic distance */
    public RadarResponseDistance fallbackGetDistance(Double start_lat, Double start_lng, Double end_lat, Double end_lng) {
        this.LOG.warning("Fallback getDistanceFallback was used");
        double dLat = Math.toRadians(end_lat - start_lat);
        double dLng = Math.toRadians(end_lng - start_lng);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(start_lat)) * Math.cos(Math.toRadians(end_lat)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = this.R * c;

        return new RadarResponseDistance(
            new RadarResponseMeta(500),
            new RadarResponseRoutes(
                new RadarResponseGeodesic(
                    new RadarResponseValue(distance, "")
                ),
                new RadarResponseTransport( //car
                    new RadarResponseValue(distance, ""),
                    new RadarResponseValue(distance, "")
                ),
                new RadarResponseTransport( //bike
                    new RadarResponseValue(distance, ""),
                    new RadarResponseValue(distance, "")
                ),
                new RadarResponseTransport( //foot
                    new RadarResponseValue(distance, ""),
                    new RadarResponseValue(distance, "")
                )
            )
        );
    }
}
