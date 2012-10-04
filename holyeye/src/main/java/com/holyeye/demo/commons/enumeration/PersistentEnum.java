package com.holyeye.demo.commons.enumeration;

import org.codehaus.jackson.annotate.JsonValue;

public interface PersistentEnum {

	@JsonValue
	public String getValue();
	public String getDesc();
	public int getOrder();
}
