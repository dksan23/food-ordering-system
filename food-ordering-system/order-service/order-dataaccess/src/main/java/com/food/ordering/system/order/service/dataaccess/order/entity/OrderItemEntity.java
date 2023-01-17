package com.food.ordering.system.order.service.dataaccess.order.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
@IdClass(OrderItemEntityId.class)
@Entity
public class OrderItemEntity {

    @Id
    Long id;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    OrderEntity orderEntity;

    UUID productId;
    BigDecimal price;
    Integer quantity;
    BigDecimal subTotal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemEntity that = (OrderItemEntity) o;
        return id.equals(that.id) && orderEntity.equals(that.orderEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderEntity);
    }
}
