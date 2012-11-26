package com.holyeye.demo.domain;

import lombok.Data;
import lombok.ToString;

import com.mysema.query.annotations.QueryProjection;
import com.mysema.query.annotations.QueryType;

@Data
public class MemberDTO {

	private Long id;
	private String name;
	private int age;
	private long cardCount;
	
	@QueryProjection
	public MemberDTO(Long id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	@QueryProjection
	public MemberDTO(Long id, String name, int age, long cardCount) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.cardCount = cardCount;
	}
	
	@QueryProjection
	public MemberDTO(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
}
