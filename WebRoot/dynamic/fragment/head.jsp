<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="staticRes" value="${pageContext.request.contextPath}/staticRes"/>
<c:set var="dynamicRes" value="${pageContext.request.contextPath}/dynamic"/>
<c:set var="basePath" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
<html>    
<head>
	
	<link rel="stylesheet" href="${staticRes}/jquery/jquery-ui.min.css" />
	<link rel="stylesheet" href="${dynamicRes}/css/home.css">
	
	<script src="${staticRes}/jquery/jquery-1.9.1.js"></script>
	<script src="${staticRes}/jquery/jquery-ui-1.10.1.custom.js"></script>
	<script src="${staticRes}/jquery/jquery.ui.datepicker-zh-TW.js"></script>
	
	<script type="text/javascript" src="${dynamicRes}/js/common/common.js"></script>
</head>

<body>

	<div id="msgDialog"></div>
</body>

</html>