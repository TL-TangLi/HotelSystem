<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	   
    <title>accesstoday Order</title>
    
<script>
   $(function(){
    $(".requestOrderResult-item").hover(
        function(){
            $(this).addClass('over');
        },
        function(){
            $(this).removeClass('over');
        }
    );
    $(".requestOrderResult-item").dblclick(
          function () {
			var id = $(this).children(".orderId").html();
			requestOrder(id);	
          }
        );

 });
</script>

 </head>
  <style>
	tr.requestOrderResult-item{
	    background:#AED0EA;
	}
	tr.over{
	    background:#4db2e6;
	}
	p.description{
		color:green;
		font-size:12;
	}
	
	div.tableDiv{
		margin:5px;
		float:left;
		padding : 10px;
	}
</style>
  
  <body>
  <p class="description">说明：双击可直接访问订单(注意：1)下午七点之后应当将今日待住的所有订单修改为‘放弃入住’或者直接删除，但要注意某些有延时的订单。2)凌晨六点后才可入住当天订单！)</p>
  <div class="ui-widget ui-widget-content tableDiv">
	  <table id="orderTable"  >
	  	
	  	<% 
	  		int index = 0;
	  	 %>
	  	<tr class="ui-widget-header ">
	  		<th>序号</th>
	  		<th>订单号</th>
	  		<th>预订人姓名</th>
	  		<th>联系电话</th>
	  		<th>预定说明</th>
	  		<th>预定时间</th>
	  		<th>预定房间类型</th>
	  		<th>预定天数</th>
	  		<th>入住日期</th>
	  		<th>订单延时</th>
	  		<th>信息</th>
	  	
	  	</tr>	
	  	<s:iterator var="item" value="listOrder" >
	  	<% 
	  		 index++;
	  	 %>
	  	<tr class="ui-widget-content requestOrderResult-item">
	  		<td><%= index %></td>
	  		<td class="orderId"><s:property value="#item.id"></s:property></td>
	  		<td><s:property value="#item.name"></s:property></td>
	  		<td><s:property value="#item.phoneNumber"></s:property></td>
	  		<td><s:property value="#item.description"></s:property></td>
	  		<td><s:property value="#item.orderTime"></s:property></td>
	  		
	  		<td><s:property value="roomPriceList.{? #this.id == #item.roomType}.get(0).description"></s:property></td>
	  		
	  		<td><s:property value="#item.days"></s:property></td>
	  		<td><s:property value="#item.enterDate"></s:property></td>
	  		<td><s:property value="#item.extendHour"></s:property></td>
	  		
	  		
	  		<td>
	  			<s:if test="%{#item.info == 0}">
	  				未入住
	  			</s:if>
	  			<s:elseif test="%{#item.info == 1}">
	  				已入住
	  			</s:elseif>
	  			<s:else >
	  				放弃入住
	  			</s:else>
	  		</td>
	  	</tr>
	  	</s:iterator>
	  	
	  </table>
  
  </div>
  </body>
</html>
