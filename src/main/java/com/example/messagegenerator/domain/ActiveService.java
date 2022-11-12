package com.example.messagegenerator.domain;

public enum ActiveService {

	NETFLIX("netflix"), YOUTUBE("youtube"), AMAZON_PRIME("amazon_prime"), APPLE_TV("apple_tv"),
	HBO("hbo"), NATIONAL_GEOGRAPHIC("national_geographic"), APPLE_MUSIC("apple_music"), SPOTIFY("spotify"),
	GOOGLE_MAPS("google_maps"), TRIP_ADVISOR("trip_advisor"), SOUNDCLOUD("soundcloud"), GLOVO("glovo");

	private final String label;

	ActiveService(String label) {
		this.label = label;
	}
}
