package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
class OrderAppServiceImpl implements OrderApp {


    private final OrderCreateCommandHandler orderCreateCommandHandler;

    private final OrderTrackQueryHandler orderTrackQueryHandler;

    public OrderAppServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler, OrderTrackQueryHandler orderTrackQueryHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackQueryHandler = orderTrackQueryHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand command) {
        return orderCreateCommandHandler.createOrder((command));
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery query) {
        return orderTrackQueryHandler.trackOrderQueryHandler((query));
    }
}
