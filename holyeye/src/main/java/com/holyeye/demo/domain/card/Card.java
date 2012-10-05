package com.holyeye.demo.domain.card;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Getter;
import lombok.Setter;

import com.holyeye.demo.domain.BaseDateEntity;
import com.holyeye.demo.domain.BaseEntity;
import com.holyeye.demo.domain.membercard.CardPoint;

@Getter @Setter
@Entity
public class Card extends BaseEntity<Long>{

	private String name;
	private int rate;
	
	public CardPoint createCardPoint(int money) {
		CardPoint cardPoint = new CardPoint(name,money,rate);
		return cardPoint;
	}
	
}
