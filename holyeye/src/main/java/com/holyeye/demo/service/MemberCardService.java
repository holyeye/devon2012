package com.holyeye.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holyeye.demo.domain.membercard.MemberCard;

@Service
@Transactional
public class MemberCardService {
	
	public void payMoney(MemberCard memberCard, int money) {
		memberCard.payMoney(money);
	}
}
