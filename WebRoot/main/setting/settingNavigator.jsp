<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<style>
		body {background-image: url("${dynamicRes}/image/eg_bg_06.gif"); }
		.header {color: #888888;font: bold;font-size:18pt; padding: 10pt;font-weight: bold;}
		li {list-style-type: none;text-align: left; padding-left: 5pt;padding-top: 8pt;margin: 3pt;font-weight: lighter;}
		li a{font-size: 15pt;color: #333333;font:bold;font: cursive;padding: 4pt; text-decoration: none;}
		li a:HOVER{color: #990000;}
		li a:ACTIVE {color:#ff0000;}
	</style>
	
	<script type="text/javascript" src="../../jquery/jquery-1.9.1.js"></script>
	<script>
		
		
		function initial(){
			$("#id_navigator_description").css("text-decoration","underline");
		}
		
		function navigatorClick(i){
			underLineHide();
			var title  ;
			switch(i)
			{
			case 1:
				title = "密码设置";	
				$("#id_navigator_psw").css("text-decoration","underline");
				break;
			case 2:
				title ="账户管理";
				$("#id_navigator_account").css("text-decoration","underline");
				
				var form = document.createElement("Form");
				form.target = "name_settingContentFrame";
				form.action="main/queryAllAccountActionForSetting.action";
				form.submit();
				
				break;
			case 3:
				title = "系统设置";
				$("#id_navigator_system").css("text-decoration","underline");
				break;
			case 4:
				title = "系统说明";
				$("#id_navigator_description").css("text-decoration","underline");
				break;
			}
			 parent.frames["name_settingTitleFrame"].document.getElementById("settingTitle_id").innerHTML = title;
		};
		
		
		function underLineHide(){
			$("#id_navigator_psw").css("text-decoration","none");
			$("#id_navigator_account").css("text-decoration","none");
			$("#id_navigator_system").css("text-decoration","none");
			$("#id_navigator_description").css("text-decoration","none");
		}		
		
	</script>

  </head>
  
  <body onload="initial()">
  
  	<p class="header">酒店管理系统</p>
  
  	<ul>
  		<li><a id="id_navigator_description" href="settingDescription.jsp" target="name_settingContentFrame" onclick="navigatorClick(4)" id = "test">系统说明</a></li>
  		<li><a id="id_navigator_psw" href="settingPsw.jsp" target="name_settingContentFrame" onclick="navigatorClick(1)" id = "test">密码设置</a></li>
  		<li><a id="id_navigator_account" target="name_settingContentFrame" onclick="navigatorClick(2)"  >账户管理</a></li>
  		<li><a id="id_navigator_system" href="settingSystem.jsp" target="name_settingContentFrame" onclick="navigatorClick(3)"  >系统设置</a></li>
  	</ul>
  	
  
  </body>
  
</html>
