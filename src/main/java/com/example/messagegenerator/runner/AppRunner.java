package com.example.messagegenerator.runner;

import com.example.messagegenerator.domain.Agent;
import com.example.messagegenerator.service.AgentService;
import com.example.messagegenerator.util.AgentGenerator;
import com.example.messagegenerator.util.MessageGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

	@Value("${app.agents.count}")
	private int agentCount;
	@Value("${app.messages.count}")
	private int messageCount;

	private final AgentService agentService;
	private final static int ONE_MINUTE = 60_000;


	@Override
	public void run(String... args) {
		log.info("IN run : APPLICATION STARTED");
		List<Agent> agents = AgentGenerator.generate(agentCount).stream()
				.peek(this::setMessages)
				.collect(Collectors.toList());
		final long waitingMills = (messageCount > 1) ? ONE_MINUTE / messageCount : 1L;
		agentService.sendMessages(agents, waitingMills);
		log.info("IN run : APPLICATION ENDED");
	}
	private void setMessages(Agent agent) {
		agent.setMessages(MessageGenerator.generate(agent.getAgentId(), messageCount));
	}
}
