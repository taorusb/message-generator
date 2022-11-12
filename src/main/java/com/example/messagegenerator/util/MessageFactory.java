package com.example.messagegenerator.util;

import com.example.messagegenerator.domain.Message;
import com.example.messagegenerator.domain.ActiveService;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MessageFactory {

	private final static List<ActiveService> SERVICES = List.of(ActiveService.values());
	private final static Random RANDOM = new Random();

	private MessageFactory() { }

	static Message create(String agentId) {
		return new Message(
				UUID.randomUUID().toString(),
				agentId,
				0L,
				SERVICES.get(RANDOM.nextInt(SERVICES.size())),
				RANDOM.nextInt(100)
		);
	}
}
