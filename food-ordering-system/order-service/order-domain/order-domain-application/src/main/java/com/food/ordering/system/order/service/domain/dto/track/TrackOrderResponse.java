package com.food.ordering.system.order.service.domain.dto.track;

import com.food.ordering.system.common.domain.valueobject.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class TrackOrderResponse {
@NotNull
private final UUID trackingId;
@NotNull
private final OrderStatus status;
}
