package com.allkp.project_management_system.service;

import com.allkp.project_management_system.model.PlanType;
import com.allkp.project_management_system.model.Subscription;
import com.allkp.project_management_system.model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);

    Subscription getUsersSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType) throws Exception;

    boolean isValidSubscription(Subscription subscription);

}
