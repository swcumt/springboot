package com.baipengx.coin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.baipengx.coin.model.WechatUserInfo;
import com.baipengx.coin.vo.ResponseVo;
import com.baipengx.common.BaseLogger;

public class BaseController extends BaseLogger{
	protected ResponseVo vo = new ResponseVo();
	protected ModelAndView mav = new ModelAndView();
	
	protected static final String SESSION_USER_KEY = "user";
	
	/**
	 * 
	 * @param response
	 * @param content
	 */
	protected void write(HttpServletResponse response, String content) {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(content);
		}
		catch (IOException e) {
			log.error(e.getMessage(), e);
		}finally {
			if(out != null){
				out.close();
			}
		}
	}
	
	/**
	 * 获取 根目录 eg: http://localhost/wechat
	 * @param request
	 * @return
	 */
	protected String getRootPath(HttpServletRequest request){
		String requestUrl = request.getRequestURL().toString();
		String contextPath = "".equals(request.getContextPath())||"/".equals(request.getContextPath())? request.getRequestURI():request.getContextPath();
		String root = requestUrl.substring(0, requestUrl.indexOf(contextPath) + contextPath.length());
		
		return root;
	}
	
	
	/**
	 * 
	 * @param response
	 */
	protected void sendRedirect(HttpServletResponse response, String uri){
		try {
			response.sendRedirect(uri);
		}
		catch (IOException e) {
			log.error("send redirect {} error {}", uri, e);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param key
	 * @param value
	 */
	protected void saveSession(HttpServletRequest request, String key, Object value){
		request.getSession().setAttribute(key, value);
	}
	
	
	/**
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	protected Object getSession(HttpServletRequest request, String key){
		return request.getSession().getAttribute(key);
	}
	
	/**
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	protected Object getSessionAndRemove(HttpServletRequest request, String key){
		Object value = getSession(request, key);
		request.getSession().removeAttribute(key);
		return value;
	}
	/**
	 * 是否 已进行 微信授权
	 * @param request
	 * @return
	 */
	protected boolean isAuthed(HttpServletRequest request){
		return getCurrentAuthed(request) != null;
	}
	
	/**
	 * 获取当前授权的微信用户信息
	 * @param request
	 * @return
	 */
	protected WechatUserInfo getCurrentAuthed(HttpServletRequest request){
		return (WechatUserInfo) getSession(request, SESSION_USER_KEY);
	}
	
}
