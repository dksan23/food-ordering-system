package com.food.ordering.system.order.service.mq.publisher_kafka;

import com.food.ordering.system.kafka.consumer.KafkaConsumer;
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestPublisher;
import com.food.ordering.system.order.service.mq.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class CreateOrderMessagePublisher implements OrderCreatedPaymentRequestPublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;

    private final OrderServiceConfigData orderServiceConfigData;

    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;

    public CreateOrderMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper,
                                       OrderServiceConfigData orderServiceConfigData,
                                       KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer) {

        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void publish(OrderCreatedEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();

        PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper.orderCreatedEventToPaymentRequestAvroModel(domainEvent);

        kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),orderId,
                paymentRequestAvroModel, getKafkaCallBack(orderServiceConfigData.getPaymentResponseTopicName(),paymentRequestAvroModel);
    }

    private ListenableFutureCallback<SendResult<String, PaymentRequestAvroModel>> getKafkaCallBack(String paymentResponseTopicName, PaymentRequestAvroModel paymentRequestAvroModel) {

        return new ListenableFutureCallback<SendResult<String, PaymentRequestAvroModel>>() {
            @Override
            public void onFailure(Throwable ex) {
                //log error
            }

            @Override
            public void onSuccess(SendResult<String, PaymentRequestAvroModel> result) {
                RecordMetadata metadata = result.getRecordMetadata();

            }
        };

    }


}
