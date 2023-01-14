package com.food.ordering.system.common.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private final BigDecimal amount;

    public static final Money ZERO = new Money(BigDecimal.ZERO);
    public Money(BigDecimal a)
    {
        this.amount = a;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isGreaterThanZero()
    {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money b)
    {
        return amount != null && amount.compareTo(b.getAmount()) > 0;
    }

     public Money add(Money a)
     {
         return new Money(setScale(amount.add(a.getAmount())));
     }

     public Money subtract(Money b)
     {
         return new Money((setScale(amount.subtract(b.getAmount()))));
     }

     public Money multiply(int factor)
     {
         return new Money(setScale(amount.multiply(new BigDecimal(factor))));
     }
     private BigDecimal setScale(BigDecimal val)
     {
         return val.setScale(2, RoundingMode.HALF_EVEN);
     }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
