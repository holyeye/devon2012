package com.holyeye.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.holyeye.demo.domain.admin.AdMenu;
import com.holyeye.demo.domain.member.Member;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;

public interface MemberRepository extends JpaRepository<Member, Long>, QueryDslPredicateExecutor<Member>, JpaSpecificationExecutor<Member>{

	public List<Member> findByNameStartingWithAndAgeBetweenOrderByAgeDesc(
			String string, int min, int max);

	public Page<Member> findByNameStartingWithAndAgeBetweenOrderByAgeDesc(
			String string, int min, int max, Pageable pageable);

//	public Page<Member> findAll(Predicate predicate, Pageable pageable, OrderSpecifier<?>... orders);

}
