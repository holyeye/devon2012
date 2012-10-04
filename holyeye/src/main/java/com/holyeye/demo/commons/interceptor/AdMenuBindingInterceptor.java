package com.holyeye.demo.commons.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.holyeye.demo.commons.annotation.Menu;
import com.holyeye.demo.commons.spring.ControllerAnnotationFinder;
import com.holyeye.demo.domain.admin.AdMenu;
import com.holyeye.demo.domain.admin.QAdMenu;
import com.holyeye.demo.repository.AdMenuRepository;


@Slf4j
public class AdMenuBindingInterceptor extends HandlerInterceptorAdapter {
	
	private static final String ADMIN_ROOT_MENU_ID = "ADMENU_ROOT_MENU_ID";
	private static final String ADMENU_ROOT_MENU_CHILDS = "ADMENU_ROOT_MENU_CHILDS";
	private static final String ADMENU_MENU_ID = "ADMENU_MENU_ID";
	private static final String ADMENU_MENU_NAME = "ADMENU_MENU_NAME";
	
	@Autowired AdMenuRepository adMenuRepository;
	
	ControllerAnnotationFinder menuAnnotationFinder = new ControllerAnnotationFinder();
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		log.debug("call AdMenuBindingInterceptor requestURI={}", request.getRequestURI());
		
		if(!(handler instanceof HandlerMethod)) {
			return true;
		}
		
		Menu menuAnno = menuAnnotationFinder.getAnnotation(handler, Menu.class);
		
		if(menuAnno != null && StringUtils.hasText(menuAnno.value())) {
			QAdMenu adMenu = QAdMenu.adMenu;
			AdMenu findAdMenu = adMenuRepository.findOne(adMenu.menuId.eq(menuAnno.value()));
			
			log.debug("findAdMenu = {}", findAdMenu);
			
			if(findAdMenu == null){
				return true;
			}
			
			if(findAdMenu.getParentAdMenu() == null) {
				request.setAttribute(ADMIN_ROOT_MENU_ID, findAdMenu.getMenuId());
				request.setAttribute(ADMENU_ROOT_MENU_CHILDS, findAdMenu.getChilds());				
			}else{
				request.setAttribute(ADMIN_ROOT_MENU_ID, findAdMenu.getParentAdMenu().getMenuId());
				request.setAttribute(ADMENU_ROOT_MENU_CHILDS, findAdMenu.getParentAdMenu().getChilds());
			}
			
			request.setAttribute(ADMENU_MENU_ID, findAdMenu.getMenuId());
			request.setAttribute(ADMENU_MENU_NAME, findAdMenu.getMenuName());
			
		}
		
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		if(modelAndView != null) {
			QAdMenu adMenu = QAdMenu.adMenu;
			Iterable<AdMenu> rootMenus = adMenuRepository.findAll(adMenu.depth.eq(1).and(adMenu.displayFlag.isTrue()), adMenu.displayOrder.asc());
			request.setAttribute("rootMenus", rootMenus);
			
		}
	}



}
