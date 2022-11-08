package com.example.messagegenerator.runner;

import com.example.messagegenerator.domain.Agent;
import com.example.messagegenerator.service.MessageService;
import com.example.messagegenerator.util.AgentGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class AppRunner implements CommandLineRunner {

	@Value("${app.agents.count}")
	private int agentCount;
	@Value("${app.messages.count}")
	private int messageCount;
	private final MessageService messageService;

	@Override
	public void run(String... args) {
		log.info("IN run : APPLICATION STARTED");
		List<Agent> agents = AgentGenerator.generate(messageService, agentCount);
		log.info("IN run : Generated " + agents.size() + " of agents.");
		agents.forEach(agent -> agent.sendMessages(messageCount));
	}
}
