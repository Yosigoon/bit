package org.bansang.interceptor;


import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;



import org.bansang.dto.MemberDTO;
import org.bansang.service.MemberService;

import lombok.extern.java.Log;

@Log
public class LoginCheckerInterceptor extends HandlerInterceptorAdapter {

	private void saveDest(HttpServletRequest req) { // 기존에 가려고 했던 곳을 URL을 저장해서 세션으로 발급하는 함수

	    String uri = req.getRequestURI();

	    String query = req.getQueryString();

	    if (query == null || query.equals("null")) {
	      query = "";
	    } else {
	      query = "?" + query;
	    }

	    if (req.getMethod().equals("GET")) {
	      log.info("dest: " + (uri + query));
	      req.getSession().setAttribute("dest", uri + query);
	    }
	  }
	
	@Autowired
	private MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		boolean useSession = request.getSession().getAttribute("userSession") != null ? true : false;

		if (useSession) { // 현재 세션 사용중인 상태
			log.info("current user uses session");
			return true;
		}
		Cookie userCookie = WebUtils.getCookie(request, "userCookie");

		boolean useCookie = userCookie != null ? true : false;
		if (useCookie) { // autoLogin Mode
			log.info("current user uses cookie");
			
			MemberDTO dto = memberService.autoLogin(userCookie.getValue());
			if (dto != null) {	// session renew
				session.setAttribute("userSession", dto);
				int amount = 12 * 60 * 60;
				Date sessionLimit = new Date(System.currentTimeMillis()+(1000 * amount));
				
				memberService.updateAutoLimit(userCookie.getValue(), sessionLimit);
				
				return true;
			}

		}
		saveDest(request); // 원래 가려고 했던 URL 정보를 세션에 저장
		response.sendRedirect("/login");

		return false;
	}

}
