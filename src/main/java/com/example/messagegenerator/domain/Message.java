package com.example.messagegenerator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Message {
	private String uuid;
	private String agentId;
	private Long previousMessageTimestamp;
	private String activeService;
	private Integer qualityScore;
}
