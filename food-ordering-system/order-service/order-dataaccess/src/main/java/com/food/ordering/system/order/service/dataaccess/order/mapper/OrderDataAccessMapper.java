package com.food.ordering.system.order.service.dataaccess.order.mapper;


import com.food.ordering.system.common.domain.valueobject.*;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderAddressEntity;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderItemEntity;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.valueObject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueObject.StreetAddress;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.food.ordering.system.order.service.domain.valueObject.TrackingId;
import org.springframework.stereotype.Component;

@Component
public class OrderDataAccessMapper {
public OrderEntity orderToOrderEntity(Order order)
{
    OrderEntity orderEntity = OrderEntity.builder()
            .id(order.getId().getValue())
            .customerId((UUID) order.getCustomerId().getValue())
            .trackingId(order.getTrackingId().getValue())
            .restaurantId((UUID) order.getRestaurantId().getValue())
            .address(streetAddressToOrderAddress(order.getStreetAddress()))
            .price(order.getPrice().getAmount())
            .items(orderItemsToOrderItemEntities(order.getOrderItemList()))
            .orderStatus(order.getOrderStatus()).build();

    orderEntity.getAddress().setOrder(orderEntity);
    orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrderEntity(orderEntity));

    return orderEntity;

}
    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .id(new OrderId(orderEntity.getId()))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .streetAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
                .price(new Money(orderEntity.getPrice()))
                .orderItemList(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .build();
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddress(address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity());
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream()
                .map(orderItem -> OrderItemEntity.builder()
                        .id(orderItem.getId().getValue())
                        .productId((UUID) orderItem.getProduct().getId().getValue())
                        .price(orderItem.getPrice().getAmount())
                        .quantity(orderItem.getQuantity())
                        .subTotal(orderItem.getSubtotal().getAmount())
                        .build())
                .collect(Collectors.toList());
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return items.stream()
                .map(orderItemEntity -> OrderItem.builder()
                        .id(new OrderItemId(orderItemEntity.getId()))
                        .product(new Product(new ProductId(orderItemEntity.getProductId())))
                        .price(new Money(orderItemEntity.getPrice()))
                        .quantity(orderItemEntity.getQuantity())
                        .subtotal(new Money(orderItemEntity.getSubTotal()))
                        .build())
                .collect(Collectors.toList());
    }

    private OrderAddressEntity streetAddressToOrderAddress(StreetAddress streetAddress) {

        return OrderAddressEntity.builder()
                .id(streetAddress.getId())
                .street(streetAddress.getStreet())
                .city(streetAddress.getCity())
                .postalCode(streetAddress.getPostalCode()).build();

    }
}
