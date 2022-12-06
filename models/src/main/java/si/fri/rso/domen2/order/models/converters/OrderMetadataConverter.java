package si.fri.rso.domen2.order.models.converters;

import si.fri.rso.domen2.order.lib.OrderMetadata;
import si.fri.rso.domen2.order.models.entities.OrderMetadataEntity;

public class OrderMetadataConverter {
    
    public static OrderMetadata toDto(OrderMetadataEntity entity) {
        OrderMetadata dto = new OrderMetadata();
        dto.setId(entity.getId());
        dto.setClientId(entity.getClientId());
        dto.setRestaurantId(entity.getRestaurantId());
        dto.setMenuId(entity.getMenuId());
        dto.setDeliverymanId(entity.getDeliverymanId());
        dto.setCreated(entity.getCreated());
        return dto;
    }

    public static OrderMetadataEntity toEntity(OrderMetadata dto) {
        OrderMetadataEntity entity = new OrderMetadataEntity();
        entity.setId(dto.getId());
        entity.setClientId(dto.getClientId());
        entity.setRestaurantId(dto.getRestaurantId());
        entity.setMenuId(dto.getMenuId());
        entity.setDeliverymanId(dto.getDeliverymanId());
        entity.setCreated(dto.getCreated());
        return entity;
    }
}
