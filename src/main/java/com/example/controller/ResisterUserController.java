package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.service.UserService;

/**
 * ユーザ-登録のためのコントローラ.
 * @author ashibe
 *
 */
@Controller
@RequestMapping("/register-user")
public class ResisterUserController {
	@Autowired
	private UserService userService;

	@ModelAttribute
	public RegisterUserForm setUpForm() {
		return new RegisterUserForm();
	}

	/**
	 * ユーザー情報登録画面へ遷移.
	 * 
	 * @return ユーザー情報登録画面
	 */
	@RequestMapping("/toRegister")
	public String toRegister(Model model) {
		return "register";
	}

	/**
	 * 
	 * ユーザー情報を登録
	 * 
	 * @param form   
	 * @param result 
	 * @return 
	 */
	@RequestMapping("/register")
	public String register(@Validated RegisterUserForm form, BindingResult result,RedirectAttributes redirectAttributes,Model model) {
		System.out.println(form);
		User error = userService.findByEmail(form.getEmail());

		if (!(error == null)) {
			result.rejectValue("email", "", "このメールアドレスは既に登録されています");
		}
		
		if (result.hasErrors()) {
			return toRegister(model);
		}

		User user = new User();

		BeanUtils.copyProperties(form, user);
		userService.register(user);

		return "redirect:/login-user/to-login";
	}
		
	/**
	 * ログイン画面へ遷移
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "redirect:/login-user/to-login";
	}
	

}
