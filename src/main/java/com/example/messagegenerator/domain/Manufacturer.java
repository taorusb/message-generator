package com.example.messagegenerator.domain;

public enum Manufacturer {

	SONY("Sony"), PANASONIC("Panasonic"), APPLE("Apple"), LG("LG");

	private final String label;

	Manufacturer(String label) {
		this.label = label;
	}
}
