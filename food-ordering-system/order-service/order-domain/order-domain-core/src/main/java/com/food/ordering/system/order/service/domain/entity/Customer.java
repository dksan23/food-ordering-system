package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.common.domain.entity.AggregateRoot;
import com.food.ordering.system.common.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {
    public Customer(CustomerId customerId) {
        super(customerId);
    }
}