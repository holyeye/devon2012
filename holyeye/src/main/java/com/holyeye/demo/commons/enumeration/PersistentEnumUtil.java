package com.holyeye.demo.commons.enumeration;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PersistentEnumUtil {

	public static <E extends PersistentEnum> E valueOf(Class<E> enumType, Object value) {
		E[] es = enumType.getEnumConstants();
		for (E e : es) {
			if (e.getValue() == value || e.getValue().equals(value))
				return e;
		}
		return null;
	}

	public static <E extends PersistentEnum> List<E> sortedListOf(Class<E> enumType) {
		E[] es = enumType.getEnumConstants();
		List<E> ess = Arrays.asList(es);
		Collections.sort(ess, new PersistentEnumComparator<PersistentEnum>());
		return ess;
	}
	
	public static <E extends PersistentEnum> Map<String,String> sortedMapOf(Class<E> enumType) {
		
		List<E> sortedListOf = sortedListOf(enumType);
		Map<String, String> sortedMap = new LinkedHashMap<String, String>();
		for (E e : sortedListOf) {
			sortedMap.put(e.getValue(), e.getDesc());
		}
		
		return sortedMap;
	}
	
	public static <E extends PersistentEnum> Map<String,String> sortedMapOfAndDefault(String key, String value, Class<E> enumType) {
		
		List<E> sortedListOf = sortedListOf(enumType);
		Map<String, String> sortedMap = new LinkedHashMap<String, String>();
		
		sortedMap.put(key, value);
		for (E e : sortedListOf) {
			sortedMap.put(e.getValue(), e.getDesc());
		}
		
		return sortedMap;
	}
	
}
