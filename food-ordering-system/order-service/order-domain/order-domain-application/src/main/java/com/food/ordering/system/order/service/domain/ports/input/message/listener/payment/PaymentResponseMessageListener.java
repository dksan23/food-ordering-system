package com.food.ordering.system.order.service.domain.ports.input.message.listener.payment;

import com.food.ordering.system.order.service.domain.dto.message.PaymentRsp;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentRsp paymentRsp);

    void paymentCancelled(PaymentRsp paymentRsp);
}
