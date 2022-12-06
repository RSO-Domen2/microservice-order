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
        QueryParameters qp = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();
        return JPAUtils.queryEntities(em, OrderMetadataEntity.class, qp).stream().map(OrderMetadataConverter::toDto).collect(Collectors.toList());
    }


    public OrderMetadata getOrderMetadata(Integer id) {
        OrderMetadataEntity dme = em.find(OrderMetadataEntity.class, id);
        if(dme == null) {
            // throw new NotFoundException();
            return null;
        }
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
            //throw new RuntimeException("Entity was not persisted");
            return null;
        }
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
            rollbackTx();
        }
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
                rollbackTx();
            }
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
