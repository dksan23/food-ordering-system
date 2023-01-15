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

    private final CustomerRepo customerRepo;

    private final RestaurantRepo restaurantRepo;

    private final OrderDataMapper orderDataMapper;

    public OrderCreateCommandHandler(OrderDomainService orderDomainService, OrderRepository orderRepository, CustomerRepo customerRepo, RestaurantRepo restaurantRepo, OrderDataMapper orderDataMapper, ApplicationDomainEventPublisher applicationDomainEventPublisher) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepo = customerRepo;
        this.restaurantRepo = restaurantRepo;
        this.orderDataMapper = orderDataMapper;
        this.applicationDomainEventPublisher = applicationDomainEventPublisher;
    }

    private final ApplicationDomainEventPublisher applicationDomainEventPublisher;
    @Transactional
    CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand){
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant rest = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent =orderDomainService.validateAndInitOrder(order, rest);
        Order orderResult = saveOrder(order);
        return orderDataMapper.orderToCreateOrderResponse(orderResult);
    }

    private Order saveOrder(Order order)
    {
        Order order1 = orderRepository.save(order);
        if(order1 == null)
        {
            throw new OrderDomainException(("order repo coundt save order"));
        }

        return order1;
    }

    private void checkCustomer(UUID customerId)
    {
        Optional<Customer> cust = customerRepo.findCustomer(customerId);

        if(cust.isEmpty())
        {
            throw new OrderDomainException("no customer found");
        }

    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand)
    {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findRestaurantInfo(restaurant);

        if(optionalRestaurant.isEmpty())
        {
            throw new OrderDomainException("restaurant not found");
        }

        return optionalRestaurant.get();
    }

}
