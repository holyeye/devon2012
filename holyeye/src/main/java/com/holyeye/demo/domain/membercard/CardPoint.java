package com.holyeye.demo.domain.membercard;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.holyeye.demo.domain.BaseDateEntity;
import com.holyeye.demo.domain.BaseEntity;

import lombok.Getter;

@Getter
@Entity
public class CardPoint extends BaseEntity<Long>{

	private String title;
	private int point;
	
}
