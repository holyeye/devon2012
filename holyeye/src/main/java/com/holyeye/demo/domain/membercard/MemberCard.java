package com.holyeye.demo.domain.membercard;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Getter;

import com.holyeye.demo.domain.BaseDateEntity;
import com.holyeye.demo.domain.BaseEntity;
import com.holyeye.demo.domain.card.Card;
import com.holyeye.demo.domain.member.Member;

@Getter
@Entity
public class MemberCard extends BaseEntity<Long>{

	@ManyToOne
	private Member member;
	
	@ManyToOne
	private Card card;
	
	@OneToMany @JoinColumn(name="memberCard_id")
	private List<CardPoint> cardPoints = new ArrayList<CardPoint>();
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	public void addCardPoint(CardPoint cardPoint) {
		cardPoints.add(cardPoint);
	}
}
