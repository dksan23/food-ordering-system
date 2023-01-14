package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService{
    @Override
    public OrderCreatedEvent validateAndInitOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInfo(order, restaurant);
        order.validateOrder();
        order.initOrder();
        log.info("Order init success");
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    private void setOrderProductInfo(Order order, Restaurant restaurant) {
        order.getOrderItemList().forEach(orderItem -> restaurant.getProducts().forEach(rp ->{
            Product currentP =orderItem.getProduct();
            if(currentP.equals(rp))
            {
                currentP.updateWithConfirmedNameAndPrice(rp.getName(), rp.getPrice());
            }
        }));

    }

    private void validateRestaurant(Restaurant restaurant) {
    if(!restaurant.isActive())
    {
        throw new OrderDomainException(("Restaurant not active"));
    }
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        return null;
    }

    @Override
    public void approveOrder(Order order) {

        order.approve();
        log.info("order is approved");

    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order) {
        order.initCancel();
        log.info("order cancelling status set");

        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public void cancelOrder(Order order) {
        order.cancel();
        log.info("order cancelled");
    }
}
