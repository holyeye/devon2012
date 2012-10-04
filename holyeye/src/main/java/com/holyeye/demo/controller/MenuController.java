package com.holyeye.demo.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holyeye.demo.commons.annotation.Menu;
import com.holyeye.demo.commons.enumeration.PersistentEnumUtil;
import com.holyeye.demo.domain.admin.AdMenu;
import com.holyeye.demo.domain.admin.QAdMenu;
import com.holyeye.demo.domain.admin.usertype.TargetType;
import com.holyeye.demo.repository.AdMenuRepository;
import com.mysema.query.types.Predicate;

@Slf4j
@Menu("A01")
@Controller
public class MenuController {

	@Autowired AdMenuRepository adMenuRepository;
	
	@PersistenceContext
	EntityManager em;
	
	@RequestMapping("/menu/home")
	public String list(Model model) {
		log.debug("메뉴관리");

		QAdMenu adMenu = QAdMenu.adMenu;
		
		List<AdMenu> menus = (List<AdMenu>) adMenuRepository.findAll(depth(1), adMenu.displayOrder.asc());
		
//		adMenuRepository.findAll(adMenu.depth.eq(1), adMenu.depth.desc());
		
		model.addAttribute("menus", menus);

		return "menu/home";
	}
	
	@RequestMapping("/menu/adMenuSaveForm")
	public String adMenuSaveForm(AdMenu adMenu, Model model) {
		
		List<AdMenu> rootMenus = (List<AdMenu>) adMenuRepository.findAll(depth(1));
		model.addAttribute("rootMenus", rootMenus);
		model.addAttribute("targetTypeOptions", PersistentEnumUtil.sortedMapOf(TargetType.class));
		
		return "menu/adMenuSaveForm";
	}
	
	@RequestMapping("/menu/adMenuUpdateForm")
	public String adMenuUpdateForm(@RequestParam("id") AdMenu adMenu, Model model) {
		
		List<AdMenu> rootMenus = (List<AdMenu>) adMenuRepository.findAll(depth(1));
		
		model.addAttribute("rootMenus", rootMenus);
		model.addAttribute("menu", adMenu);
		model.addAttribute("targetTypeOptions", PersistentEnumUtil.sortedMapOf(TargetType.class));
		return "menu/adMenuSaveForm";
	}
	
	
	@RequestMapping("/menu/saveMenu")
	public String saveMenu(AdMenu adMenu, @RequestParam("parentMenuId") Long parentMenuId, BindingResult result) {
		
		log.debug("메뉴저장");
		log.debug(ToStringBuilder.reflectionToString(adMenu));
		
		if(parentMenuId != null) {
			AdMenu parentAdMenu = adMenuRepository.findOne(parentMenuId);
			adMenu.changeParentAdMenu(parentAdMenu);
		}
		
		adMenuRepository.save(adMenu);
		
		return "redirect:/menu/home";
	}
	
	@RequestMapping("/menu/test")
	@Menu("")
	public @ResponseBody String test() {
		
		List<AdMenu> rootMenus = (List<AdMenu>) adMenuRepository.findAll();
		log.debug("메뉴저장");
		AdMenu adMenu = adMenuRepository.findOne(5L);
		log.debug("AdMenu {}", adMenu.getMenuName());
		log.debug("AdMenu {}", adMenu.getParentAdMenu());
		
		
		return "go";
	}
	
	
	@RequestMapping("/menu/testall")
	@Menu("")
	public @ResponseBody String testall() {
		
		log.debug("all load");
		
		List<AdMenu> rootMenus = (List<AdMenu>) adMenuRepository.findAllData();
		for (AdMenu adMenu : rootMenus) {
			log.debug("adMenu data = {}", adMenu.getMenuName());
			if(adMenu.getParentAdMenu() != null) {
				log.debug("adMenu.getParentAdMenu() data = {}", adMenu.getParentAdMenu().getChilds());
			}
		}
//		em.createQuery("from TEST_ADMENU").setHint("org.hibernate.cacheable", true).getResultList();
		
		return "go";
	}
	
	
	
	private Predicate depth(int depth) {
		QAdMenu adMenu = QAdMenu.adMenu;
		return adMenu.depth.eq(depth);
	}	
	
}
