package com.food.ordering.system.common.domain.valueobject;

import java.util.UUID;

public class RestaurantId<UUID> extends BaseId<UUID>{
    public RestaurantId(UUID value) {
        super(value);
    }
}
