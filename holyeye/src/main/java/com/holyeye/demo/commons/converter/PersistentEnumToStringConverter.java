package com.holyeye.demo.commons.converter;

import org.springframework.core.convert.converter.Converter;

import com.holyeye.demo.commons.enumeration.PersistentEnum;

@SuppressWarnings("rawtypes")
public class PersistentEnumToStringConverter implements Converter<Enum, String> {

	@Override
	public String convert(Enum source) {
		if(source instanceof PersistentEnum) {
			return ((PersistentEnum) source).getValue();
		}
		return source.name();
	}
}