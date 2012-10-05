package com.holyeye.demo.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.holyeye.demo.commons.annotation.Menu;
import com.holyeye.demo.commons.enumeration.PersistentEnumUtil;
import com.holyeye.demo.domain.admin.AdMenu;
import com.holyeye.demo.domain.admin.usertype.TargetType;
import com.holyeye.demo.domain.member.Member;
import com.holyeye.demo.repository.MemberRepository;

@Slf4j
@Menu("B01")
@Controller
public class MemberController {

	@Autowired MemberRepository memberRepository;

	@RequestMapping("member/home")
	public String home(Model model) {

		List<Member> members = memberRepository.findAll();
		model.addAttribute("members", members);

		return "member/home";
	}

	@RequestMapping("/member/memberSaveForm")
	public String adMenuSaveForm(Member member) {

		return "member/memberSaveForm";
	}

	@RequestMapping("/member/saveMember")
	public String saveMenu(Member member) {

		memberRepository.save(member);
		return "redirect:/member/home";
	}

	@RequestMapping("/member/memberUpdateForm")
	public String adMenuUpdateForm(@RequestParam("id") Member member, Model model) {

		model.addAttribute("member", member);
		return "member/memberSaveForm";
	}

}
