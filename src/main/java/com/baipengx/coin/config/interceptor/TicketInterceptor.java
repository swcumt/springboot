package com.baipengx.coin.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.baipengx.coin.controller.BaseController;
import com.baipengx.coin.model.ActivityUser;
import com.baipengx.coin.service.ActivityUserService;

@Component
public class TicketInterceptor extends BaseController implements HandlerInterceptor {
	@Autowired
	private ActivityUserService auService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		ActivityUser au = auService.findPayed(getCurrentAuthed(request).getId());
		if (au != null) {
			if (!request.getRequestURI().endsWith("info")) {
				sendRedirect(response, "./info");
				return false;
			}

			return true;
		}
		else if (!request.getRequestURI().endsWith("buy")) {
			sendRedirect(response, "./buy");
			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
