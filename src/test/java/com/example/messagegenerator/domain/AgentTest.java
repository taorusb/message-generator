package com.example.messagegenerator.domain;

import com.example.messagegenerator.service.MessageService;
import com.example.messagegenerator.util.AgentGenerator;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentTest {

	@Mock
	MessageService messageService;
	@Mock
	SendResult<String, Message> sendResult;
	RecordMetadata recordMetadata = new RecordMetadata(
			new TopicPartition("123", 1),
			1L,
			1,
			123L,
			1,
			1);

	@Test
	void testSendMessages() {
		Agent agent = AgentGenerator.generate(messageService, 1).get(0);
		when(messageService.sendMessage(any())).thenReturn(CompletableFuture.completedFuture(sendResult));
		when(sendResult.getRecordMetadata()).thenReturn(recordMetadata);
		agent.sendMessages(1);
		verify(messageService).sendMessage(any());
	}
}