<%@page import="com.hotel.entity.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<%@include file="FormAndDialog.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>账目统计</title>

	<link rel="stylesheet" href="${staticRes }/jquery/pepper-grinder/jquery-ui.css" />
	<link rel="stylesheet" href="${staticRes }/jquery/pepper-grinder/jquery-ui.min.css" />
	<script src="../script/home.js" charset="UTF-8"></script>
	<script src="../script/queryAccountSum.js" charset="UTF-8"></script>


 <style>
	tr.accountAndCheckIn-item{
	    background:#aaaaaa;
	}
	tr.over{
	    background:#999999;
	}
	div.serchDiv{ font-size: 14px;color:#666666; font-family:cursive;}
	div.description{ font-size: 14px;}
	p{color:#666666; font-family:cursive; }
	h1.title{color:#666666; font-family:cursive;}
</style>

	
</head>
  
  <body >
	  <h1 align="center" class="ui-widget-header title">账目统计</h1>
	  
	  <div id ="description_div" class="ui-widget-header ui-state-hover description" >
	  	
	  </div>
	  
	  
	  
	  <div  class="ui-widget-header serchDiv" >
		 <s:form theme="simple">
			
			&ensp;&ensp;&ensp;类型：
			<s:select 
				id = "type_id"
		       name="type"
		       list="#{'-1':'所有','0':'银行卡', '1':'现金','2':'网络费用','3':'积分兑换'}"
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
	   
	   <!-- /////////////////////////////////////////////入账统计/////////////////////////////////////////////////// -->
	  <div  class="ui-widget-content">
		  <div class="accordion">
		  
		  	<h3 class="ui-widget-header">入账<s:property value='addBalanceSum'/></h3>
		  	<div>
		  		<table class= "ui-widget-content">
		  			<tr class="ui-widget-header ">
				  		<th>序号</th>
				  		<th>入账方式</th>
				  		<th>入账金额</th>
				  		<th>入账时间</th>
				  		<th>入账说明</th>
				  		
				  		<th>房间号</th>
				  		<th>联系人</th>
				  		<th>联系电话</th>
				  		<th>入住人数</th>
				  		<th>入住说明</th>
				  		<th>是否退房</th>
				  		<th>是否为小时房</th>
				  		<c:if test="${sessionScope.user.level <= 1}">
					  		<th>选择</th>
						</c:if>
				  		
		  			</tr>	
				 	<s:iterator var="accountAndCheckIn" value="listListAccountAndCheckIn.get(0)">
			  		<%j++; %>
				 	
		  			 <tr class="accountAndCheckIn-item ui-widget-content "  >	
					  	<td><%=j %></td>
						<td><s:if test='#accountAndCheckIn.account.type== 0'>银行卡</s:if>
							<s:elseif test='#accountAndCheckIn.account.type == 1'>现金</s:elseif>
							<s:elseif test='#accountAndCheckIn.account.type == 2'>网络费用</s:elseif>
							<s:elseif test='#accountAndCheckIn.account.type == 3'>积分兑换</s:elseif>
						</td>										  	
					  	<td><s:property value="#accountAndCheckIn.account.balance"/></td>
					  	<td><s:property value="#accountAndCheckIn.account.genTime"/></td>
					  	<td><s:property value="#accountAndCheckIn.account.description"/></td>
					  	
					  	
					  	<s:if test = "#accountAndCheckIn.cif == null">
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
					  	</s:if>
					  	<s:else>
					  		<td><s:property value="#accountAndCheckIn.cif.rId"/></td>
					  		<td><s:property value="#accountAndCheckIn.cif.name"/></td>
					  		<td><s:property value="#accountAndCheckIn.cif.phoneNumber"/></td>
					  		<td><s:property value="#accountAndCheckIn.cif.numberPeople"/></td>
					  		<td><s:property value="#accountAndCheckIn.cif.description"/></td>
					  		<td>
					  			<s:if test = "#accountAndCheckIn.cif.isCheckOut">
					  			已退
						  		</s:if>
						  		<s:else>
						  			未退
						  		</s:else>
						  	</td>
					  		<td>
					  			<s:if test = "#accountAndCheckIn.cif.isHourRoom">
					  			是
						  		</s:if>
						  		<s:else>
						  			不是
						  		</s:else>
						  	</td>
					  	</s:else>
					  	<c:if test="${sessionScope.user.level <= 1}">
						  	<td class="del_tr">
						  		<input  type="checkBox" name="delIds" value=<s:property value="#accountAndCheckIn.account.id"></s:property>></input>
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
		  	<h3 class="ui-widget-header">房费统计<s:property value='roomChargeSum'/></h3>
		  	<div>
		  		<table class= "ui-widget-content">
		  			<tr class="ui-widget-header ">
				  		<th>序号</th>
				  		<th>房费金额</th>
				  		<th>房费类型</th>
				  		<th>房费时间</th>
				  		
				  		<th>房间号</th>
				  		<th>联系人</th>
				  		<th>联系电话</th>
				  		<th>入住人数</th>
				  		<th>入住说明</th>
				  		<th>是否退房</th>
				  		<th>是否为小时房</th>
				  		<c:if test="${sessionScope.user.level <= 1}">
					  		<th>选择</th>
						</c:if>
		  			</tr>	
				 	<s:iterator var="item" value="listChargeAndCheckIn">
			  		<%j++; %>
				 	
		  			  <tr class="accountAndCheckIn-item ui-widget-content "  >	
					  	<td><%=j %></td>
					  	<td><s:property value="#item.rc.charge"/></td>
					  	<s:if test="#item.rc.type == 1">
					  		<td>全天费用</td>
					  	</s:if>
					  	<s:elseif test="#item.rc.type == 2">
					  		<td>半天费用</td>
					  	</s:elseif>
					  	<s:else>
					  		<td>小时费用</td>
					  	</s:else>
					  	<td><s:property value="#item.rc.date"/></td>
					  	
					  	
					  	<s:if test = "#item.cif == null">
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>	
							<td>X已删X</td>	
					  	</s:if>
					  	<s:else>
					  		<td><s:property value="#item.cif.rId"/></td>
					  		<td><s:property value="#item.cif.name"/></td>
					  		<td><s:property value="#item.cif.phoneNumber"/></td>
					  		<td><s:property value="#item.cif.numberPeople"/></td>
					  		<td><s:property value="#item.cif.description"/></td>
					  		<td>
					  			<s:if test = "#item.cif.isCheckOut">
					  				已退
						  		</s:if>
						  		<s:else>
						  			未退
						  		</s:else>
						  	</td>
					  		<td>
					  			<s:if test = "#item.cif.isHourRoom">
					  				是
						  		</s:if>
						  		<s:else>
						  			不是
						  		</s:else>
						  	</td>
					  	</s:else>
					  	<c:if test="${sessionScope.user.level <= 1}">
					  		<s:if test = "#item.cif.isCheckOut">
							  	<td class="del_tr">
							  		<input  type="checkBox" name="delChargeIds" value=<s:property value="#item.rc.id"></s:property>></input>
						  		</td>
					  		</s:if>
					  		<s:else>
					  			<td>--</td>
					  		</s:else>
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
				  		<th>消费金额</th>
				  		<th>消费时间</th>
				  		<th>消费说明</th>
				  		
				  		<th>房间号</th>
				  		<th>联系人</th>
				  		<th>联系电话</th>
				  		<th>入住人数</th>
				  		<th>入住说明</th>
				  		<th>是否退房</th>
				  		<th>是否为小时房</th>
				  		<c:if test="${sessionScope.user.level <= 1}">
					  		<th>选择</th>
						</c:if>
		  			</tr>	
				 	<s:iterator var="accountAndCheckIn" value="listListAccountAndCheckIn.get(1)">
			  		<%j++; %>
				 	
		  			  <tr class="accountAndCheckIn-item ui-widget-content "  >	
					  	<td><%=j %></td>
					  	<td><s:property value="#accountAndCheckIn.account.balance"/></td>
					  	<td><s:property value="#accountAndCheckIn.account.genTime"/></td>
					  	<td><s:property value="#accountAndCheckIn.account.description"/></td>
					  	
					  	
					  	<s:if test = "#accountAndCheckIn.cif == null">
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>					  		
							<td>X已删X</td>	
							<td>X已删X</td>	
					  	</s:if>
					  	<s:else>
					  		<td><s:property value="#accountAndCheckIn.cif.rId"/></td>
					  		<td><s:property value="#accountAndCheckIn.cif.name"/></td>
					  		<td><s:property value="#accountAndCheckIn.cif.phoneNumber"/></td>
					  		<td><s:property value="#accountAndCheckIn.cif.numberPeople"/></td>
					  		<td><s:property value="#accountAndCheckIn.cif.description"/></td>
					  		<td>
					  			<s:if test = "#accountAndCheckIn.cif.isCheckOut">
					  				已退
						  		</s:if>
						  		<s:else>
						  			未退
						  		</s:else>
						  	</td>
					  		<td>
					  			<s:if test = "#accountAndCheckIn.cif.isHourRoom">
					  				是
						  		</s:if>
						  		<s:else>
						  			不是
						  		</s:else>
						  	</td>
					  	</s:else>
					  	<c:if test="${sessionScope.user.level <= 1}">
						  	<td class="del_tr">
						  		<input  type="checkBox" name="delIds" value=<s:property value="#accountAndCheckIn.account.id"></s:property>></input>
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
