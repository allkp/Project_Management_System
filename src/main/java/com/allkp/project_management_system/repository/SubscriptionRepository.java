package com.allkp.project_management_system.repository;

import com.allkp.project_management_system.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findByUserId(long userId);

}
