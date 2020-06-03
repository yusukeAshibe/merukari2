package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.form.LoginForm;

/**
 * ログイン操作を行うコントローラー
 * @author ashibe
 *
 */
@Controller
@RequestMapping("/login-user")
public class LoginController {
	
 private HttpSession session;
	
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	/**
	 * エラーの際、ログイン画面に遷移.
	 * @param model
	 * @param error
	 * @param form
	 * @return
	 */
	@RequestMapping("/to-login")
	public String toLogin(Model model, @RequestParam(required = false) String error, @Validated LoginForm form, BindingResult result) {
		if (error != null) {
			model.addAttribute("error", "メールアドレスかパスワードが間違っています ");
		}
		if(result.hasErrors()) {
			return "login.html";
		}
	
		return "login.html";
	}
	
	
	/**
	 * ログアウト.
	 * 
	 * @return 
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/login-user/to-login";
		
	}

}
