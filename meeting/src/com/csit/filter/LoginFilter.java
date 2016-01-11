package com.csit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.csit.entity.User;

/**
 * @Description:登录过滤器
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class LoginFilter implements Filter {
	protected FilterConfig filterConfig;
	public void destroy() {
		filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI().toString();
		String uninclude = filterConfig.getInitParameter("uninclude");
		String[] s = uninclude.split(";");
		for (String temp : s) {
			if (uri.endsWith(temp)) {
				chain.doFilter(request, response);
				return;
			}
		}
		if (uri.endsWith("User.do")) {
			String action = req.getParameter("action");
			if ("login".equals(action)) {
				chain.doFilter(request, response);
				return;
			}
		}
		// 对登录页和验证码页面不拦截
		if (uri.contains("login.html") || uri.contains("Img.do")) {
			chain.doFilter(request, response);
		} else {
			User user = (User) req.getSession().getAttribute(User.SESSION_LOGIN_USER);
			if (user == null) {
				String input = filterConfig.getInitParameter("input");
				resp.sendRedirect(input);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		filterConfig = fConfig;
	}
}
