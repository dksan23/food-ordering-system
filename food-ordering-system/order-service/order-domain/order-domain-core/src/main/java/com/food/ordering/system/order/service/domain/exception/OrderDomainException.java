package com.food.ordering.system.order.service.domain.exception;

import com.food.ordering.system.common.domain.exception.DomianException;

public class OrderDomainException extends DomianException {

    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
