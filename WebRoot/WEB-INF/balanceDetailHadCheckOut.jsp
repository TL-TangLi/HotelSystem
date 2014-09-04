<%@page import="com.hotel.entity.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<%@include file="FormAndDialog.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>账单详情</title>

	<link rel="stylesheet" href="../jquery/pepper-grinder/jquery-ui.css" />
	<link rel="stylesheet" href="../jquery/pepper-grinder/jquery-ui.min.css" />
	
	
	<script src="../jquery/jquery-1.9.1.js"></script>
	<script src="../jquery/jquery-ui-1.10.1.custom.js"></script>
	<script src="../jquery/jquery.ui.datepicker-zh-TW.js"></script>
	
	<script src="../script/home.js" charset="UTF-8"></script>
	<script src="../script/balanceDetailHadCheckOut.js" charset="UTF-8"></script>


 <style>
	tr.account-item{
	    background:#aaaaaa;
	}
	tr.over{
	    background:#999999;
	}
	td{text-align: center;}
	div.serchDiv{ font-size: 14px;color:#666666; font-family:cursive;}
	div.description{ font-size:14;}
	p{color:#666666; font-family:cursive; }
	h1.title{color:#666666; font-family:cursive;}
</style>

	
</head>
  
  <body >
	  <h1 align="center" class="ui-widget-header title">已退房账单详情</h1>
	  
	  <div id ="description_div" class="ui-widget-header description" >
	  	<p><s:property value="cif.enterTime"/>入住，<s:property value="cif.outTime"/>退房，
	  	所住房间：<s:property value="cif.rId"/>，入住人数：<s:property value="cif.numberPeople"/>，联系人：<s:property value="cif.name"/>
	  	，联系电话：<s:property value="cif.phoneNumber"/>，入住说明：<s:property value="cif.description"/>
	  	<br> 房费：<s:property value="cif.balance"/>，消费总额：<s:property value="cif.allConsume"/>，总入账：<s:property value="cif.allAddBalance"/></p>
	  </div>
	  
	  
	  
	  <div  class="ui-widget-header serchDiv" >
		 <s:form theme="simple">
			<s:textfield id ="checkId_id" name = "checkId"  cssStyle="display:none"></s:textfield>			
			&ensp;&ensp;&ensp;类型：
			<s:select 
				id="type_id"
		       name="type"
		       list="#{'-1':'所有','0':'银行卡', '1':'现金'}"
		       required="true"
			/>
			日期段：
			<s:textfield id ="beginDate_id" name = "beginDate" readonly="true"  ></s:textfield>
			<button id="beginDate_ignore_button" type = "button" onclick="ignoreDate(1)"></button>&nbsp;到
			
			<s:textfield id ="endDate_id" name = "endDate" readonly="true"  ></s:textfield>
			<button id="endDate_ignore_button" type = "button" onclick="ignoreDate(2)"></button>&nbsp;&nbsp;&nbsp;
			
			查找:<s:submit  type="button" id="queryBalance_submit" ></s:submit>&nbsp;&nbsp;&nbsp;
			
			<c:if test="${sessionScope.user.level <= -1}">
				删除所选<button id="delAccounts" type = "button" onclick="chkbox_del()"></button>
			</c:if>
			
		 </s:form> 
	  </div>
	  
	  
	  
	  
	  <%
	  	int j = 0;
	  	String type;
	   %>
	   
	  	<!-- //////////////////////////////////////////////入账统计/////////////////////////////////////////////////// -->
	  <div  class="ui-widget-content">
		  <div class="accordion">
		  
		  
		  	<h3 class="ui-widget-header">入账<s:property value='addBalanceSum'/></h3>
		  	<div>
		  		<table class= "ui-widget-content">
		  			<tr class="ui-widget-header ">
				  		<th>序号</th>
				  		<th>房号</th>
				  		<th>入账说明</th>
				  		<th>入账方式</th>
				  		<th>入账金额</th>
				  		<th>入账时间</th>
				  		<c:if test="${sessionScope.user.level <= 1}">
					  		<th>选择</th>
						</c:if>
		  			</tr>	
				 	<s:iterator var="account" value="listListAccount.get(0)">
			  		<%j++; %>
				 	
		  			 <tr class="account-item ui-widget-content "  >	
					  	<td><%=j %></td>
					  	<td><s:property value="#account.rId"/></td>
					  	<td><s:property value="#account.description"/></td>
						<td><s:if test='#account.type == 1'>现金</s:if><s:else>银行卡</s:else></td>						  	
					  	<td><s:property value="#account.balance"/></td>
					  	<td><s:property value="#account.genTime"/></td>
					  	
					  	<c:if test="${sessionScope.user.level <= 1}">
						  	<td class="del_tr">
						  		<input  type="checkBox" name="delIds" value=<s:property value="#account.id"></s:property>></input>
					  		</td>
						</c:if>
			  		</tr>
			   		</s:iterator>
			  	</table>
			  	
		  	</div>
			
			
		</div>
	</div>
	
	
	 <!-- //////////////////////////////////////////////房费统计/////////////////////////////////////////////////// -->
	  <div  class="ui-widget-content">
		  <div class="accordion">
			
			 <% j=0;%>
		  	<h3 class="ui-widget-header">房费<s:property value='roomChargeSum'/></h3>
		  	<div>
		  		<table class= "ui-widget-content">
		  			<tr class="ui-widget-header ">
				  		<th>序号</th>
				  		<th>房号</th>
				  		<th>房费类型</th>
				  		<th>房费产生日期</th>
				  		<th>房费</th>
				  		<c:if test="${sessionScope.user.level <= 1}">
					  		<th>选择</th>
						</c:if>
		  			</tr>	
				 	<s:iterator var="account" value="listRoomCharge">
			  		<%j++; %>
				 	
		  			 <tr class="account-item ui-widget-content "  >	
					  	<td><%=j %></td>
					  	<td><s:property value="#account.rId"/></td>
					  	
					  	<s:if test="#account.type == 1">
					  		<td>全天费用</td>
					  	</s:if>
					  	<s:elseif test="#account.type == 2">
					  		<td>半天费用</td>
					  	</s:elseif>
					  	<s:else>
					  		<td>小时费用</td>
					  	</s:else>
					  	
					  	<td><s:property value="#account.date"/></td>
					  	<td><s:property value="#account.charge"/></td>
					  	
					  	
					  	<c:if test="${sessionScope.user.level <= 1}">
						  	<td class="del_tr">
						  		<input  type="checkBox" name="delChargeIds" value=<s:property value="#account.id"></s:property>></input>
					  		</td>
						</c:if>
					  	
			  		</tr>
			   		</s:iterator>
			  	</table>
		  	</div>
			
		</div>
	</div>
	
	
	 <!-- //////////////////////////////////////////////额外消费/////////////////////////////////////////////////// -->
	  <div  class="ui-widget-content">
		  <div class="accordion">
		  
			<% j=0;%>
		  	<h3 class="ui-widget-header">额外消费<s:property value='consumeSum'/></h3>
		  	<div>
		  		<table class= "ui-widget-content">
		  			<tr class="ui-widget-header ">
				  		<th>序号</th>
				  		<th>房号</th>
				  		<th>消费说明</th>
				  		<th>消费金额</th>
				  		<th>消费时间</th>
				  		<c:if test="${sessionScope.user.level <= 1}">
					  		<th>选择</th>
						</c:if>
		  			</tr>	
				 	<s:iterator var="account" value="listListAccount.get(1)">
			  		<%j++; %>
				 	
		  			 <tr class="account-item ui-widget-content "  >	
					  	<td><%=j %></td>
					  	<td><s:property value="#account.rId"/></td>
					  	<td><s:property value="#account.description"/></td>
					  	<td><s:property value="#account.balance"/></td>
					  	<td><s:property value="#account.genTime"/></td>
					  	
					  	
					  	<c:if test="${sessionScope.user.level <= 1}">
						  	<td class="del_tr">
						  		<input  type="checkBox" name="delIds" value=<s:property value="#account.id"></s:property>></input>
					  		</td>
						</c:if>
			  		</tr>
			   		</s:iterator>
			  	</table>
		  	</div>
		  	
		</div>
	</div>
		
		
		

  </body>
</html>
