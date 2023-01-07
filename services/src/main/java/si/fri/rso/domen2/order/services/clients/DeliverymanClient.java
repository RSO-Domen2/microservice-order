package si.fri.rso.domen2.order.services.clients;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import si.fri.rso.domen2.order.lib.DeliverymanResponse;
import si.fri.rso.domen2.order.services.config.ApiProperties;

@ApplicationScoped
public class DeliverymanClient {

    private Logger LOG = Logger.getLogger(DeliverymanClient.class.getSimpleName());

    @Inject
    private ApiProperties ap;

    private Client httpClient;

    @PostConstruct
    private void init() { 
        httpClient = ClientBuilder.newClient();
    }

    public DeliverymanResponse[] getAllDeliveryman() {
        Response response = httpClient.target(ap.getDeliverymanUrl() + "/v1/deliveryman")
            .queryParam("fields", "id,vehicle,hourlyPay,transportPrice,lat,lng")
            .request()
            .get();
        this.LOG.info("GET "+ap.getRadarUrl()+"/v1/deliveryman "+Integer.toString(response.getStatus()));
        if(response.getStatus() == 200) {
            return response.readEntity(DeliverymanResponse[].class);
        }
        return null;
    }
}
