package com.holyeye.demo.domain.admin.usertype;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.holyeye.demo.commons.enumeration.PersistentEnum;
import com.holyeye.demo.commons.enumeration.usertype.GenericEnumUserType;

@Getter
@AllArgsConstructor
public enum TargetType implements PersistentEnum{
	
	SCRIPT("S", "스크립트방식", 1),
	POPUP("P", "팝업방식", 2),
	DIVISION("D", "분할하기", 3);
	
	private final String value;
	private final String desc;
	private final int order;		
	
	public static class TargetTypeConverter extends GenericEnumUserType<TargetType> {
		public static final String NAME = "com.holyeye.demo.domain.admin.usertype.TargetType$TargetTypeConverter";
	}
	
}