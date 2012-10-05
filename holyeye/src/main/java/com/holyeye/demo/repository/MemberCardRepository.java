package com.holyeye.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.holyeye.demo.domain.member.Member;
import com.holyeye.demo.domain.membercard.MemberCard;

public interface MemberCardRepository extends JpaRepository<MemberCard, Long>, QueryDslPredicateExecutor<MemberCard>{

}
