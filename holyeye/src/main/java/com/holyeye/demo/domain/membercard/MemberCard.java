package com.holyeye.demo.domain.membercard;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import com.holyeye.demo.domain.BaseDateEntity;
import com.holyeye.demo.domain.BaseEntity;
import com.holyeye.demo.domain.card.Card;
import com.holyeye.demo.domain.member.Member;
import com.mysema.query.annotations.QueryInit;

@Slf4j
@Getter
@Entity
public class MemberCard extends BaseEntity<Long>{
	
	public void payMoney(int money) {
		CardPoint createCardPoint = card.createCardPoint(money);
		addCardPoint(createCardPoint);
	}
	

	public MemberCard() {}
	
	public MemberCard(Member member, Card card) {
		setMember(member);
		setCard(card);
	}
	
	@ManyToOne
	private Member member;
	
	@ManyToOne
	private Card card;
	
	@OneToMany(cascade=CascadeType.PERSIST) @JoinColumn(name="memberCard_id")
	private List<CardPoint> cardPoints = new ArrayList<CardPoint>();
	
	private void setMember(Member member) {
		this.member = member;
		member.addMemberCard(this);
	}
	private void setCard(Card card) {
		this.card = card;
	}
	private void addCardPoint(CardPoint cardPoint) {
		cardPoints.add(cardPoint);
	}

	//BIZ
	
	//VIEW
	public int getTotalPoint() {
		int totalPoint = 0;
		for (CardPoint cardPoint : cardPoints) {
			totalPoint += cardPoint.getPoint();
		}
		return totalPoint;
	}
}
