package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class OrderCreatedEventAppListener {

    private final OrderCreatedPaymentRequestPublisher orderCreatedPaymentRequestPublisher;

    public OrderCreatedEventAppListener(OrderCreatedPaymentRequestPublisher orderCreatedPaymentRequestPublisher) {
        this.orderCreatedPaymentRequestPublisher = orderCreatedPaymentRequestPublisher;
    }


    @TransactionalEventListener
    void process(OrderCreatedEvent orderCreatedEvent)
    {
        orderCreatedPaymentRequestPublisher.publish(orderCreatedEvent);
    }

}
