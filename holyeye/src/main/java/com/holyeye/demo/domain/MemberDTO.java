package com.holyeye.demo.domain;

import lombok.Data;
import lombok.ToString;

import com.mysema.query.annotations.QueryProjection;
import com.mysema.query.annotations.QueryType;

public class MemberDTO {

	private Long id;
	private String name;
	private int age;
	private long cardCount;
	private String cardName;
	private Long count;
	
	public MemberDTO() {
	}
	
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
	public MemberDTO(Long id, String name, int age, String cardName) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.cardName = cardName;
	}
	
	@QueryProjection
	public MemberDTO(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getCardCount() {
		return cardCount;
	}

	public void setCardCount(long cardCount) {
		this.cardCount = cardCount;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
	
	
}
