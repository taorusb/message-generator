package com.example.messagegenerator.service;

import com.example.messagegenerator.domain.Agent;
import com.example.messagegenerator.domain.Message;
import com.example.messagegenerator.util.AgentGenerator;
import com.example.messagegenerator.util.MessageGenerator;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.kafka.support.SendResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentServiceTest {

	@Mock
	private MessageService messageService;
	@Mock
	private SendResult<String, Message> sendResult;
	private final RecordMetadata recordMetadata = new RecordMetadata(
			new TopicPartition("123", 1),
			1L,
			1,
			123L,
			1,
			1);
	private AgentService agentService;

	@BeforeEach
	void setup() {
		ExecutorService executorService = Mockito.mock(ExecutorService.class);
		agentService = new AgentService(messageService, executorService);

		doAnswer((Answer<Object>) invocation -> {
			((Runnable) invocation.getArguments()[0]).run();
			return null;
		}).when(executorService).submit(any(Runnable.class));

		when(messageService.sendMessage(any())).thenReturn(CompletableFuture.completedFuture(sendResult));
		when(sendResult.getRecordMetadata()).thenReturn(recordMetadata);
	}

	@Test
	void testSendMessages() {
		List<Agent> agents = AgentGenerator.generate(1).stream()
				.peek(agent -> agent.setMessages(MessageGenerator.generate(agent.getAgentId(), 1)))
				.collect(Collectors.toList());

		agentService.sendMessages(agents, 1L);
		verify(messageService).sendMessage(any());
	}
}