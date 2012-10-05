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
import com.holyeye.demo.repository.CardRepository;

@Slf4j
@Menu("C01")
@Controller
public class CardController {

	@Autowired CardRepository cardRepository;

	@RequestMapping("card/home")
	public String home(Model model) {

		List<Card> cards = cardRepository.findAll();
		model.addAttribute("cards", cards);

		return "card/home";
	}

	@RequestMapping("card/cardSaveForm")
	public String cardSaveForm(Card card) {

		return "card/cardSaveForm";
	}

	@RequestMapping("card/saveCard")
	public String saveMenu(Card card) {

		cardRepository.save(card);
		return "redirect:/card/home";
	}

	@RequestMapping("card/cardUpdateForm")
	public String cardUpdateForm(@RequestParam("id") Card card, Model model) {

		model.addAttribute("card", card);
		return "card/cardSaveForm";
	}
}
