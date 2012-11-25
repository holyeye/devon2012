package com.holyeye.demo.domain.card;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Getter;
import lombok.Setter;

import com.holyeye.demo.domain.BaseDateEntity;
import com.holyeye.demo.domain.BaseEntity;
import com.holyeye.demo.domain.membercard.CardPoint;

@SuppressWarnings("serial")
@Getter @Setter
@Entity
public class Card extends BaseEntity<Long>{

	private String name;
	private int rate;
	
	//create
	public CardPoint createCardPoint(int money) {
		return new CardPoint(name,money,rate);
	}
	
}
