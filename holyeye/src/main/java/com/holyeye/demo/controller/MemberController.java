package com.holyeye.demo.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.holyeye.demo.commons.annotation.Menu;
import com.holyeye.demo.commons.enumeration.PersistentEnumUtil;
import com.holyeye.demo.domain.admin.AdMenu;
import com.holyeye.demo.domain.admin.usertype.TargetType;
import com.holyeye.demo.domain.member.Member;
import com.holyeye.demo.domain.member.QMember;
import com.holyeye.demo.domain.member.cond.MemberCond;
import com.holyeye.demo.repository.MemberRepository;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.expr.BooleanExpression;

@Slf4j
@Menu("B0101")
@Controller
public class MemberController {

	@Autowired MemberRepository memberRepository;

	@RequestMapping("member/home")
	public String home(MemberCond cond, Model model) {
		
		//QUERY DSL
//		QMember qMember = QMember.member;
//		BooleanExpression containsName = qMember.name.contains(cond.getName());
//		BooleanExpression gtAge = qMember.age.gt(cond.getAge());
		
		Iterable<Member> members = memberRepository.findAll(cond.toDSL());
		model.addAttribute("members", members);

		return "member/home";
	}

	@RequestMapping("member/memberSaveForm")
	public String memberSaveForm(Member member) {

		return "member/memberSaveForm";
	}

	@RequestMapping("member/saveMember")
	public String saveMenu(Member member) {

		memberRepository.save(member);
		return "redirect:/member/home";
	}

	@RequestMapping("member/memberUpdateForm")
	public String memberUpdateForm(@RequestParam("id") Member member, Model model) {

		model.addAttribute("member", member);
		return "member/memberSaveForm";
	}

}
