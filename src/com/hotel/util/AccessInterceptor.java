package com.hotel.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AccessInterceptor extends AbstractInterceptor
{

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception
	{

		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(ServletActionContext.HTTP_RESPONSE);

		String reqUrl = request.getServletPath().substring(1);
		String queryString = request.getQueryString();

		// /显示访问路径 以及执行函数
		if (queryString == null)
		{
			System.out.println("++++++++++请求地址：" + reqUrl + " ===> " + invocation.getAction().getClass());
		}
		else
		{
			System.out.println("++++++++++请求地址：" + reqUrl + "?" + queryString + " ===> " + invocation.getAction().getClass());
		}

		// 拦截开关
		if (Constant.OPEN_ACCESS_INTERCEPTOR == false)
		{
			return invocation.invoke();
		}
		// // 过滤不需要的拦截
		for (String url : Constant.EXCLUDE_INTERCEPTOR)
		{
			if (reqUrl.startsWith(url))
			{
				return invocation.invoke();
			}
		}

		String isAjax = request.getHeader("X-Requested-With");
		if (isAjax != null)
		{
			System.out.println("XXXXXXXXXX：请求将会被拦截,是ajax访问");
		}
		else
		{
			System.out.println("XXXXXXXXXX：请求将会被拦截,是普通访问");
		}

		// //如果登录超时
		if (ActionContext.getContext().getSession().get("user") == null)
		{
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("{\"result\":"+Constant.FAILED+",\"message\":\"登录超时，请重新登录\"}");
//			// 是ajax访问
//			if (null != isAjax && isAjax.equalsIgnoreCase("xmlhttprequest"))
//			{
//				response.setCharacterEncoding("UTF-8");
//				response.getWriter().print("{\"result\":"+Constant.FAILED+",\"message\":\"登录超时，请重新登录\"}");
//
//			}
//			// 不是ajax定位到登录页面
//			else
//			{
//				response.sendRedirect("/"+Constant.PROJECT_NAME+"/main/login.jsp");
//			}
			return null;
		}

		// 先 action ,后进入 interceptor
		return invocation.invoke();
	}

}
