package com.example.demo.kafka;

import com.example.demo.entity.StatisticsBooking;
import com.example.demo.entity.StatisticsUser;
import com.example.demo.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaListener {
    private final StatisticsService statisticsService;

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "${app.kafka.kafkaUser}",
            groupId = "${app.kafka.kafkaUserGroupId}",
            containerFactory = "kafkaUserListenerContainerFactory"
    )
    public void listenKafkaUser(@Payload StatisticsUser user) {
        log.info("Received KafkaUser event: {}", user);
        statisticsService.post(user);
    }

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "${app.kafka.kafkaBooking}",
            groupId = "${app.kafka.kafkaUserGroupId}",
            containerFactory = "kafkaBookingListenerContainerFactory"
    )
    public void listenKafkaBooking(@Payload StatisticsBooking booking) {
        log.info("Received KafkaBooking event: {}", booking);
        statisticsService.post(booking);
    }
}
