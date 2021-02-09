package com.freshvotes.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;
import com.freshvotes.service.UserService;

@Controller
public class DashboardController {
	
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private UserService userService;
	
@RequestMapping(value="/",method = RequestMethod.GET)
public String rootView() {
	return "index";
}
@GetMapping("/login")
public String login() {
	return "login";
}
@GetMapping("/dashboard")
public String dashboard(@AuthenticationPrincipal User user,ModelMap model) {
	List<Product> products = productRepo.findByUser(user);
	model.put("products", products);
	return "dashboard";
}

@GetMapping("/register")
public String register(ModelMap model) {
	model.put("user", new User());
	return "register";
}

@PostMapping("/register")
public String registerPost(User user) {
	userService.save(user);
	return "redirect:/login";

}
}
