package com.example.messagegenerator.domain;

public enum DeviceType {

	MOBILE_PHONE("mobile_phone"), PC("pc"), LAPTOP("laptop"),
	TABLET("tablet"), SMART_WATCH("smart_watch"), OTHER("other");

	private final String label;

	DeviceType(String label) {
		this.label = label;
	}
}
