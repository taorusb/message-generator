package com.example.messagegenerator.util;

import com.example.messagegenerator.domain.Message;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageGenerator {

	private MessageGenerator() { }

	public static List<Message> generate(String agentId, int count) {
		return Stream.iterate(0, n -> n < count, n -> n + 1)
				.map(x -> MessageFactory.create(agentId))
				.collect(Collectors.toList());
	}
}
