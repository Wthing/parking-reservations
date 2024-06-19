package com.wthing.parking.services.interfaces;

import com.wthing.parking.dto.SubscriptionDto;
import com.wthing.parking.models.Subscription;

import java.util.List;

public interface SubscriptionService {
    Subscription save(Subscription subscription);

    Subscription create(SubscriptionDto subscriptionDto);

    List<SubscriptionDto> getAllSubscriptions();

    SubscriptionDto getById(Long subId);

    void deleteById(Long subId);
}
