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
    $(".requestQueryRoom-item").hover(
        function(){
            $(this).addClass('over');
        },
        function(){
            $(this).removeClass('over');
        }
    );
    $(".requestQueryRoom-item").dblclick(
          function () {
			var roomId = $(this).children(".requstQueryRoom-id").html();
			accessRoom(roomId);	
          }
        );

 });
</script>
  </head>
  <style>
	tr.requestQueryRoom-item{
	    background:#ABE3EB;
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
  
  <p class="description">说明：双击可直接访问房间.注意：务必将“待退房”在13点之前退房，否者产生半天房费，18点之后将产生全天房费！</p>
  <div class="ui-widget ui-widget-content tableDiv ">	
	  <table >
	  	
	  	<% 
	  		int index = 0;
	  	 %>
	  	<tr class="ui-widget-header ">
	  		<th>序号</th>
	  		<th>房号</th>
	  		<th>房间类型</th>
	  		<th>房间说明</th>
	  		
	  		<th>联系人</th>
	  		<th>电话号码</th>
	  		<th>入住人数</th>
	  		<th>入住说明</th>
	  		<th>入住时间</th>
	  		<th>到期日期</th>
	  		<th>小时房</th>
	  		<th>总入账</th>
	  		<th>订单号</th>
	  	</tr>	
	  	<s:iterator var = "item" value = "roomAndCheckIn">
	  	<tr class="requestQueryRoom-item">	
	  		<td><%= ++index %></td>
	  		<td class="requstQueryRoom-id"><s:property value="#item.room.id"></s:property></td>
	  		<td><s:property value="roomPriceList.{? #this.id == #item.room.type}.get(0).description"></s:property></td>
	  		<td><s:property value="#item.room.description"></s:property></td>
	  		
	  		<td><s:property value="#item.ci.name"></s:property></td>
	  		<td><s:property value="#item.ci.phoneNumber"></s:property></td>
	  		<td><s:property value="#item.ci.numberPeople"></s:property></td>
	  		<td><s:property value="#item.ci.description"></s:property></td>
	  		<td><s:property value="#item.ci.enterTime"></s:property></td>
	  		<td><s:property value="#item.ci.outTime"></s:property></td>	
	  		
	  		
	  		<td>
		  		<s:if test="#item.ci.isHourRoom">
					 <p>是</p> 			
		  		</s:if>
		  		<s:else>
		  			否	
		  		</s:else>
	  		</td>
	  		
	  		<td><s:property value="#item.ci.balance"></s:property></td>	
	  		
	  		<td>
		  		<s:if test="#item.ci.orderId==0">
					 <p>无</p> 			
		  		</s:if>
		  		<s:else>
		  			<s:property value="#item.ci.orderId"></s:property>	
		  		</s:else>
	  		</td>
	  		
	  		
	  	</tr>
	  	</s:iterator>
	  	
	  </table>
  </div>
  
  </body>
</html>
