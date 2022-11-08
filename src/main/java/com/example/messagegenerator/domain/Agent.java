package com.example.messagegenerator.domain;

import com.example.messagegenerator.service.MessageService;
import com.example.messagegenerator.util.MessageGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.support.SendResult;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicLong;


@Log4j2
public class Agent {

	private final String agentId;
	private final DeviceType deviceType;
	private final Manufacturer manufacturer;
	private final OSType osType;
	private final MessageService messageService;
	private final AtomicLong previousMessageTimestamp;
	private final static int ONE_MINUTE = 60_000;

	public Agent(String agentId, DeviceType deviceType, Manufacturer manufacturer, OSType osType,
				 MessageService messageService) {
		this.agentId = agentId;
		this.deviceType = deviceType;
		this.manufacturer = manufacturer;
		this.osType = osType;
		this.messageService = messageService;
		this.previousMessageTimestamp = new AtomicLong(getInitTimestamp());
	}

	private Long getInitTimestamp() {
		return ZonedDateTime.now(ZoneId.systemDefault()).minusWeeks(1L).toInstant().toEpochMilli();
	}

	public void sendMessages(int countPerMinute) {
		final long waitingMills = (countPerMinute > 1) ? ONE_MINUTE / countPerMinute : 1L;
		MessageGenerator.generate(agentId, countPerMinute)
				.forEach(message -> {
					log.debug("IN sendMessages : Agent id " + agentId);
					log.debug("IN sendMessages : Current timestamp is " + previousMessageTimestamp.get());
					message.setPreviousMessageTimestamp(previousMessageTimestamp.get());
					try {
						messageService.sendMessage(message)
								.thenAccept(this::setPreviousMessageTimestamp);
						Thread.sleep(waitingMills);
					} catch (InterruptedException e) {
						log.error("IN sendMessages : Failure the reason is " + e.getMessage());
						throw new RuntimeException(e);
					}
					log.debug("IN sendMessages : Next timestamp is " + previousMessageTimestamp.get());
				});
	}

	private void setPreviousMessageTimestamp(SendResult<String, Message> sendResult) {
		this.previousMessageTimestamp.set(sendResult.getRecordMetadata().timestamp());
	}
}
