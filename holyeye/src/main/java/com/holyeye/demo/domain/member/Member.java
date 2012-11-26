package com.holyeye.demo.domain.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

import com.holyeye.demo.domain.BaseEntity;
import com.holyeye.demo.domain.membercard.MemberCard;
import com.mysema.query.annotations.QueryDelegate;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;

@SuppressWarnings("serial")
@Getter @Setter
@Entity
public class Member //extends BaseEntity<Long>
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private int age;
	
	@OneToMany(mappedBy="member")
	List<MemberCard> memberCards = new ArrayList<MemberCard>();

	public void addMemberCard(MemberCard memberCard) {
		memberCards.add(memberCard);
	}


}
