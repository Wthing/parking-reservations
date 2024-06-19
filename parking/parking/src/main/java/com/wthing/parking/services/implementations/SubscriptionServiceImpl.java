package com.wthing.parking.services.implementations;

import com.wthing.parking.dto.SubscriptionDto;
import com.wthing.parking.mappers.Mappers;
import com.wthing.parking.models.Subscription;
import com.wthing.parking.models.User;
import com.wthing.parking.repositories.SubscriptionRepo;
import com.wthing.parking.repositories.UserRepo;
import com.wthing.parking.services.interfaces.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.wthing.parking.constants.Messages.SUBSCRIPTION_NOT_FOUND;
import static com.wthing.parking.constants.Messages.USER_NOT_FOUND;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepo subscriptionRepo;
    private final UserRepo userRepo;

    public SubscriptionServiceImpl(SubscriptionRepo subscriptionRepo, UserRepo userRepo) {
        this.subscriptionRepo = subscriptionRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Subscription save(Subscription subscription) {
        return subscriptionRepo.save(subscription);
    }

    @Override
    public Subscription create(SubscriptionDto subscriptionDto) {
        Subscription subscription = new Subscription();

        User user = userRepo.findById(subscriptionDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        subscription.setUser(user);
        subscription.setStartDate(subscriptionDto.getStartDate());
        subscription.setEndDate(subscriptionDto.getEndDate());
        subscription.setType(subscriptionDto.getType());

        return subscriptionRepo.save(subscription);
    }

    @Override
    public List<SubscriptionDto> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepo.findAll();

        return subscriptions.stream()
                .map(Mappers::mapToSubscriptionDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionDto getById(Long subId) {
        Subscription subscription = subscriptionRepo.findById(subId)
                .orElseThrow(() -> new IllegalArgumentException(SUBSCRIPTION_NOT_FOUND));

        return Mappers.mapToSubscriptionDto(subscription);
    }

    @Override
    public void deleteById(Long subId) {
        subscriptionRepo.deleteById(subId);
    }
}
