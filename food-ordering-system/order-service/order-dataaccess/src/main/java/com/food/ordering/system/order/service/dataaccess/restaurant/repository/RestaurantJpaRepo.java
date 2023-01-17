package com.food.ordering.system.order.service.dataaccess.restaurant.repository;


import com.food.ordering.system.order.service.dataaccess.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RestaurantJpaRepo extends JpaRepository<RestaurantEntity, UUID> {
}
