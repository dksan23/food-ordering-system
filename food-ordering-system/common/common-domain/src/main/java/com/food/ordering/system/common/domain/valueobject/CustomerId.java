package com.food.ordering.system.common.domain.valueobject;

import java.util.UUID;

public class CustomerId<UUID> extends BaseId<UUID> {

    public CustomerId(UUID value) {
        super(value);
    }
}
