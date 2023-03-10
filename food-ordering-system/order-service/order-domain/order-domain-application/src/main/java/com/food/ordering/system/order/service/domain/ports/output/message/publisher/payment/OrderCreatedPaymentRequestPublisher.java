package com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestPublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
