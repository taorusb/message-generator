package com.example.messagegenerator.util;

import com.example.messagegenerator.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgentGeneratorTest {

	@Mock
	MessageService messageService;

	@Test
	void testGenerate() {
		assertEquals(1, AgentGenerator.generate(messageService, 1).size());
	}
}