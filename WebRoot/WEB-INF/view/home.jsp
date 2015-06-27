<%User user=(User)session.getAttribute("user"); 
if(user==null) 
{%> 
<jsp:forward page="../main/login.jsp"/>
<%} %>


<%@page
	import="com.hotel.entity.*,com.hotel.staticdata.StaticData,java.text.DecimalFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="FormAndDialog.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理中心</title>

<script src="${dynamicRes}/js/homeInitial.js" charset="UTF-8"></script>
<script src="${dynamicRes}/js/homeMenu.js" charset="UTF-8"></script>
<script src="${dynamicRes}/js/homeRoomTypeOperation.js" charset="UTF-8"></script>

<script src="${dynamicRes}/js/homeOrder.js" charset="UTF-8"></script>
<script src="${dynamicRes}/js/home.js" charset="UTF-8"></script>

<script>
	
	var userLevel = '<%=user.level%>';
</script>


</head>


<body onload="bodyInitial()" >
	<!-- ////////////////////////////////////////////////////////////////////////////////////// -->
	<div id="tabs" >
		<ul>
			<li id="todaySummary" class="todayMenu"><a href="#tabs-0">今日统计</a>
			</li>
			<li id="roomMenuHead" class="roomMenu">
			<a href="#tabs-0">房间 管 理</a>
			</li>
			
			<li id="orderMenuHead" class="orderMenu">
			<a href="#tabs-0">订单 管 理</a>
			</li>
			<li id="checkInInfoMenuHead" class="checkInInfoMenu">
			<a href="#tabs-0">住 房 统 计</a>
			</li>
		</ul>

		
	<div id="tabs-0">
		
		
		<%
		
			int todayWaitToCheckInOrderCount = (Integer)request.getAttribute("todayWaitToCheckInOrderCount");			//待住订单
			int todayNewOrderCount = (Integer)request.getAttribute("todayNewOrderCount");						//新增订单
			int todayWaitToCheckOutRoomCount = (Integer)request.getAttribute("todayWaitToCheckOutRoomCount");			//待退房
			int todayNewCheckInRoomCount = (Integer)request.getAttribute("todayNewCheckInRoomCount");				//新入住
			int todayCheckIngRoomCount = (Integer)request.getAttribute("todayCheckIngRoomCount");					//全部入住房
			int todayLeft = (Integer)request.getAttribute("todayLeft");
			int todayEmptyRoomCount = (Integer)request.getAttribute("todayEmptyRoomCount");
			
		 %>
		<!-- menu -->
		<!-- ////////////////////////////////////////////////////////////////////////////////////// -->
		<div  id="todayInfoHead" class="ui-widget-header todayMenu">
			<button id="todayInfoPin"  onclick="todayInfoPinToggle()"></button>&ensp; &ensp;&ensp;&ensp;
			<a class="todayInfo" onclick="beforAccessOrder(3)">待住订单<%out.print(todayWaitToCheckInOrderCount); %></a>&ensp; &ensp;&ensp;&ensp;
			<a class="todayInfo" onclick="beforAccessOrder(4)">新增订单<%out.print(todayNewOrderCount); %></a>&ensp; &ensp;&ensp;&ensp;
			<a class="todayInfo" onclick="beforQueryRoom(1)">新入住<%out.print(todayNewCheckInRoomCount); %></a>&ensp; &ensp;&ensp;&ensp;
			<a class="todayInfo" onclick="beforQueryRoom(2)">待退房<%out.print(todayWaitToCheckOutRoomCount); %></a>&ensp; &ensp;&ensp;&ensp;
			<a class="todayInfo" onclick="beforQueryRoom(3)">所有在住<%out.print(todayCheckIngRoomCount); %></a>&ensp; &ensp;&ensp;&ensp;
			<a class="todayInfo" onclick="beforeRequestEmptyRoom(1)">所有可用空房<%out.print(todayEmptyRoomCount); %></a>&ensp; &ensp;&ensp;&ensp;
			<!-- <a class="todayInfo" href="">已退房xx</a>&ensp; &ensp;&ensp;&ensp; -->
			<a class="todayInfo" onclick="beforRequestTodayRoomStatistics()">剩余房统计<%out.print(todayLeft); %></a>&ensp; &ensp;&ensp;&ensp;&ensp; &ensp;&ensp;&ensp;
			刷新：	<a id="freshButton" href="roomTrendAction"></a>
			关闭：<button id="dialogClose" onclick="closeAllDialog()"></button>
			退出：<button id="poweroff" onclick="managerExit()"></button>
			设置：<a id="setting" target="_blank" href="../main/setting/setting.jsp"></a>
		</div>
		
		
		<ul id="roomMenu"  class="positionable roomMenu menu" >
			<c:if test="${sessionScope.user.level <= 1}">
				<li><a onclick="menueClickRequestRoomTypeList(1)">添加房间</a>
				</li>
				<li><a onclick="openAddRoomTypeDialog()">添加房间种类</a>
				</li>
				<li><a onclick="menueClickRequestRoomTypeList(3)">删除房间种类</a>
				</li>
				<li><a onclick="menueClickRequestRoomTypeList(4)">修改房间种类</a>
				</li>
			</c:if>
		</ul>
		<ul id="orderMenu" class="positionable orderMenu menu" >
			<li><a onclick="menueClickRequestRoomTypeList(2)">添加订单</a>
			</li>
			<li><a target="_blank" href="../main/queryOrder.jsp">查询订单</a>
			</li>
		</ul>	
		
		<ul id="checkInInfoMenu" class="positionable checkInInfoMenu menu" >
			<li><a target="_blank" href="../main/queryAccountSum.jsp">账目统计</a>
			</li>
			<li><a target="_blank" href="../main/queryRoomStatistics.jsp">房间剩余统计</a>
			</li>
			<li><a target="_blank" href="../main/queryCheckIn.jsp">入住信息统计</a>
			</li>
		</ul>	
			
			

		<!-- ////////提示对话框infoDialog 会刷新//////// -->
		<div id="infoDialog" style="display:none">
			<center>
				<s:form theme="simple" id="freshForm" action="roomTrendAction" namespace="/main" >
					<table class= "ui-widget-content" align="center">
						<tr><td><s:label id="info"></s:label></td></tr>			
						<tr><td><s:submit value="确认并刷新"/></td></tr>
					</table>
				</s:form>
			</center>
		</div>
		
		
		
		<!-- 房态 -->
		<!-- ////////////////////////////////////////////////////////////////////////////////////// -->
			<%
				int i = 0;
				@SuppressWarnings("unchecked")
				List<RoomState> list = (List<RoomState>)request.getAttribute("roomStateList");
				RoomState item;
				DecimalFormat df = new DecimalFormat( "0.00");	//余额控制小数后两位
			%>
			<s:iterator id="item" value="list">
				<% 
					item = list.get(i);
				%>
				<div  class = "room"
					<% out.print("id = room"+item.room.id);%> 
					 <%out.print(" ondblclick=\"accessRoom("+item.room.id+")\""); %>
					<%
						
						out.print("style = \"background-color: " + item.roomColor.colorValue+ "\"");
					%>>
					
						<h3 class="ui-widget-header">
						<%
							out.println(item.room.id+"("+item.roomPrice.description+")");
						%>
					</h3>
						<%
							if(item.room.description == null)
								out.println("<p>房间说明:无</p>");
							else							
								out.println("<p>房间说明:" + item.room.description+"</p>");
							if(item.checkInInfo ==null)
								out.println("<p>状态:" + item.roomColor.description+"</p>");
							else
								out.println("<p>状态:" + item.roomColor.description+"("+item.checkInInfo.outTime+")到期"+"</p>");
							
							if(item.checkInInfo != null)
							{
								out.println("<p>联系人:"+item.checkInInfo.name+"</p>");
								out.println("<p>费用剩余:"+df.format(item.leftMoney)+"</p>");
							}
							
								i++;
						%>
				</div>
			</s:iterator>
			<p style="clear:both">登录管理员：<%out.print(user.userName); %></p>
			<div style="clear:both"></div>
		
			
		</div>
		
	</div>
</body>
</html>