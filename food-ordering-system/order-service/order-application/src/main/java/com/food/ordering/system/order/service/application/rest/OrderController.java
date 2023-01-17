package com.food.ordering.system.order.service.application.rest;


import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
public class OrderController {
private final OrderApp orderApp;

    public OrderController(OrderApp orderApp) {
        this.orderApp = orderApp;
    }


    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand createOrderCommand)
    {
            CreateOrderResponse createOrderResponse = orderApp.createOrder(createOrderCommand);

            return ResponseEntity.ok(createOrderResponse);
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResponse> trackOrder(@PathVariable UUID trackingId)
    {
        TrackOrderQuery trackOrderQuery = TrackOrderQuery.builder().trackingId(trackingId).build();

        return ResponseEntity.ok(orderApp.trackOrder(trackOrderQuery));
    }
}
