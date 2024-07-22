package com.allkp.project_management_system.serviceImpl;

import com.allkp.project_management_system.model.PlanType;
import com.allkp.project_management_system.model.Subscription;
import com.allkp.project_management_system.model.User;
import com.allkp.project_management_system.repository.SubscriptionRepository;
import com.allkp.project_management_system.service.SubscriptionService;
import com.allkp.project_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserService userService;

    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();

        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUsersSubscription(Long userId) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if(!isValidSubscription(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
            subscription.setSubscriptionStartDate(LocalDate.now());
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        subscription.setPlanType(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());
        if(planType.equals(PlanType.ANNUALLY)){
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }else{
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }

        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValidSubscription(Subscription subscription) {
        if(subscription.getPlanType().equals(PlanType.FREE)){
            return true;
        }

        LocalDate subscriptionEndDate = subscription.getSubscriptionEndDate();
        LocalDate currentDate = LocalDate.now();

        return subscriptionEndDate.isAfter(currentDate) || subscriptionEndDate.isEqual(currentDate);
    }
}
