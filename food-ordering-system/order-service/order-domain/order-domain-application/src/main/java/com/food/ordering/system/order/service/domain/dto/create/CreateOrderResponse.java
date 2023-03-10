package com.food.ordering.system.order.service.domain.dto.create;

import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import com.food.ordering.system.order.service.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderResponse {
@NotNull
    private final UUID orderTrackingId;
@NotNull
    private final OrderStatus orderStatus;
}
