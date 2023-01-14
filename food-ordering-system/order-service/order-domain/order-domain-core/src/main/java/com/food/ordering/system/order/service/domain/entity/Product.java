package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.common.domain.entity.BaseEntity;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super(productId);
        this.name = name;
        this.price = price;
    }

    public Product(ProductId productId)
    {
        super(productId);
    }

    public Money getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void updateWithConfirmedNameAndPrice(String name, Money price) {

        this.name = name;
        this.price = price;
    }
}
