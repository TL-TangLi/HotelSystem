<%@page
	import="com.hotel.entity.*,com.hotel.staticdata.StaticData,java.text.DecimalFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="FormAndDialog.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>入住信息统计</title>
	
	<script src="${dynamicRes}/js/queryCheckIn.js" charset="UTF-8"></script>

 <style>
	tr.checkInInfo-item{
	    background:#AED0EA;
	}
	tr.over{
	    background:#4db2e6;
	}
	div.serchDiv{ font-size: 14px;color:#888888;}
	td{text-align: center;color:black;}
	
	h1.title{color:#444444;padding-bottom: 10px;}
</style>

</head>
  
  <body onload="BodyInitial()">
	  <h1 align="center" class="ui-widget-header title">入住信息统计</h1>
	  
	  <div  class="ui-widget-content serchDiv" >
			<s:form theme="simple">
			<table>
				<tr>
					<td>姓名：</td>
					<td><s:textfield id="guestName" name="guestName" ></s:textfield><button class="ignorButton" type="button"
							onclick="ignoreById('guestName')"></button></td>		
					<td>联系电话：</td>
					<td><s:textfield id="phone" name="phone" ></s:textfield><button class="ignorButton" type="button"
							onclick="ignoreById('phone')"></button></td>		
					<td>&nbsp;&nbsp;&nbsp; 入住时间段：</td>
					<td><s:textfield id="enterDateBegin_id" name="enterDateBegin"
							readonly="true"></s:textfield>
						<button id="enterDateBegin_ignore_button" type="button" class="ignorButton"
							onclick="ignoreById('enterDateBegin_id')"></button></td>
					<td>到</td>
					<td><s:textfield id="enterDateEnd_id" name="enterDateEnd"
							readonly="true"></s:textfield>
						<button id="enterDateEnd_ignore_button" type="button" class="ignorButton"
							onclick="ignoreById('enterDateEnd_id')"></button></td>
				</tr>
				<tr>		
					<td>房间号：</td>
					<td><s:textfield id="roomName" name="roomName" ></s:textfield><button class="ignorButton" type="button"
							onclick="ignoreById('roomName')"></button></td>		
					<td>说明：</td>
					<td><s:textfield id="remark" name="remark" ></s:textfield><button class="ignorButton" type="button"
							onclick="ignoreById('remark')"></button></td>		
					
					<td>&nbsp;&nbsp;&nbsp; 退房时间段：</td>
					<td><s:textfield id="outDateBegin_id" name="outDateBegin"
							readonly="true"></s:textfield>
						<button id="outDateBegin_ignore_button" type="button" class="ignorButton"
							onclick="ignoreById('outDateBegin_id')"></button></td>
					<td>到</td>
					<td><s:textfield id="outDateEnd_id" name="outDateEnd"
							readonly="true"></s:textfield>
						<button id="outDateEnd_ignore_button" type="button" class="ignorButton"
							onclick="ignoreById('outDateEnd_id')"></button></td> &nbsp;&nbsp;&nbsp;
				</tr>
			</table>
			<table align="center">
				<tr>
					<td>查找:</td>
					<td><s:submit type="button" id="query_submit"></s:submit></td>
					<c:if test="${sessionScope.user.level <= -1}">
						<td>删除所选:</td>
						<td>
							<button id="delButton" type="button" onclick="chkbox_del()"></button>
						</td>
					</c:if>
				</tr>
			</table>
		</s:form>
	</div>
	  <%
	  	int i = 0;
	  	int j = 0;
	   %>
	  <div id="accordion-resizer" class="ui-widget-content">
		  <div id="accordion">
		  	
		  	<h3 class="ui-widget-header">查找到<s:property value="listCheckInInfo.size()"></s:property>个</h3>
		  	<div>
		  		<table>
		  			<tr class="ui-widget-header">
				  		<th>序号</th>
				  		<th>房间号</th>
				  		<th>订单号</th>
				  		<th>姓名</th>
				  		<th>联系电话</th>
				  		<th>人数</th>
				  		
				  		<th>说明</th>
				  		<th>入住时间</th>
				  		<th>退房时间/过期日期</th>
				  		<th>小时房</th>
				  		
				  		<th>总入账</th>
				  		<th>原房费</th>
				  		<th>总消费</th>
				  		
				  		<th>选择</th>
		  			</tr>	
				 	<s:iterator var="checkInInfo" value="listCheckInInfo">
		  			 <tr class="checkInInfo-item ui-widget-content "  >	
					  	<td><%=++j %></td>
				  		<td class="roomId"><s:property value="#checkInInfo.rId"></s:property></td>
				  		<td><s:property value="#checkInInfo.orderId"></s:property></td>
				  		<td><s:property value="#checkInInfo.name"></s:property></td>
				  		<td><s:property value="#checkInInfo.phoneNumber"></s:property></td>
				  		<td><s:property value="#checkInInfo.numberPeople"></s:property></td>
				  		
				  		<td><s:property value="#checkInInfo.description"></s:property></td>
				  		<td><s:property value="#checkInInfo.enterTime"></s:property></td>
				  		<td><s:property value="#checkInInfo.outTime"></s:property></td>
				  		
				  		<td>
				  			<s:if test="%{#checkInInfo.isHourRoom}">
				  				是
				  			</s:if>
				  			<s:else>
				  				不是
				  			</s:else>
				  		</td>
				  		
				  		<td><s:property value="#checkInInfo.allAddBalance"></s:property></td>
				  		<td><s:property value="#checkInInfo.balance"></s:property></td>
				  		<td><s:property value="#checkInInfo.allConsume"></s:property></td>
				  		
			  			<s:if test="%{#checkInInfo.isCheckOut}">
			  			
			  				<c:choose>
			  				
							   <c:when test="${sessionScope.user.level <= 1}">
					  				<td class="del_tr"><input class="delInput" type="checkBox" name="delIds" 
					  				value=<s:property value="#checkInInfo.id"/> ></input></td>
							   </c:when>
							   
							   <c:otherwise>
							   		<td style="display:none" class="del_tr">
				  					<input class="delInput"  type="checkBox" value=<s:property value="#checkInInfo.id"/> ></input>
				  					<p class="checkOut">0</p>
					  				</td>
					  				<td>已退</td>
							   </c:otherwise>
							   
							</c:choose>
			  				
			  			</s:if>
			  			<s:else>
			  				<td style="display:none" class="del_tr">
			  					<input class="delInput"  type="checkBox" value=<s:property value="#checkInInfo.id"/> ></input>
			  					<p class="checkOut">0</p>
			  				</td>
			  				<td>未退</td>
			  			</s:else>
				  		
			  		</tr>
			   		</s:iterator>
			  	</table>
		  	</div>
		  
			</div>
		</div>




  </body>
</html>
