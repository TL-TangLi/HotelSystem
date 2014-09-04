<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <sx:head parseContent="false"/>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script src="../../../jquery/jquery-1.9.1.js"></script>
	<script src="../../../jquery/jquery-ui-1.10.1.custom.js"></script>
	<script src="../../../jquery/jquery.ui.datepicker-zh-TW.js"></script>
	
	<link rel="stylesheet" href="../../../jquery/jquery-ui.css" />
	<link rel="stylesheet" href="../../../jquery/jquery-ui.min.css" />
	<link rel="stylesheet" href="../../../css/home.css">
	
	<style type="text/css">
		table { border-style: solid;border-color: #1f85d7;border-width: 2pt;}
		tr{ padding: 2pt;background: #d0dae3;}
		
		tr.even{background:#9C6;}
		tr.odd {  background:#98d3e5;}
		tr.on	{ background:#FC6;}
		
	</style>
	
	<script type="text/javascript">
		$(function(){
			$('.table_tr').hover(
	        function(){
	            $(this).addClass('on');
	        },
	        function(){
	            $(this).removeClass('on');
        	});
		});
		
		
		function modifyAccount(name){
		
			alert(name);
		};
		
	</script>

  </head>
  
  <body >
  
  	<h3>当前账户列表</h3>
  	<table >
  		<tr>
  			<th>序号</th>
  			<th>用户名</th>
  			<th>创建日期</th>
  			<th>账户等级</th>
  		</tr>
  		
  		<s:iterator  var="user"  status="status" value="users">
  			<s:if test="#status.odd">
	  			<tr class="table_tr odd" ondblclick="modifyAccount('<s:property value="#user.userName"/>')">
	  				<td><s:property value="#status.count"/></td>
	  				<td><s:property value="#user.userName"/></td>
	  				<td><s:property value="#user.dateTime"/></td>
	  				<td><s:property value="#user.level"/></td>
	  			</tr>
  			</s:if>
  			<s:else>
  				<tr class="table_tr even" ondblclick="modifyAccount('<s:property value="#user.userName"/>')">
  					<td><s:property value="#status.count"/></td>
	  				<td><s:property value="#user.userName"/></td>
	  				<td><s:property value="#user.dateTime"/></td>
	  				<td><s:property value="#user.level"/></td>
  				</tr>
  			</s:else>
  		</s:iterator>
  		
  	</table>
  	
  	
  </body>
</html>
