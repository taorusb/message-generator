package com.example.messagegenerator.service;

import com.example.messagegenerator.domain.Agent;
import com.example.messagegenerator.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

@Log4j2
@RequiredArgsConstructor
@Service
public class AgentService {

	private final MessageService messageService;
	private final ExecutorService executorService;
	private final ConcurrentHashMap<String, Long> previousMessageTimestamps = new ConcurrentHashMap<>();

	public void sendMessages(List<Agent> agents, long waitingMills) {
		agents.forEach(agent -> executorService.submit(() -> executeTask(agent, waitingMills)));
	}

	private void executeTask(Agent agent, long waitingMills) {
		final String currentThreadName = Thread.currentThread().getName();
		log.debug("IN executeTask : thread " + currentThreadName + " is started sending messages");
		previousMessageTimestamps.put(currentThreadName, getInitTimestamp());
		agent.getMessages()
				.forEach(message -> {
					final long timestamp = previousMessageTimestamps.get(currentThreadName);
					log.debug("IN executeTask : agent " + agent.getAgentId() + " is sending message" +
							", current timestamp is " + timestamp);
					message.setPreviousMessageTimestamp(timestamp);
					messageService.sendMessage(message)
							.thenAccept((sendResult) -> setPreviousMessageTimestamp(sendResult, currentThreadName));
					try {
						Thread.sleep(waitingMills);
					} catch (InterruptedException e) {
						log.error("IN executeTask : handled an exception " + e.getMessage());
						throw new RuntimeException(e);
					}
				});
	}

	private static Long getInitTimestamp() {
		return ZonedDateTime.now(ZoneId.systemDefault()).minusWeeks(1L).toInstant().toEpochMilli();
	}

	private void setPreviousMessageTimestamp(SendResult<String, Message> sendResult, String threadName) {
		final long timestamp = sendResult.getRecordMetadata().timestamp();
		log.debug("IN setPreviousMessageTimestamp : " +
				Thread.currentThread().getName() + " is setting timestamp" + "to " + timestamp);
		previousMessageTimestamps.put(threadName, timestamp);
	}
}
