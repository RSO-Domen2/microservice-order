package si.fri.rso.domen2.order.services.clients;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import si.fri.rso.domen2.order.lib.MenuResponse;
import si.fri.rso.domen2.order.lib.RestaurantResponse;
import si.fri.rso.domen2.order.services.config.ApiProperties;

@ApplicationScoped
public class RestaurantClient {

    private Logger LOG = Logger.getLogger(RestaurantClient.class.getSimpleName());

    @Inject
    private ApiProperties ap;

    private Client httpClient;

    @PostConstruct
    private void init() { 
        httpClient = ClientBuilder.newClient();
    }

    public RestaurantResponse getRestaurant(int id) {
        Response response = httpClient.target(ap.getRestaurantUrl() + "/v1/restaurant/" + Integer.toString(id))
            .request()
            .get();
        this.LOG.info("GET "+ap.getRestaurantUrl()+"/v1/restaurant/"+Integer.toString(id)+" "+Integer.toString(response.getStatus()));
        if(response.getStatus() == 200) {
            return response.readEntity(RestaurantResponse.class);
        }
        return null;
    }

    public MenuResponse getMenu(int id) {
        Response response = httpClient.target(ap.getRestaurantUrl() + "/v1/menu/" + Integer.toString(id))
            .request()
            .get();
        this.LOG.info("GET "+ap.getRestaurantUrl()+"/v1/menu/"+Integer.toString(id)+" "+Integer.toString(response.getStatus()));
        if(response.getStatus() == 200) {
            return response.readEntity(MenuResponse.class);
        }
        return null;
    }
}
