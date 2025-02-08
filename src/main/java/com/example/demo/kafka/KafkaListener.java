package com.example.demo.kafka;

import com.example.demo.entity.KafkaBooking;
import com.example.demo.entity.KafkaUser;
import com.example.demo.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaListener {
    private final KafkaService kafkaService;

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "${app.kafka.kafkaUser}",
            groupId = "${app.kafka.kafkaUserGroupId}",
            containerFactory = "kafkaUserListenerContainerFactory"
    )
    public void listenKafkaUser(@Payload KafkaUser user) {
        log.info("Received KafkaUser event: {}", user);
        kafkaService.post(user);
    }

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "${app.kafka.kafkaBooking}",
            groupId = "${app.kafka.kafkaUserGroupId}",
            containerFactory = "kafkaBookingListenerContainerFactory"
    )
    public void listenKafkaBooking(@Payload KafkaBooking booking) {
        log.info("Received KafkaBooking event: {}", booking);
        kafkaService.post(booking);
    }
}
