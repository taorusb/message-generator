package com.example.messagegenerator.util;

import com.example.messagegenerator.domain.Agent;
import com.example.messagegenerator.domain.DeviceType;
import com.example.messagegenerator.domain.Manufacturer;
import com.example.messagegenerator.domain.OSType;
import com.example.messagegenerator.service.MessageService;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class AgentFactory {

	private final static Random RANDOM = new Random();
	private final static List<DeviceType> DEVICE_TYPES = List.of(DeviceType.values());
	private final static List<Manufacturer> MANUFACTURERS = List.of(Manufacturer.values());
	private final static List<OSType> OS_TYPES = List.of(OSType.values());

	private AgentFactory() { }

	static Agent create(MessageService messageService) {
		return new Agent(
				UUID.randomUUID().toString(),
				DEVICE_TYPES.get(RANDOM.nextInt(DEVICE_TYPES.size())),
				MANUFACTURERS.get(RANDOM.nextInt(MANUFACTURERS.size())),
				OS_TYPES.get(RANDOM.nextInt(OS_TYPES.size())),
				messageService
		);
	}
}
