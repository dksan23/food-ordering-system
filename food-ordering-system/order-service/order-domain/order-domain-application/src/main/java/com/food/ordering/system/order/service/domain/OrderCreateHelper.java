package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
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
public class OrderCreateHelper {
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;

    private final CustomerRepo customerRepo;

    private final RestaurantRepo restaurantRepo;

    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderDomainService orderDomainService, OrderRepository orderRepository, CustomerRepo customerRepo, RestaurantRepo restaurantRepo, OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepo = customerRepo;
        this.restaurantRepo = restaurantRepo;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand order)
    {
        checkCustomer(order.getCustomerId());
        Restaurant rest = checkRestaurant(order);
        Order order1 = orderDataMapper.createOrderCommandToOrder(order);
        OrderCreatedEvent orderCreatedEvent =orderDomainService.validateAndInitOrder(order1, rest);
        Order orderResult = saveOrder(order1 );
        return orderCreatedEvent;
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
