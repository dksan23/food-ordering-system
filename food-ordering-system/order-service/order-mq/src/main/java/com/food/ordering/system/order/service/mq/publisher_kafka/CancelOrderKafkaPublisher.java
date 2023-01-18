package com.food.ordering.system.order.service.mq.publisher_kafka;

import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestPublisher;
import com.food.ordering.system.order.service.mq.mapper.OrderMessagingDataMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Component
public class CancelOrderKafkaPublisher implements OrderCancelledPaymentRequestPublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapperl;

    private final OrderServiceConfigData orderServiceConfigData;

    KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;

    public CancelOrderKafkaPublisher(OrderMessagingDataMapper orderMessagingDataMapperl, OrderServiceConfigData orderServiceConfigData, KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer) {
        this.orderMessagingDataMapperl = orderMessagingDataMapperl;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
    }


    @Override
    public void publish(OrderCancelledEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();

        PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapperl.orderCancelledEventToPaymentRequestAvroModel(domainEvent);

        kafkaProducer.send(orderServiceConfigData.getPaymentResponseTopicName(), orderId, paymentRequestAvroModel
        getKafkaCallBack(orderServiceConfigData.getPaymentResponseTopicName(),
                            paymentRequestAvroModel));
    }

    private ListenableFutureCallback<SendResult<String, PaymentRequestAvroModel>>
    getKafkaCallBack(String paymentResponseTopicName, PaymentRequestAvroModel paymentRequestAvroModel) {

        return new ListenableFutureCallback<SendResult<String, PaymentRequestAvroModel>>() {
            @Override
            public void onFailure(Throwable ex) {
                // log.error
            }

            @Override
            public void onSuccess(SendResult<String, PaymentRequestAvroModel> result) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                //log success metadata
            }
        }

    }
}
