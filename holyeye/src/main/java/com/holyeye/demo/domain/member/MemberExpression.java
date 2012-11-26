package com.holyeye.demo.domain.member;

import org.springframework.util.StringUtils;

import com.mysema.query.annotations.QueryDelegate;
import com.mysema.query.annotations.QueryEmbeddable;
import com.mysema.query.types.expr.BooleanExpression;

public class MemberExpression {

	@QueryDelegate(Member.class)
	public static BooleanExpression isVip(QMember $){
		return $.age.gt(50);
	}
	
	@QueryDelegate(Member.class)
	public static BooleanExpression isAdult(QMember $){
		return $.age.gt(20);
	}
	
	
	@QueryDelegate(Member.class)
	public static BooleanExpression hasFirstname(QMember $, String firstName){
		
		if(StringUtils.hasText(firstName)) {
			return $.name.startsWith(firstName);
		}
		return null;
	}
	
}

