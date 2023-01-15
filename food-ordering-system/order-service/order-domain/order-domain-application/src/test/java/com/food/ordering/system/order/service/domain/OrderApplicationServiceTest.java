package com.food.ordering.system.order.service.domain;


import com.food.ordering.system.common.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.dto.create.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApp;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepo;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {
    @Autowired
    private OrderApp orderAppService;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private RestaurantRepo restaurantRepo;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;

    private CreateOrderCommand createOrderCommandWrongProductPrice;

    private final UUID customerId = UUID.randomUUID();
    private final UUID restaurantId = UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454");

    private final UUID productId = UUID.randomUUID();
    private final UUID orderId = UUID.randomUUID();

    private final BigDecimal price = new BigDecimal(200);


    @BeforeAll
    public void init() {
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(customerId)
                .restaurantId(restaurantId)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("10000AB")
                        .city("Paris")
                        .build())
                .price(price)
                .items(List.of(OrderItem.builder()
                        .productId(productId)
                        .quantity((1))
                        .price(new BigDecimal("50.00"))
                        .subTotal(new BigDecimal("50.00"))
                        .build(),
                        OrderItem.builder()
                                .productId(productId)
                                .quantity(3)
                                .price(new BigDecimal(("50.00")))
                                .subTotal(new BigDecimal("150.00"))
                                .build())).build();
        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(customerId)
                .restaurantId(restaurantId)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("10000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .productId(productId)
                                .quantity((1))
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(productId)
                                .quantity(3)
                                .price(new BigDecimal(("50.00")))
                                .subTotal(new BigDecimal("150.00"))
                                .build())).build();

        createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(customerId)
                .restaurantId(restaurantId)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("10000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .productId(productId)
                                .quantity((1))
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(productId)
                                .quantity(3)
                                .price(new BigDecimal(("50.00")))
                                .subTotal(new BigDecimal("150.00"))
                                .build())).build();

        Customer customer = new Customer(new CustomerId(customerId));

        Restaurant restaurant = Restaurant.builder()
                .id(new RestaurantId(restaurantId))
                .products(List.of(new Product(new ProductId(productId), "product-1",
                        new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(productId), "product-2",
                                new Money(new BigDecimal("50.00")))))
                .active(true)
                .build();

        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);

        when(customerRepo.findCustomer(customerId)).thenReturn(Optional.of(customer));
        when(restaurantRepo.findRestaurantInfo(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurant));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        }

        @Test
        public void testCreateOrder()
        {
            CreateOrderResponse createOrderResponse = orderAppService.createOrder(createOrderCommand);

            assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus() );

        }

        @Test
        public void testCreateWithWrongTotalPrice()
        {
            OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                    () -> orderAppService.createOrder(createOrderCommandWrongPrice));
            assertNotNull(orderDomainException);
        }
    }
