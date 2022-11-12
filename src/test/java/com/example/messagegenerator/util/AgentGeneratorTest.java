package com.example.messagegenerator.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentGeneratorTest {

	@Test
	void testGenerate() {
		assertEquals(1, AgentGenerator.generate(1).size());
	}
}