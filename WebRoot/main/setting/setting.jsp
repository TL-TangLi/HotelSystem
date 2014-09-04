<%@ page language="java" import="java.util.*,com.hotel.entity.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>设置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="Shortcut Icon" href="../../ico/setting.ico"/>
	
	<script type="text/javascript">
		///将会作为各个子页面的 共用对象。
		var user = {name:"${sessionScope.user.userName}",psw:"${sessionScope.user.psw}",level:"${sessionScope.user.level}"};
	</script>
	<script type="text/javascript" src="./script/setting.js"></script>
	
  </head>
  
    <%-- <c:if test="${sessionScope.user.level <= 5}">
    	level : ${sessionScope.user.level};
    </c:if> 
    <br>
    <%
    	User user = (User)session.getAttribute("user");
    	out.print("" + user.userName + "<br>");
    	out.print("" + user.psw + "<br>");
     %> --%>
     
     <%
     	User user = (User)session.getAttribute("user");
		     
      %>
  	
  	<frameset cols="15%,85%" frameborder="yes" border="1px" bordercolor="#bbbbbb">
  		<frame src="settingNavigator.jsp" >
		<frameset rows="10%,90%" >
	  		 <frame name="name_settingTitleFrame" src="settingTitle.jsp" noresize="noresize" >
	  		 <frame name="name_settingContentFrame" src="settingDescription.jsp" >
		</frameset>
		
	</frameset>
     
    
</html>
