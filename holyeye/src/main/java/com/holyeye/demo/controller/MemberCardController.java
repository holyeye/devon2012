package com.holyeye.demo.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.holyeye.demo.commons.annotation.Menu;
import com.holyeye.demo.domain.card.Card;
import com.holyeye.demo.domain.member.Member;
import com.holyeye.demo.domain.membercard.MemberCard;
import com.holyeye.demo.repository.CardRepository;
import com.holyeye.demo.repository.MemberCardRepository;
import com.holyeye.demo.repository.MemberRepository;
import com.holyeye.demo.service.MemberCardService;

@Slf4j
@Menu("D0101")
@Controller
public class MemberCardController {

	@Autowired MemberCardRepository memberCardRepository;
	@Autowired MemberRepository memberRepository;
	@Autowired CardRepository cardRepository;
	
	@Autowired MemberCardService memberCardService;
	
	@RequestMapping("memberCard/home")
	public String home(Model model) {
		
		List<MemberCard> memberCards = memberCardRepository.findAll();
		model.addAttribute("memberCards", memberCards);
		return "memberCard/home";
	}

	@Menu("D0102")
	@RequestMapping("memberCard/memberCardSaveForm")
	public String memberSaveForm(Model model) {
		
		List<Member> members = memberRepository.findAll();
		List<Card> cards = cardRepository.findAll();
		
		model.addAttribute("members", members);
		model.addAttribute("cards", cards);
		
		return "memberCard/memberCardSaveForm";
	}

	@RequestMapping("memberCard/saveMemberCard")
	public String saveMemberCard(
			@RequestParam("memberId") Member member,
			@RequestParam("cardId") Card card) {

		MemberCard memberCard = new MemberCard(member, card);
		memberCardRepository.save(memberCard);
		
		return "redirect:/memberCard/home";
	}
	
	@RequestMapping("memberCard/payMoney")
	public String payMoney(
			@RequestParam("memberCardId") MemberCard memberCard,
			@RequestParam("money") int money) {
		
		memberCardService.payMoney(memberCard, money);
		
		return "redirect:/memberCard/home";
	}
	
	@RequestMapping("memberCard/memberCardDetail")
	public String payMoney(@RequestParam("id") MemberCard memberCard, Model model) {
		
		model.addAttribute("memberCard", memberCard);
		return "memberCard/memberCardDetail";
	}	
}
