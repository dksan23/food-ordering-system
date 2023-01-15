package com.food.ordering.system.order.service.domain;


import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestPublisher;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestPublisher;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantApproval.OrderPaidPaymentRequestPublisher;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepo;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepo;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class OrderTestConfiguration {

    @Bean
    public OrderCreatedPaymentRequestPublisher orderCreatedPaymentRequestPublisher()
    {
        return Mockito.mock(OrderCreatedPaymentRequestPublisher.class);
    }

    @Bean

    public OrderCancelledPaymentRequestPublisher orderCancelledPaymentRequestPublisher()
    {
        return Mockito.mock(OrderCancelledPaymentRequestPublisher.class);
    }

    @Bean
    public OrderPaidPaymentRequestPublisher orderPaidPaymentRequestPublisher()
    {
        return Mockito.mock(OrderPaidPaymentRequestPublisher.class);
    }

    @Bean
    public OrderRepository orderRepository()
    {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepo customerRepo()
    {
        return Mockito.mock(CustomerRepo.class);
    }

    @Bean
    public RestaurantRepo restaurantRepo()
    {
        return Mockito.mock(RestaurantRepo.class);
    }

    @Bean
    public OrderDomainService orderDomainService()
    {
        return new OrderDomainServiceImpl();
    }
}
