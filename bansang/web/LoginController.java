package org.bansang.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import org.bansang.dto.MemberDTO;
import org.bansang.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;


import lombok.extern.java.Log;

@Controller
@RequestMapping("/*")
@Log
public class LoginController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/login")
	public void loginGet() {
		return;
	}
	
	@PostMapping("/login")
    public void loginPost(MemberDTO dto, Model model) {
        model.addAttribute("userSession", memberService.login(dto)); // jsp가 아닌  preHandler 쪽으로 보내기 위함
        log.info(""+memberService.login(dto));
        return;
    }
	
	 @GetMapping("/logout")
	 public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
	    	
	    	
	    	Object obj = session.getAttribute("userSession");

	   	  	if (obj != null) {
	   	  		session.removeAttribute("userSession");
	   	  		session.invalidate();
		   	  
	   	  		Cookie loginCookie = WebUtils.getCookie(request, "userCookie");
		
	   	  		if (loginCookie != null) {
		   	  
		   		  loginCookie.setPath("/");
		   		  loginCookie.setMaxAge(0);
		   		  response.addCookie(loginCookie);
		   	  
	   	  		}
		   	} 
	   	  	return "redirect:/login";    	
	    }
}
