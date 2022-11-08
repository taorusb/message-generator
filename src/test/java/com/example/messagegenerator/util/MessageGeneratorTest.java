package com.example.messagegenerator.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageGeneratorTest {

	@Test
	void testGenerate() {
		assertEquals(1, MessageGenerator.generate("1", 1).size());
	}
}