package com.holyeye.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holyeye.demo.domain.membercard.MemberCard;
import com.holyeye.demo.repository.MemberCardRepository;

@Service
@Transactional
public class MemberCardService {

	@Autowired MemberCardRepository memberCardRepository;
	
	public void payMoney(MemberCard memberCard, int money) {
		memberCard.payMoney(money);
	}
}
