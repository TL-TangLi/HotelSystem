<%@page
	import="com.hotel.entity.*,com.hotel.staticdata.StaticData,java.text.DecimalFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="FormAndDialog.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单查询</title>

	<link rel="stylesheet" href="../jquery/redmond/jquery-ui.css" />
	<link rel="stylesheet" href="../jquery/redmond/jquery-ui.min.css" />
	<link rel="stylesheet" href="../css/home.css">
	
	
	<script src="../jquery/jquery-1.9.1.js"></script>
	<script src="../jquery/jquery-ui-1.10.1.custom.js"></script>
	<script src="../jquery/jquery.ui.datepicker-zh-TW.js"></script>
	
	<script src="../script/homeOrder.js" charset="UTF-8"></script>
	<script src="../script/home.js" charset="UTF-8"></script>
	<script src="../script/homeMenu.js" charset="UTF-8"></script>

	<script src="../script/queryOrder.js" charset="UTF-8"></script>

 <style>
	tr.order-item{
	    background:#AED0EA;
	}
	tr.over{
	    background:#4db2e6;
	}
	tr.firstLine{display:none;}
	div.serchDiv{ font-size: 14px;color:#555555;}
	td{text-align: center;}
	h1.title{padding-bottom: 10px};
</style>

</head>
  
  <body onload="BodyInitial()">
	  <h1 align="center" class="ui-widget-header title">订单查询</h1>
	  
	  <div  class="ui-widget-content serchDiv" >
		 <s:form theme="simple">
			订单类型筛选：
			<s:select 
			   id = "info_id"
		       name="info"
		       list="#{'3':'所有','0':'未入住订单', '1':'已入住订单','2': '放弃入住订单'}"
		       required="true"
			/>
			入住时间：<s:textfield id ="enterDate_id" name = "enterDate" readonly="true"  ></s:textfield>
			<button id="enterDate_ignore_button" type = "button" onclick="ignoreEnterDate()"></button>&nbsp;&nbsp;&nbsp;
			预定时间：<s:textfield id ="orderTime_id" name = "OrderTime" readonly="true" ></s:textfield>
			<button id="orderTime_ignore_button" type="button" onclick="ignoreOrderTime()"></button>
			
			查找:<s:submit  type="button" id="orderQuery_submit"></s:submit>
			
			删除所选:<button id="delOrderButton" type="button" onclick="chkbox_del()"></button>
		 </s:form> 
	  </div>
	  <%
	  	int i = 0;
	  	int j = 0;
	   %>
	  <div id="accordion-resizer" class="ui-widget-content">
		  <form action="queryOrderAction" >
		  <div id="accordion">
		  	<s:iterator var="listOrder" value="listListOrder" >
		  	
		  	<h3 class="ui-widget-header">
		  		<s:property value="listRoomPrice.{? #this.id == #listOrder.get(0).roomType}.get(0).description"/>
		  		+<s:property value="#listOrder.size()-1"/>
		  	</h3>
		  	<div>
		  		<table>
		  			<tr class="ui-widget-header ">
				  		<th>序号</th>
				  		<th>订单号</th>
				  		<th>预订人姓名</th>
				  		<th>联系电话</th>
				  		<th>预定说明</th>
				  		<th>预定时间</th>
				  		<th>预定天数</th>
				  		<th>入住日期</th>
				  		<th>订单延时</th>
				  		<th>信息</th>
				  		<th>选择</th>
		  			</tr>	
				 	<s:iterator var="order" value="#listOrder">
		  			 <tr class="order-item ui-widget-content <%if(j==0){out.print("firstLine");} %>"  >	
					  	<td><%=j %></td>
				  		<td class="orderId"><s:property value="#order.id"></s:property></td>
				  		<td><s:property value="#order.name"></s:property></td>
				  		<td><s:property value="#order.phoneNumber"></s:property></td>
				  		<td><s:property value="#order.description"></s:property></td>
				  		<td><s:property value="#order.orderTime"></s:property></td>
				  		
				  		<td><s:property value="#order.days"></s:property></td>
				  		<td><s:property value="#order.enterDate"></s:property></td>
				  		<td><s:property value="#order.extendHour"></s:property></td>
				  		
				  		
				  		<td>
				  			<s:if test="%{#order.info == 0}">
				  				未入住
				  			</s:if>
				  			<s:elseif test="%{#order.info == 1}">
				  				已入住
				  			</s:elseif>
				  			<s:else >
				  				放弃入住
				  			</s:else>
				  		</td>
				  		<td class="del_tr"><input class="delInput" type="checkBox" name="delIds" 
				  			value=<s:property value="#order.id"/> ></input></td>
			  		</tr>
			  		<%j++; %>
			   		</s:iterator>
			  	</table>
		  	</div>
		  	<%i++; j=0; %>
		 	 </s:iterator>
		  
			</div>
			 </form>
		</div>




		<!-- ////////提示对话框infoDialog 会刷新//////// -->
		<div id="infoDialog" style="display:none">
			<center>
				<s:form theme="simple" action="queryOrderAction" namespace="/main" >
					<table class= "ui-widget-content" align="center">
						<tr><td><s:label id="info"></s:label></td></tr>			
						<tr><td><s:submit value="确认并刷新"/></td></tr>
					</table>
				</s:form>
			</center>
		</div>
	
		



  </body>
</html>
