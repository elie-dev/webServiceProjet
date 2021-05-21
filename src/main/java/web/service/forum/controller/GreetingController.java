package web.service.forum.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.service.forum.security.service.UserDetailsServiceImpl;

@Controller
public class GreetingController {

	@ResponseBody
	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		//model.addAttribute("name", name);
		
		if (UserDetailsServiceImpl.isAdmin()) {
			return "greeting";
		} else {
			return "";
		}
	}
}