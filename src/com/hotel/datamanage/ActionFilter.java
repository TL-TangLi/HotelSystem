package com.hotel.datamanage;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class ActionFilter extends StrutsPrepareAndExecuteFilter
{

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException
	{

//		 HttpServletRequest request = (HttpServletRequest)arg0;
//		 
//		    
//	    HttpServletResponse response = (HttpServletResponse)arg1;
//	    
//	    HttpSession session = request.getSession();
//	    
//	    String uri = request.getRequestURI();
//	    
//	    //对所有 非 LoginAction.action 的action 进行 检测 登录
//	    if(uri.indexOf("loginAction.action") ==-1 && uri.indexOf(".action") !=-1)
//	    {
//	    	if(session.getAttribute("user") == null){
//		    	response.sendRedirect("../main/login.jsp");
//		    	return;
//		    }
//	    }
		
		super.doFilter(arg0, arg1, arg2);
	}
	
}

	
	
	