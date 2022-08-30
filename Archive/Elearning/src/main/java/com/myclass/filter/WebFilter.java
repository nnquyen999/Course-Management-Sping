package com.myclass.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.myclass.dto.SessionDto;

@Component
public class WebFilter implements Filter {

	private SessionDto sessionDto;

	@Value("${adress.foruser}")
	private String[] urlForUser;

	@Value("${adress.notforteacher}")
	private String[] urlNotForTeachers;

	@Value("${adress.home}")
	private String homeAdress;

	@Value("${file.upload-result}")
	private String uploadAdress;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		sessionDto = (SessionDto) req.getSession().getAttribute("sessionDto");
		
		if (isAssetsOrErrorOrLogout(req)) {			
			chain.doFilter(request, response);
		} 
		//CHECK if u not login
		else if (sessionDto == null) {
			if(req.getServletPath().equals("/login")) {
				chain.doFilter(request, response);
			}else {
				resp.sendRedirect(req.getContextPath() + "/login");
			}
			
		} 
		
		//CHECK ROLE USER
		else if (sessionDto.getRole().equals("USER")) {
			if (checkUrlForUser(req.getServletPath())) {
				chain.doFilter(request, response);
			} else if (req.getServletPath().equals("/login")) {
				resp.sendRedirect(req.getContextPath() + homeAdress);
			} else if (req.getServletPath().equals("/user/password")) {

				if (req.getMethod().equals("POST")) {
					chain.doFilter(request, response);
				} else if (checkIdFromRequest(req,sessionDto.getId()))
					chain.doFilter(request, response);
				else
					resp.sendRedirect(req.getContextPath() + "/403");
			} else {
				resp.sendRedirect(req.getContextPath() + "/403");
			}
		} 
		
		//CHECK ROLE TEACHER
		else if (sessionDto.getRole().equals("TEACHER")) {
			if (checkUrlForTeacher(req.getServletPath())) {
				chain.doFilter(request, response);
			} else if (req.getServletPath().equals("/login")) {
				resp.sendRedirect(req.getContextPath() + homeAdress);
			}else if (req.getServletPath().equals("/user/password")) {

				if (req.getMethod().equals("POST")) {
					chain.doFilter(request, response);
				} else if (checkIdFromRequest(req,sessionDto.getId()))
					chain.doFilter(request, response);
				else
					resp.sendRedirect(req.getContextPath() + "/401");
			} else {
				resp.sendRedirect(req.getContextPath() + "/403");
			}
		} 
		
		//CHECK ROLE ADMIN
		else if (sessionDto.getRole().equals("ADMIN")) { // ADMIN cho vào hết
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect(req.getContextPath() + homeAdress);
		}

	}

//-----------------========Private method=========-------------------------
	private boolean checkUrlForUser(String url) {
		for (String item : urlForUser) {
			if (url.equals(item) || url.startsWith(uploadAdress)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkUrlForTeacher(String url) {
		for (String item : urlNotForTeachers) {
			if (url.equals(item)) {
				return false;
			}
		}
		return true;
	}

	private boolean checkIdFromRequest(HttpServletRequest req, int idUser) {
		String idRequest = req.getQueryString().replace("id=", "");
		if (Integer.parseInt(idRequest) == idUser)
			return true;
		else
			return false;
	}
	private boolean isAssetsOrErrorOrLogout(HttpServletRequest req){
		if(req.getServletPath().startsWith("/assets") 
				|| req.getServletPath().startsWith("/40")
				|| req.getServletPath().equals("/logout"))
			return true;
		else
			return false;
	}
}