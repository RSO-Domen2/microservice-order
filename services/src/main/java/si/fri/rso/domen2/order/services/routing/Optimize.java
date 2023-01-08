package si.fri.rso.domen2.order.services.routing;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import si.fri.rso.domen2.order.lib.ClientResponse;
import si.fri.rso.domen2.order.lib.DeliverymanResponse;
import si.fri.rso.domen2.order.lib.MenuResponse;
import si.fri.rso.domen2.order.lib.OrderMetadata;
import si.fri.rso.domen2.order.lib.RadarResponseDistance;
import si.fri.rso.domen2.order.lib.RestaurantResponse;
import si.fri.rso.domen2.order.services.clients.ClientClient;
import si.fri.rso.domen2.order.services.clients.DeliverymanClient;
import si.fri.rso.domen2.order.services.clients.RadarClient;
import si.fri.rso.domen2.order.services.clients.RestaurantClient;

@ApplicationScoped
public class Optimize {

    private Logger LOG = Logger.getLogger(Optimize.class.getName());

    @Inject
    private RadarClient radar;

    @Inject
    private DeliverymanClient dc;

    @Inject
    private ClientClient cc;

    @Inject
    private RestaurantClient rc;

    public OrderMetadata calculateBestDeliveryman(OrderMetadata om) {
        DeliverymanResponse[] dr = dc.getAllDeliveryman();
        ClientResponse cr = cc.getClient(om.getClientId());
        RestaurantResponse rr = rc.getRestaurant(om.getRestaurantId());
        MenuResponse mr = rc.getMenu(om.getMenuId());

        if(dr == null || cr == null || rr == null || mr == null) {
            this.LOG.warning("One of the responses was null");
            return om;
        }

        double baseCost = mr.price;

        RadarResponseDistance baseDistance = radar.getDistance(rr.lat, rr.lng, cr.lat, cr.lng);

        if(baseDistance == null) {
            this.LOG.warning("Radar baseDistance response was null");
            return om;
        }
        
        int bestDeliveryman = 1;
        DeliverymanResponse currentDeliveryman = dr[0];
        RadarResponseDistance ctr = radar.getDistance(rr.lat, rr.lng, currentDeliveryman.lat, currentDeliveryman.lng);
        double lowestCost = this.calculateCost(currentDeliveryman, baseDistance, ctr);

        for(int i = 1; i < dr.length; i++) {
            currentDeliveryman = dr[i];
            ctr = radar.getDistance(rr.lat, rr.lng, currentDeliveryman.lat, currentDeliveryman.lng);
            double cost = this.calculateCost(currentDeliveryman, baseDistance, ctr);
            if(cost < lowestCost) {
                lowestCost = cost;
                bestDeliveryman = i+1;
            }
        }

        om.setDeliverymanId(bestDeliveryman);
        om.setCost(baseCost+lowestCost);
        return om;
    }

    private double calculateCost(DeliverymanResponse dm, RadarResponseDistance base, RadarResponseDistance ctr) {
        double cost = 0.0;
        switch(dm.vehicle) {
            case "car":
                cost += dm.transportPrice * base.routes.car.distance.value / 1000;
                cost += dm.hourlyPay * base.routes.car.duration.value / 60;
                cost += dm.transportPrice * ctr.routes.car.distance.value / 1000;
                cost += dm.hourlyPay * ctr.routes.car.duration.value / 60;
                break;
            case "bike":
                cost += dm.transportPrice * base.routes.bike.distance.value / 1000;
                cost += dm.hourlyPay * base.routes.bike.duration.value / 60;
                cost += dm.transportPrice * ctr.routes.bike.distance.value / 1000;
                cost += dm.hourlyPay * ctr.routes.bike.duration.value / 60;
                break;
            default: //foot
                cost += dm.transportPrice * base.routes.foot.distance.value / 1000;
                cost += dm.hourlyPay * base.routes.foot.duration.value / 60;
                cost += dm.transportPrice * ctr.routes.foot.distance.value / 1000;
                cost += dm.hourlyPay * ctr.routes.foot.duration.value / 60;
                break;
        }
        return cost;
    }
}
