package com.holyeye.demo.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.holyeye.demo.domain.admin.AdMenu;

public interface AdMenuRepository extends JpaRepository<AdMenu, Long>, QueryDslPredicateExecutor<AdMenu>{

	@Query("from TEST_ADMENU")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<AdMenu> findAllData();
}
