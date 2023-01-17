package com.food.ordering.system.common.domain.valueobject;

import java.util.UUID;

public abstract class BaseId<ID>{
private  final ID value;

    protected BaseId(ID value) {
        this.value = value;
    }

    public ID getValue() {
        return value;
    }
}
