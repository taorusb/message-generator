package com.example.messagegenerator.util;

import com.example.messagegenerator.domain.Agent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AgentGenerator {

	private AgentGenerator() { }

	public static List<Agent> generate(int count) {
		return Stream.iterate(0, n -> n < count, n -> n + 1)
				.map(x -> AgentFactory.create())
				.collect(Collectors.toList());
	}
}
