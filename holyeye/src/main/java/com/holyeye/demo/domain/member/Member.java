package com.holyeye.demo.domain.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Getter;
import lombok.Setter;

import com.holyeye.demo.domain.BaseDateEntity;
import com.holyeye.demo.domain.BaseEntity;
import com.holyeye.demo.domain.membercard.MemberCard;

@Getter @Setter
@Entity
public class Member extends BaseEntity<Long>{

	private String name;
	private int age;
	
	@OneToMany(mappedBy="member")
	List<MemberCard> memberCards = new ArrayList<MemberCard>();

	public void addMemberCard(MemberCard memberCard) {
		memberCards.add(memberCard);
	}
}
