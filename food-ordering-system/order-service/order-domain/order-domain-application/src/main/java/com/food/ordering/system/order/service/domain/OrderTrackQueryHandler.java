package com.food.ordering.system.order.service.domain;


import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.valueObject.TrackingId;
import com.food.ordering.system.order.service.domain.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Slf4j
@Component
public class OrderTrackQueryHandler {

    private OrderDataMapper orderDataMapper;

    private OrderRepository orderRepository;

    public OrderTrackQueryHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrderQueryHandler(TrackOrderQuery trackOrderQuery)
    {
        Optional<Order> orderResult = orderRepository.findByTrackingId(new TrackingId((trackOrderQuery.getTrackingId())));

        if(orderResult.isEmpty())
        {
            // throw not found exception
        }

        return orderDataMapper.orderToTrackOrderResponse(orderResult.get());

    }

}
