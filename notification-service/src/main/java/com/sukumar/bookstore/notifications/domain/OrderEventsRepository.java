package com.sukumar.bookstore.notifications.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEventsRepository extends JpaRepository<OrderEventsEntity, Long>{

	boolean existsByEventId(String eventId);
}
