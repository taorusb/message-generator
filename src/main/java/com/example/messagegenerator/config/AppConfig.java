package com.example.messagegenerator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AppConfig {

	@Bean
	public Executor messageExecutor(@Value("${app.message.thread.count}") int threadCount) {
		return Executors.newWorkStealingPool(threadCount);
	}

	@Bean
	public ExecutorService agentExecutor(@Value("${app.agent.thread.count}") int threadCount) {
		return Executors.newFixedThreadPool(threadCount);
	}
}
