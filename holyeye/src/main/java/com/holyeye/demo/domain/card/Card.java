package com.holyeye.demo.domain.card;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Getter;
import lombok.Setter;

import com.holyeye.demo.domain.BaseDateEntity;
import com.holyeye.demo.domain.BaseEntity;

@Getter @Setter
@Entity
public class Card extends BaseEntity<Long>{

	private String name;
	private int rate;
	
}
