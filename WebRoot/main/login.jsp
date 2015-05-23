<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>管理员登录</title>
<script src="../jquery/jquery-1.9.1.js"></script>
<script src="../jquery/jquery-ui-1.10.1.custom.js"></script>
<link rel="stylesheet" href="../jquery/jquery-ui.min.css" />
<link rel="stylesheet" href="../css/login.css">

<link rel="Shortcut Icon" href="../ico/login.ico" />

<script>
	$(function() {
		$("input[type=submit],button").button();
		$("input[type=text]").val('');
	});
</script>

</head>

<body>
	<br>
	<br>
	<br>
	<br>
	<h1>管理员登录</h1>
	<br>
	<br>
	<br>
	<br>
	<%-- <center>
		<s:form name="form1"  theme = "xhtml" action="loginAction" namespace="/main">
				<s:textfield name="userName" label="管理员名" />
				<s:password name="psw" label="输入密码" />
				<s:submit value="确认" />
		</s:form>
	<FONT color="red"><s:actionerror/></FONT>
	</center> --%>


	<center>
		<s:form name="form1" theme="simple" method="post" action="loginAction"
			namespace="/main">
			<table>
				<tr>
					<td>管理员名:</td>
					<td><input style="display:none"/><s:textfield name="userName" value=""/></td>
				</tr>
				<tr>
					<td><s:fielderror cssStyle="font-size:14px;color:red">
							<s:param>userName</s:param>
						</s:fielderror></td>
				</tr>
				<tr>
					<td>输入密码:</td>
					<td><input type='password' style="display:none"/><s:password name="psw" value=""/></td>
				</tr>
				<tr>
					<td><s:fielderror cssStyle="font-size:14px;color:red">
							<s:param>psw</s:param>
						</s:fielderror></td>
				</tr>
				<tr>
					<td></td>
					<td align="center"><s:submit value="确认" /></td>
				</tr>
			</table>
		</s:form>
		<%-- <s:fielderror cssStyle="font-size:14px;color:red"></s:fielderror> --%>
		<FONT color="red"><s:actionerror /></FONT>
	</center>

</body>
</html>





