package com.food.ordering.system.order.service.domain.dto.message;

import com.food.ordering.system.common.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class PaymentRsp {
private String id;
private String sagaId;
private String orderId;
private String paymentId;
private String customerId;
private BigDecimal price;
private Instant createdAt;

private PaymentStatus paymentStatus;
}
