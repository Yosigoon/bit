package org.bansang.interceptor;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import org.bansang.dto.MemberDTO;
import org.bansang.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.java.Log;

@Log
public class LoginAfterInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private MemberService memberService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		HttpSession session = request.getSession();
		if (request.getMethod().equals("GET")) {
			return;
		}

		String auto = request.getParameter("auto"); // 자동 로그인 여부 변수

		Map<String, Object> map = modelAndView.getModel(); // model을 가져온다.

		if (map.get("userSession") != null) { // 로그인 성공했다면...

			request.getSession().setAttribute("userSession", map.get("userSession")); // session발급
			
			
			MemberDTO dto = (MemberDTO) map.get("userSession");
			if (auto != null) { // 자동 로그인 체크 유무 (쿠키 발급 유무)
				Cookie loginCookie = new Cookie("userCookie", session.getId());
				int amount = 12 * 60 * 60; // 60초(1분) -> 60번(1시간)-> 12번(12시간) 지속 (브라우저 껐다 켜도 지속)
				loginCookie.setMaxAge(amount); 
				response.addCookie(loginCookie);
				
				Date sessionLimit = new Date(System.currentTimeMillis()+(1000 * amount));
				memberService.keepLogin(dto.getMemberId(), session.getId(), sessionLimit);
				
			}
			
			
			Object dest = session.getAttribute("dest");
				
			if(dest != null) {
				if(dto.getAdmin().equals("y") && ((String)dest).equals("/bansang/storeManagement")) {
					log.info("관리자가 가려던 곳...");
					response.sendRedirect((String)dest);
					return;
				}else if(dto.getAdmin().equals("n") && ((String)dest).equals("/bansang/group")) {
					log.info("일반 사용자가 가려던 곳...");
					response.sendRedirect((String)dest);
					return;
				}
			}
			if(dto.getAdmin().equals("y")) {
				log.info("관리자가 가야할 곳...");
				response.sendRedirect("/bansang/storeManagement");
				return;
			}else {
				log.info("일반사용자가 가려할 곳...");
				response.sendRedirect("/bansang/group");
				return;
			}
			
		}

		return;
	}

}
