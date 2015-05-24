<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	   
    <title>accesstoday Order</title>
    
<script>
   $(function(){
    $(".requestOrderForCheckIn-item").hover(
        function(){
            $(this).addClass('on');
        },
        function(){
            $(this).removeClass('on');
        }
    );
    $(".requestOrderForCheckIn-item").dblclick(
          function () {
			var id = $(this).children(".requestOrderForCheckIn-id").html();
			afterSelectOrder(id);	
          }
        );
});
</script>
  </head>
  <style>
	tr.requestOrderForCheckIn-item{
	    background:#9C6
	}
	tr.on{
	    background:#4db2e6
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
  	  <p class="description">双击便可选中订单，如果无订单，请双击第一行的无订单入住，订单可以重新关联，请注意每关联一次都会给该房间续住订单指定的天数！
  	  	<br>另外如果是入住：1)如果选择小时房入住，关联订单无效，指定入住天数也无效 .2)如果非小时房入住且关联了订单，指定入住天数无效.
  	  </p>
  	  
	  <div class="ui-widget ui-widget-content tableDiv" >
		  <table id="orderTable" >
		  	<tr class="ui-widget-header ">
		  		<th>订单号</th>
		  		<th>预订人姓名</th>
		  		<th>联系电话</th>
		  		<th>预定说明</th>
		  		<th>预定时间</th>
		  		<th>预定天数</th>
		  		<th>入住时间</th>
		  		<th>订单延时</th>
		  	</tr>	
		  	<tr class="ui-widget-content requestOrderForCheckIn-item">
		  		<td class="requestOrderForCheckIn-id">无订单入住</td>
		  		<td>-----</td>
		  		<td>-----</td>
		  		<td>-----</td>
		  		<td>-----</td>
		  		<td>-----</td>
		  		<td>-----</td>
		  		<td>-----</td>
		  	</tr>
		  	<s:iterator var="item" value="listOrder" >
		  	<tr class="ui-widget-content requestOrderForCheckIn-item">
		  		<td class="requestOrderForCheckIn-id"><s:property value="#item.id"></s:property></td>
		  		<td><s:property value="#item.name"></s:property></td>
		  		<td><s:property value="#item.phoneNumber"></s:property></td>
		  		<td><s:property value="#item.description"></s:property></td>
		  		<td><s:property value="#item.orderTime"></s:property></td>
		  		<td><s:property value="#item.days"></s:property></td>
		  		<td><s:property value="#item.enterDate"></s:property></td>
		  		<td><s:property value="#item.extendHour"></s:property></td>
		  	</tr>
		  	</s:iterator>
		  	
		  </table>
	  </div>
  
  </body>
</html>
