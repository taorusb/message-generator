package com.example.messagegenerator.service;

import com.example.messagegenerator.domain.Message;
import com.example.messagegenerator.util.MessageGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

	@Mock
	private KafkaTemplate<String, Message> kafkaTemplate;
	private MessageService messageService;

	@BeforeEach
	void setUp() {
		messageService = new MessageService(kafkaTemplate);
		messageService.setTopic("123");
	}

	@Test
	void sendMessage() {
		Message message = MessageGenerator.generate("1", 1).get(0);
 		when(kafkaTemplate.send("123", message)).thenReturn(mock(ListenableFuture.class));
		messageService.sendMessage(message);
		verify(kafkaTemplate).send(Mockito.any(), Mockito.any());
	}
}