package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.common.domain.entity.AggregateRoot;
import com.food.ordering.system.common.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueObject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueObject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueObject.TrackingId;

import java.util.List;
import java.util.UUID;


public class Order extends AggregateRoot<OrderId>
{
private final CustomerId customerId;
private final RestaurantId restaurantId;

    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailure_messages() {
        return failure_messages;
    }

    private final StreetAddress streetAddress;

private final Money price;

private final List<OrderItem> orderItemList;

TrackingId trackingId;

private OrderStatus orderStatus;

List<String> failure_messages;

    private Order(Builder builder) {
        super(builder.id);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        streetAddress = builder.streetAddress;
        price = builder.price;
        orderItemList = builder.orderItemList;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failure_messages = builder.failure_messages;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderId id;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress streetAddress;
        private Money price;
        private List<OrderItem> orderItemList;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failure_messages;

        private Builder() {
        }

        public Builder id(OrderId val) {
            id = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder streetAddress(StreetAddress val) {
            streetAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder orderItemList(List<OrderItem> val) {
            orderItemList = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failure_messages(List<String> val) {
            failure_messages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    public void initOrder()
    {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = (new TrackingId((UUID.randomUUID())));
        orderStatus = OrderStatus.PENDING;
        initOrderItems();
    }

    private void initOrderItems()
    {
        long itemId = 0;
        for(OrderItem item: orderItemList) {
            item.initOrderItem(super.getId(), new OrderItemId(itemId++));
        }
    }

    public void validateOrder()
    {
        validateInitOrder();
        validateOrderItems();
        validatePrice();
    }

    private void validateInitOrder()
    {
        if(orderStatus != null || super.getId() != null)
        {
            throw new OrderDomainException("Order is not in correct state for init");
        }
    }

    private void validatePrice()
    {
        if(price == null || !price.isGreaterThanZero())
        {
            throw new OrderDomainException("Order price not valid");
        }
    }

    private void validateOrderItems()
    {
        Money orderItemsTotal = orderItemList.stream().map(orderItem -> {
            validateOrderItem(orderItem);
            return orderItem.getSubtotal();
        }).reduce(Money.ZERO, Money::add);

        if(!price.equals(orderItemsTotal))
        {
            throw new OrderDomainException("order items price not equal to order price");
        }

    }

    private void validateOrderItem(OrderItem item)
    {
        if(!item.isPriceValid())
        {
            throw new OrderDomainException("order item price not valid");
        }
    }

    public void pay()
    {
        if(orderStatus != OrderStatus.PENDING)
        {
            throw new OrderDomainException("order not in correct state for payment");
        }
        orderStatus = OrderStatus.PAID;
    }

    public void approve()
    {
        if(orderStatus != OrderStatus.PAID)
        {
            throw new OrderDomainException(("Order not in valid state for approval"));
        }

        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel()
    {
        if(orderStatus != OrderStatus.PAID)
        {
            throw new OrderDomainException((""));
        }

        orderStatus = OrderStatus.CANCELLING;
    }

    public void cancel()
    {
        if(orderStatus != OrderStatus.PAID)
        {
            throw new OrderDomainException((""));
        }

        orderStatus = OrderStatus.CANCELLED;
    }

}