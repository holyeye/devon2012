/*
 * Copyright 2008-2011 the original author or authors.
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
package com.holyeye.demo.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author holyeye
 * 
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseDateEntity<PK extends Serializable> extends AbstractPersistable<PK>{

	private static final long serialVersionUID = -8669707500586538457L;
	
	private String createdBy;
	private String lastModifiedBy;

	@Column(length = 14)
	private String createdDttm;

	@Column(length = 14)
	private String lastModifiedDttm;
	
	/*
	 *	편리를 위해 setId를 오픈합니다.
	 *	부모처럼 protected로 두게되면 web에서 수정시 id값을 바인딩 받지 못합니다.
	 */
	@Override
	public void setId(PK id) {
		super.setId(id);
	}

	@PreUpdate
	public void preUpdate() {
		lastModifiedDttm = getNowTime();
	}

	@PrePersist
	public void prePersist() {
		String nowTime = getNowTime();
		createdDttm = nowTime;
		lastModifiedDttm = nowTime;
	}

	public static String getNowTime() {
		return getTime("yyyyMMddHHmmss");
	}

	public static String getTime(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}

}
