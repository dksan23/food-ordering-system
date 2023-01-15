package com.food.ordering.system.order.service.domain;


import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestPublisher;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepo;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateCommandHandler {

    private final OrderDomainService orderDomainService;

    private  final OrderRepository orderRepository;

    private final OrderCreatedPaymentRequestPublisher orderCreatedPaymentRequestPublisher;

    public OrderCreateCommandHandler(OrderDomainService orderDomainService, OrderRepository orderRepository, OrderCreatedPaymentRequestPublisher orderCreatedPaymentRequestPublisher, OrderCreateHelper orderCreateHelper, CustomerRepo customerRepo, RestaurantRepo restaurantRepo, OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.orderCreatedPaymentRequestPublisher = orderCreatedPaymentRequestPublisher;
        this.orderCreateHelper = orderCreateHelper;
        this.customerRepo = customerRepo;
        this.restaurantRepo = restaurantRepo;
        this.orderDataMapper = orderDataMapper;
    }

    private final OrderCreateHelper orderCreateHelper;

    private final CustomerRepo customerRepo;

    private final RestaurantRepo restaurantRepo;

    private final OrderDataMapper orderDataMapper;


    CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand){
        OrderCreatedEvent orderCreatedEvent =  orderCreateHelper.persistOrder(createOrderCommand);
        orderCreatedPaymentRequestPublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder());
    }



}
