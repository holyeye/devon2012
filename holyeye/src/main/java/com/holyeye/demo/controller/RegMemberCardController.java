package com.holyeye.demo.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.holyeye.demo.commons.annotation.Menu;

@Slf4j
@Menu("D0101")
@Controller
public class RegMemberCardController {

	@RequestMapping("memberCard/regForm")
	public String home(Model model) {

		return "card/home";
	}
}
