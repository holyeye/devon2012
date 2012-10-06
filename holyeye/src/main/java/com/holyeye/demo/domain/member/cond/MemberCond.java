package com.holyeye.demo.domain.member.cond;

import org.springframework.util.StringUtils;

import com.holyeye.demo.domain.member.QMember;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;

import lombok.Data;

@Data
public class MemberCond {

	private String name;
	private Integer age;
	
	public Predicate toDSL() {
		
		//QUERY DSL
		QMember member = QMember.member;
//		BooleanExpression dsl = member.name.contains(name).and(member.age.goe(age));
		
		BooleanBuilder builder = new BooleanBuilder();
		
		if(StringUtils.hasText(name)){
			builder.and(member.name.contains(name));
		}
		if(age != null) {
			builder.and(member.age.goe(age));
		}
		
		return builder.getValue();
		
	}
}
