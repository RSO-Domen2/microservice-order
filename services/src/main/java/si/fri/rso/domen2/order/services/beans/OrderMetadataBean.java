package si.fri.rso.domen2.order.services.beans;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.UriInfo;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import si.fri.rso.domen2.order.lib.OrderMetadata;
import si.fri.rso.domen2.order.models.converters.OrderMetadataConverter;
import si.fri.rso.domen2.order.models.entities.OrderMetadataEntity;

@RequestScoped
public class OrderMetadataBean {

    private Logger LOG = Logger.getLogger(OrderMetadataBean.class.getName());


    @Inject
    private EntityManager em;


    public List<OrderMetadata> getOrderMetadataFilter(UriInfo uriInfo) {
        this.LOG.info("Function call getOrderMetadataFilter");
        QueryParameters qp = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();
        return JPAUtils.queryEntities(em, OrderMetadataEntity.class, qp).stream().map(OrderMetadataConverter::toDto).collect(Collectors.toList());
    }


    public OrderMetadata getOrderMetadata(Integer id) {
        OrderMetadataEntity dme = em.find(OrderMetadataEntity.class, id);
        if(dme == null) {
            this.LOG.warning("Function call getOrderMetadata ID "+id.toString()+" FAILED");
            return null;
        }
        this.LOG.info("Function call getOrderMetadata");
        OrderMetadata dm = OrderMetadataConverter.toDto(dme);
        return dm;
    }


    public OrderMetadata createOrderMetadata(OrderMetadata dm) {
        OrderMetadataEntity dme = OrderMetadataConverter.toEntity(dm);
        try {
            beginTx();
            em.persist(dme);
            commitTx();
        } catch(Exception e) {
            rollbackTx();
        }

        if(dme.getId() == null) {
            this.LOG.warning("Function call createOrderMetadata ID FAILED");
            return null;
        }
        this.LOG.info("Function call createOrderMetadata");
        return OrderMetadataConverter.toDto(dme);
    }


    public OrderMetadata putOrderMetadata(Integer id, OrderMetadata dm) {
        OrderMetadataEntity dme = em.find(OrderMetadataEntity.class, id);
        if(dme == null) {
            return null;
        }
        OrderMetadataEntity updatedDme = OrderMetadataConverter.toEntity(dm);
        try {
            beginTx();
            updatedDme.setId(dme.getId());
            updatedDme = em.merge(updatedDme);
            commitTx();
        } catch(Exception e) {
            this.LOG.warning("Function call putOrderMetadata FAILED");
            rollbackTx();
        }
        this.LOG.info("Function call putOrderMetadata");
        return OrderMetadataConverter.toDto(updatedDme);
    }


    public boolean deleteOrderMetadata(Integer id) {
        OrderMetadataEntity dme = em.find(OrderMetadataEntity.class, id);
        if(dme != null) {
            try {
                beginTx();
                em.remove(dme);
                commitTx();
            } catch(Exception e) {
                this.LOG.warning("Function call deleteOrderMetadata FAILED");
                rollbackTx();
                return false;
            }
            this.LOG.info("Function call deleteOrderMetadata");
            return true;
        } else {
            return false;
        }
    }


    private void beginTx() {
        if(!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if(em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if(em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
