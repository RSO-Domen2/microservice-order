package si.fri.rso.domen2.order.services.clients;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import si.fri.rso.domen2.order.lib.ClientResponse;
import si.fri.rso.domen2.order.services.config.ApiProperties;

@ApplicationScoped
public class ClientClient {
    
    private Logger LOG = Logger.getLogger(ClientClient.class.getSimpleName());

    @Inject
    private ApiProperties ap;

    private Client httpClient;

    @PostConstruct
    private void init() { 
        httpClient = ClientBuilder.newClient();
    }

    public ClientResponse getClient(int id) {
        Response response = httpClient.target(ap.getClientUrl() + "/v1/client/" + Integer.toString(id))
            .request()
            .get();
        this.LOG.info("GET "+ap.getClientUrl()+"/v1/client/"+Integer.toString(id)+" "+Integer.toString(response.getStatus()));
        if(response.getStatus() == 200) {
            return response.readEntity(ClientResponse.class);
        }
        return null;
    }
}
