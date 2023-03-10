package com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantApproval;

import com.food.ordering.system.common.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidPaymentRequestPublisher extends DomainEventPublisher<OrderPaidEvent> {
}
