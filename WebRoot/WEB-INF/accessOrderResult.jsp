<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script src="../script/home.js" charset="UTF-8"></script>

 <script >
  
  $(function(){
  
 	 $( "input[type=submit],button" ).button();
	$( "#updateOrder_enterDate" ).datepicker({ dateFormat: "yy-mm-dd" });
  
  });
  
</script>

</head>
  
  <body>
  
  <table  class="ui-widget ui-widget-content" >
  	<s:form theme="simple"  id="updateOrderForm" namespace="/main" action="addOrderAction" onsubmit="return updateOrderAvalidate()" >
  		<tr  style="display:none">
	  		<td><s:textfield   name ="od.id" ></s:textfield></td>
	  		<td><s:textfield  id="addOrUpdate" name ="addOrUpdate" value="-1"></s:textfield></td>
  		</tr>
  		<tr>
  			<td>下单时间：</td>
  			<td><s:label><s:property value="od.orderTime"/></s:label></td>
  		</tr>
  		<tr>
  			<td>联系人：</td>
  			<td><s:textfield name ="od.name"  id="updateOrder_name"></s:textfield></td>
  			<td><p id="updateOrder_warn_name" class="warn">*必填</p> </td>
  		</tr>
  		<tr>
  			<td>联系电话：</td>
  			<td><s:textfield name ="od.phoneNumber" ></s:textfield></td>
  		</tr>
  		<tr>
  			<td>说明：</td>
  			<td><s:textfield name ="od.description" ></s:textfield></td>
  		</tr>
  		<tr>
  			<td>房间类型：</td>
  			<td><s:select 
  				name = "roomType"
				id ="room_type_list_updateOrder"
				list="roomTypeMap"
				value="od.roomType"
		/> </td>
  		</tr>
  		<tr>
  			<td>入住时间：</td>
  			<td><s:textfield name ="od.enterDate" id="updateOrder_enterDate" readonly="true" ></s:textfield></td>
  		</tr>
  		<tr>
  			<td>入住天数：</td>
  			<td><s:textfield name ="od.days" id="updateOrder_days"></s:textfield></td>
  			<td><p id="updateOrder_warn_days" class="warn">*必填</p> </td>
  		</tr>
  		<tr>
  			<td>延迟时间：</td>
  			<td><s:textfield name ="od.extendHour" ></s:textfield></td>
  		</tr>
  		<tr>
  		 	<td></td>
  			<td>
  				<button onclick="updateOrderButtonClick()">修改</button>
 				<%-- <sx:submit id="delOrder_submit" formId="delOrderForm"  value='删除'></sx:submit> --%>
  			</td>
  			<td></td>
  		</tr>
  		
  	</s:form>
	  	
	  	<!-- ///删除订单delOrderForm -->
	  <s:form id="delOrderForm"  style="display:none"   namespace="/main" action="delOrderAction" onsubmit="return delOrderAvalidate()">
	  		<s:textfield style="display:none" id="delOrder_id" name ="order.id" ></s:textfield>
	  </s:form>	
	  	
  </table>
  
  
  </body>
</html>
