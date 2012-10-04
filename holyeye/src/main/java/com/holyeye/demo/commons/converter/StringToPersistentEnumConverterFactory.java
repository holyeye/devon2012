/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.holyeye.demo.commons.converter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.holyeye.demo.commons.enumeration.PersistentEnum;
import com.holyeye.demo.commons.enumeration.PersistentEnumUtil;

/**
 * PersistentEnum 처리
 */
@Slf4j
@SuppressWarnings({ "unchecked", "rawtypes" })
final class StringToPersistentEnumConverterFactory implements ConverterFactory<String, Enum> {

	public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
		if (PersistentEnum.class.isAssignableFrom(targetType)) {
			return new StringToPersistentEnum(targetType);
		} else {
			return new StringToEnum<T>(targetType);
		}

	}

	private class StringToPersistentEnum<T extends PersistentEnum> implements Converter<String, T> {

		private final Class<T> enumType;

		public StringToPersistentEnum(Class<T> enumType) {
			this.enumType = enumType;
		}

		public T convert(String source) {
			if (source.length() == 0) {
				// It's an empty enum identifier: reset the enum value to null.
				return null;
			}
			T valueOf = PersistentEnumUtil.valueOf(enumType, source.trim());
			if(source != null && valueOf == null) {
				log.warn("Not Match PersistentEnumType param={}, enumType={}", source.trim(), enumType);
			}
			return valueOf;
		}
	}

	private class StringToEnum<T extends Enum> implements Converter<String, T> {

		private final Class<T> enumType;

		public StringToEnum(Class<T> enumType) {
			this.enumType = enumType;
		}

		public T convert(String source) {
			if (source.length() == 0) {
				// It's an empty enum identifier: reset the enum value to null.
				return null;
			}
			return (T) Enum.valueOf(this.enumType, source.trim());
		}
	}

}
