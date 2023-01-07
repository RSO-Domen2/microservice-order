package si.fri.rso.domen2.order.services.clients.fallbacks;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

import si.fri.rso.domen2.order.lib.RadarResponseDistance;
import si.fri.rso.domen2.order.lib.RadarShema.RadarResponseGeodesic;
import si.fri.rso.domen2.order.lib.RadarShema.RadarResponseMeta;
import si.fri.rso.domen2.order.lib.RadarShema.RadarResponseRoutes;
import si.fri.rso.domen2.order.lib.RadarShema.RadarResponseTransport;
import si.fri.rso.domen2.order.lib.RadarShema.RadarResponseValue;

public class RadarDistanceFallback implements FallbackHandler<RadarResponseDistance> {

    final Double R = 6371000.0;

    @Override
    public RadarResponseDistance handle(ExecutionContext context) {
        Object[] params = context.getParameters();
        Double start_lat = (Double) params[0];
        Double start_lng = (Double) params[1];
        Double end_lat = (Double) params[2];
        Double end_lng = (Double) params[3];
        
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
                    new RadarResponseValue(distance / 833, "") // 50 km/h -> 833 m/min
                ),
                new RadarResponseTransport( //bike
                    new RadarResponseValue(distance, ""),
                    new RadarResponseValue(distance / 333, "") // 20 km/h -> 333 m/min
                ),
                new RadarResponseTransport( //foot
                    new RadarResponseValue(distance, ""),
                    new RadarResponseValue(distance / 100, "") // 6 km/h -> 100 m/min
                )
            )
        );
    }
    
}
