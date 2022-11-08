package com.example.messagegenerator.service;

import com.example.messagegenerator.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Log4j2
public class MessageService {

	@Value("${app.kafka.topic.name}")
	private String topic;
	private final KafkaTemplate<String, Message> kafkaTemplate;

	@Async
	public CompletableFuture<SendResult<String, Message>> sendMessage(Message message) {
		log.debug("IN sendMessage : Thread " + Thread.currentThread().getName() + " is sending message: " + message);
		return kafkaTemplate.send(topic, message).completable();
	}
}
