package com.example.messagegenerator.util;

import com.example.messagegenerator.domain.Message;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MessageFactory {

	private final static List<String> SERVICES =
			List.of("netflix", "youtube", "amazon_prime", "apple_tv", "hbo", "national_geographic",
					"apple_music", "spotify", "google_maps", "trip_advisor");
	private final static Random RANDOM = new Random();

	private MessageFactory() { }

	static Message create(String agentId) {
		return new Message(
				UUID.randomUUID().toString(),
				agentId,
				0L,
				SERVICES.get(RANDOM.nextInt(10)),
				RANDOM.nextInt(100)
		);
	}
}
