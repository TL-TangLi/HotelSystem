<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 	 <link rel="stylesheet" href="../jquery/jquery-ui.css" />
	<link rel="stylesheet" href="../jquery/jquery-ui.min.css" />
	
	<script src="../jquery/jquery-1.9.1.js"></script>
	<script src="../jquery/jquery-ui-1.10.1.custom.js"></script>
	   
    <title>accesstoday Order</title>
    
<script>
   $(function(){
    $(".requestTodayRoomStatistic-item").hover(
        function(){
            $(this).addClass('over');
        },
        function(){
            $(this).removeClass('over');
        }
    );
  /*   $(".requestTodayRoomStatistic-item").dblclick(
          function () {
			var roomId = $(this).children(".requestTodayRoomStatistic-id").html();
			accessRoom(roomId);	
          }
        );
 */
 });
</script>
  </head>
  <style>
	tr.requestTodayRoomStatistic-item{
	   background:#9fdbe9;
	}
	tr.over{
	    background:#65c6e1;
	}
	
	div.tableDiv{
		margin:5px;
		float:left;
		padding : 10px;
		
	}
	p.description{
		color:green;
		font-size:12;
	}
</style>
  
  <body>
  	 <p class="description">说明：1）可用总数不包含‘不可用房’，2）待住是今日未入住订单，3）过期是今天以前就该退的房(务必让其保持为0)</p>
  	<div class="ui-widget ui-widget-content tableDiv">
		  <table  class="ui-widget ui-widget-content" >
		  	
		  	<% 
		  		int index = 0;
		  	 %>
		  	<tr class="ui-widget-header ">
		  		<th>序号</th>
		  		<th>房间类型</th>
		  		<th>小时价格</th>
		  		<th>全天价格</th>
		  		<th>可用总数</th>
		  		<th>空房</th>
		  		<th>所有在住</th>
		  		<th>续住</th>
		  		<th>待退</th>
		  		
		  		<th>待住</th>
		  		<th>过期</th>
		  		<th>剩余</th>
		  	</tr>	
		  	<s:iterator var = "item" value = "listRoomPrice">
		  	<tr class="requestTodayRoomStatistic-item">	
		  		
		  		<td><%= ++index %></td>
		  		<td class="requestTodayRoomStatistic-id"><s:property value="#item.description"></s:property></td>
		  		<td><s:property value="#item.hourPrice"></s:property></td>
		  		<td><s:property value="#item.price"></s:property></td>
		  		<td><s:property value="#item.num"></s:property></td>
		  		<td><s:property value="#item.num - #item.checkIngCount-#item.expireCount-#item.todayCheckOut"></s:property></td>	
		  		<td><s:property value="#item.allcheckIngCount"></s:property></td>
		  		<td><s:property value="#item.checkIngCount"></s:property></td>
		  		<td><s:property value="#item.todayCheckOut"></s:property></td>
		  		<td><s:property value="#item.waitCheckInCount"></s:property></td>	
		  		<td><s:property value="#item.expireCount"></s:property></td>
		  		<td><s:property value="#item.num-#item.waitCheckInCount-#item.checkIngCount"></s:property></td>	
		  		
		  	</tr>
		  	</s:iterator>
		  	
		  </table>
  
  		</div>
  </body>
</html>
