<%@page import="com.hotel.entity.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<%@include file="FormAndDialog.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>房间<s:property value="roomId"/>账单</title>
	<link rel="stylesheet" href="${staticRes }/jquery/pepper-grinder/jquery-ui.css" />
	<link rel="stylesheet" href="${staticRes }/jquery/pepper-grinder/jquery-ui.min.css" />
	<script src="../script/home.js" charset="UTF-8"></script>
	<script src="../script/balanceDetail.js" charset="UTF-8"></script>

 <style>
	tr.account-item{
	    background:#aaaaaa;
	}
	tr.over{
	    background:#999999;
	}
	td{color: black;}
	div.serchDiv{ font-size: 14px;color:#666666; font-family:cursive;}
	div.description{ font-size:14;}
	p{color:#666666; font-family:cursive; }
	h1.title{color:#666666; font-family:cursive;}
</style>

	
</head>
  
  <body >
	  <h1 align="center" class="ui-widget-header title">房间<s:property value="roomId"/>账单</h1>
	  
	  <div id ="description_div" class="ui-widget-header description" >
	  	
	  </div>
	  
	  <div  class="ui-widget-header serchDiv" >
		 <s:form theme="simple">
			
			<s:textfield id ="roomId_id" name = "roomId"  cssStyle="display:none"></s:textfield>
			&ensp;&ensp;&ensp;类型：
			<s:select 
				id="type_id"
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
			
			增加入账<button id="addBalance_button" type = "button" onclick="openAddBalance()"></button>&nbsp;&nbsp;&nbsp;
			增加消费<button id="addConsume" type = "button" onclick="openConsume()"></button>&nbsp;&nbsp;&nbsp;
			结账<button id="settleAccount" type = "button" onclick="openAddBalance(true)"></button>
			<c:if test="${sessionScope.user.level <= -1}">
				删除所选<button id="delAccounts" type = "button" onclick="chkbox_del()"></button>
			</c:if>
			<c:if test="${sessionScope.user.level <= 1}">
				房费重置<button id="resetCharge" type = "button" onclick="resetRoomCharge()"></button>
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
						<td><s:if test='#account.type == 0'>银行卡</s:if>
							<s:elseif test='#account.type == 1'>现金</s:elseif>
							<s:elseif test='#account.type == 2'>网络费用</s:elseif>
							<s:elseif test='#account.type == 3'>积分兑换</s:elseif>
						</td>						  	
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
		
		
	  <!-- /////////////////////////////////////////////房费统计/////////////////////////////////////////////////// -->
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
	  
	  
		
	<!-- ////////提示对话框infoDialog 会刷新//////// -->
	<div id="infoDialog" style="display:none">
		<center>
			<s:form theme="simple" id="freshForm" action="requestBalanceDetailActionForBalance" namespace="/main" >
				<table class= "ui-widget-content" align="center">
					<tr><td><s:label id="info"></s:label></td></tr>			
					<s:textfield name="roomId" cssStyle="display:none"></s:textfield>
		    		<s:textfield name="type"  cssStyle="display:none"></s:textfield>
		    		<s:textfield name="beginDate"  cssStyle="display:none"></s:textfield>
		    		<s:textfield name="endDate"  cssStyle="display:none"></s:textfield>
					<tr><td><s:submit id = "requestBalanceDetail_submit" value="确认并刷新" executeScripts="true"  ></s:submit></td></tr>
				</table>
			</s:form>
		</center>
	</div>


	

  </body>
</html>
