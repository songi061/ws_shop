package com.example.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.shop.dao.IShopDao;
import com.example.shop.dto.ProductDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class MyController {
	@Autowired
	private IShopDao dao;
	
	@RequestMapping("/")
	public String root(Model model) {
		List<ProductDto> plist = dao.getProducts();
		model.addAttribute("plist",plist);
		
		return "main";
	}
	
	@RequestMapping("/userLoginForm")
	public String userLoginForm() {
		return "/user/loginForm";
	}
	
	
	@RequestMapping("/adminLoginForm")
	public String adminLoginForm() {
		return "/admin/loginForm";
	}
	
	
	@RequestMapping("/userLogin")
	public String userLogin(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String username = request.getParameter("username");
		String pw = request.getParameter("pw");
		
		HttpSession session = request.getSession();
		int result = dao.userLogin(username, pw);
		
		if(result==1) {
			session.setAttribute("username",username);
			return "redirect:/";
		}else {
			redirectAttributes.addFlashAttribute("errorMessage", "계정 로그인에 실패했습니다. 다시 입력해주세요");
			return "redirect:/user/loginForm";
		}
		
		
	}
	
	@RequestMapping("/adminLogin")
	public String admin(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String adminId = request.getParameter("adminId");
		String adminPw = request.getParameter("adminPw");
		
		HttpSession session = request.getSession();
		int result = dao.adminLogin(adminId, adminPw);
		
		if(result==1) {
			session.setAttribute("adminId",adminId);
			return "redirect:/";
		}else {
			redirectAttributes.addFlashAttribute("errorMessage", "계정 로그인에 실패했습니다. 다시 입력해주세요");
			return "redirect:/admin/loginForm";
		}
		
		
	}
	
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request, Model model) {
		String pid_ = request.getParameter("pid");
		int pid = Integer.parseInt("pid_");
		ProductDto product = dao.getProduct(pid);
		model.addAttribute("product",product);
		
		return "detail";
	}
	
	
	@RequestMapping("/addCart")
	public String addCart(@RequestParam("pid")String pid, HttpServletRequest request) {
		int qty = Integer.parseInt(request.getParameter("qty"));
		int pid_ = Integer.parseInt("pid");
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		
		dao.addCart(pid_, qty, username);
		
		return "redirect:detail";
	}
	
	@RequestMapping("/order")
	public String tempOrder(@RequestParam("pid")String pid, HttpServletRequest request) {
		int qty = Integer.parseInt(request.getParameter("qty"));
		int pid_ = Integer.parseInt("pid");
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		
		dao.order(pid_, qty, username);
		
		return "redirect:detail";
	}
	
	
	
}
