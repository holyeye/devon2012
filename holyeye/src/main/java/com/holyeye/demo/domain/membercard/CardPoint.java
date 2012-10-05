package com.holyeye.demo.domain.membercard;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.holyeye.demo.domain.BaseDateEntity;
import com.holyeye.demo.domain.BaseEntity;

import lombok.Getter;

@Getter
@Entity
public class CardPoint extends BaseEntity<Long>{

	public CardPoint() {
	}
	
	public CardPoint(String title, int money, int rate) {
		this.title = title;
		this.money = money;
		this.rate = rate;
		this.point = (money * rate)/100;
	}

	private String title;
	private int money;
	private int point;
	private int rate;
	
}
