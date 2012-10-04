package com.holyeye.demo.domain.admin;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Type;

import com.holyeye.demo.domain.BaseEntity;
import com.holyeye.demo.domain.admin.usertype.TargetType;
import com.holyeye.demo.domain.admin.usertype.TargetType.TargetTypeConverter;

@Getter
@Setter
@Entity(name = "TEST_ADMENU")
public class AdMenu extends BaseEntity<Long> {

	String menuId;
	String menuName;
	String url;
	
	
	int displayOrder;
	boolean displayFlag;
	int depth = 1;

	@Type(type = TargetTypeConverter.NAME)
	TargetType targetType;

	@Column(length = 4000)
	String description;

	@OneToMany(mappedBy = "parentAdMenu")
//	@BatchSize(size = 100)
	@OrderBy("displayOrder")
	List<AdMenu> childs;

	@ManyToOne
//	@Fetch(FetchMode.JOIN)
	AdMenu parentAdMenu;

	public void changeParentAdMenu(AdMenu parentAdMenu) {
		this.parentAdMenu = parentAdMenu;
		depth = parentAdMenu.getDepth() + 1;
	}

}
