package com.holyeye.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.holyeye.demo.domain.admin.AdMenu;
import com.holyeye.demo.domain.member.Member;

public interface MemberRepository  extends JpaRepository<Member, Long>, QueryDslPredicateExecutor<Member>{

}
