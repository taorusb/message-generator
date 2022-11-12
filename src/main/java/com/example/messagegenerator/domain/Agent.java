package com.example.messagegenerator.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;


@Log4j2
@Getter
@Setter
public class Agent {

	private String agentId;
	private DeviceType deviceType;
	private Manufacturer manufacturer;
	private OSType osType;
	private List<Message> messages;

	public Agent(String agentId, DeviceType deviceType, Manufacturer manufacturer, OSType osType) {
		this.agentId = agentId;
		this.deviceType = deviceType;
		this.manufacturer = manufacturer;
		this.osType = osType;
		this.messages = new ArrayList<>();
	}
}
