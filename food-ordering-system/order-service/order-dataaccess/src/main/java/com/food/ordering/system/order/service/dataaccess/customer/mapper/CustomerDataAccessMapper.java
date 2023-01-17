package com.food.ordering.system.order.service.dataaccess.customer.mapper;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.order.service.dataaccess.customer.entity.CustomerEntity;
import com.food.ordering.system.order.service.domain.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerDataAccessMapper {

    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()));
    }

    public CustomerEntity customerToCustomerEntity(Customer customer) {
        return CustomerEntity.builder()
                .id((UUID) customer.getId().getValue())
                .username(customer.getUserName())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }

}
