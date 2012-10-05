package com.holyeye.demo.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.holyeye.demo.commons.annotation.Menu;

@Slf4j
@Menu("C01")
@Controller
public class CardController {

	@RequestMapping("card/home")
	public String home(Model model){
		
		return "menu/home";
	}
}
