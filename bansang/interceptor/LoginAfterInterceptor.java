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

		String auto = request.getParameter("auto"); // �ڵ� �α��� ���� ����

		Map<String, Object> map = modelAndView.getModel(); // model�� �����´�.

		if (map.get("userSession") != null) { // �α��� �����ߴٸ�...

			request.getSession().setAttribute("userSession", map.get("userSession")); // session�߱�
			
			
			MemberDTO dto = (MemberDTO) map.get("userSession");
			if (auto != null) { // �ڵ� �α��� üũ ���� (��Ű �߱� ����)
				Cookie loginCookie = new Cookie("userCookie", session.getId());
				int amount = 12 * 60 * 60; // 60��(1��) -> 60��(1�ð�)-> 12��(12�ð�) ���� (������ ���� �ѵ� ����)
				loginCookie.setMaxAge(amount); 
				response.addCookie(loginCookie);
				
				Date sessionLimit = new Date(System.currentTimeMillis()+(1000 * amount));
				memberService.keepLogin(dto.getMemberId(), session.getId(), sessionLimit);
				
			}
			
			
			Object dest = session.getAttribute("dest");
				
			if(dest != null) {
				if(dto.getAdmin().equals("y") && ((String)dest).equals("/bansang/storeManagement")) {
					log.info("�����ڰ� ������ ��...");
					response.sendRedirect((String)dest);
					return;
				}else if(dto.getAdmin().equals("n") && ((String)dest).equals("/bansang/group")) {
					log.info("�Ϲ� ����ڰ� ������ ��...");
					response.sendRedirect((String)dest);
					return;
				}
			}
			if(dto.getAdmin().equals("y")) {
				log.info("�����ڰ� ������ ��...");
				response.sendRedirect("/bansang/storeManagement");
				return;
			}else {
				log.info("�Ϲݻ���ڰ� ������ ��...");
				response.sendRedirect("/bansang/group");
				return;
			}
			
		}

		return;
	}

}
