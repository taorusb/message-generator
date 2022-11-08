package com.example.messagegenerator.domain;

public enum OSType {

	IOS("iOS"), WINDOWS("Windows"), LINUX("Linux"), ANDROID_OS("Android"), MAC_OS("macOS");

	private final String label;

	OSType(String label) {
		this.label = label;
	}
}
