package com.food.ordering.system.order.service.domain.mapper;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.OrderId;
import com.food.ordering.system.common.domain.valueobject.ProductId;
import com.food.ordering.system.common.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.dto.create.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.valueObject.StreetAddress;
import com.food.ordering.system.common.domain.valueobject.Money;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand)
    {
        return Restaurant.builder()
                .id(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(orderItem ->
                    new Product((new ProductId<>(orderItem.getProductId()))))
                    .collect(Collectors.toList())
                ).build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand)
    {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .streetAddress(orderAddrToStreetAddr(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .orderItemList(orderItemsToOrderItemsEntities(createOrderCommand.getItems()))
                .build();
    }

    private List<com.food.ordering.system.order.service.domain.entity.OrderItem> orderItemsToOrderItemsEntities(List<OrderItem> items) {
        return items.stream().map(orderItem ->
            com.food.ordering.system.order.service.domain.entity.OrderItem.builder()
                    .product(new Product(new ProductId(orderItem.getProductId())))
                    .price(new Money(orderItem.getPrice()))
                    .quantity(orderItem.getQuantity())
                    .subtotal(new Money(orderItem.getSubTotal()))
                    .build()).collect(Collectors.toList());


    }

    private StreetAddress orderAddrToStreetAddr(OrderAddress address) {
        return new StreetAddress(UUID.randomUUID(), address.getStreet(), address.getPostalCode(), address.getCity());
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order orderResult) {


        return CreateOrderResponse.builder()
                .orderStatus(orderResult.getOrderStatus())
                .orderTrackingId(orderResult.getTrackingId().getValue())
                .build();
    }
}
