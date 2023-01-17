package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.common.domain.entity.AggregateRoot;
import com.food.ordering.system.common.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {

    String firstName;
    String lastName;

    String userName;

    public Customer(CustomerId customerId) {
        super(customerId);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }
}
