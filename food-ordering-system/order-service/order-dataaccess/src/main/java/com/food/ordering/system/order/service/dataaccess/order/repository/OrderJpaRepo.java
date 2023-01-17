package com.food.ordering.system.order.service.dataaccess.order.repository;

import com.food.ordering.system.common.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity;
import com.food.ordering.system.order.service.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface OrderJpaRepo extends JpaRepository<OrderEntity, UUID> {


    Optional<OrderEntity> findByTrackingId(UUID trackingId);

}
