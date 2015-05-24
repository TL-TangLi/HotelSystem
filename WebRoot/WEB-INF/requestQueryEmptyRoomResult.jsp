<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	   
    
<script>
   $(function(){
    $(".requestEmptyRoomResult-item").hover(
        function(){
            $(this).addClass('over');
        },
        function(){
            $(this).removeClass('over');
        }
    );
    $(".requestEmptyRoomResult-item").dblclick(
          function () {
			var id = $(this).children(".emptyRoomId").html();
			aterSelectRoomForCheckIn(id);	
          }
        );

 });
</script>

 </head>
  <style>
	tr.requestEmptyRoomResult-item{
	    background:#AED0EA;
	}
	tr.over{
	    background:#4db2e6;
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
  		 <p class="description">说明：双击即可</p>
  		<div class="ui-widget ui-widget-content tableDiv" >
		  <table class="ui-widget ui-widget-content" >
		  	
		  	<% 
		  		int index = 0;
		  	 %>
		  	<tr class="ui-widget-header ">
		  		<th>序号</th>
		  		<th>房间号</th>
		  		<th>房间说明</th>
		  		<th>状态</th>
		  	
		  	</tr>	
		  	<s:iterator var="item" value="roomList" >
		  	<% 
		  		 index++;
		  	 %>
		  	<tr class="ui-widget-content requestEmptyRoomResult-item">
		  		<td><%= index %></td>
		  		<td class="emptyRoomId"><s:property value="#item.id"></s:property></td>
		  		<td><s:property value="#item.description"></s:property></td>
		  		
		  		<td>
		  			<s:if test="%{#item.isAvaliable == false}">
		  				不可用房
		  			</s:if>
		  			<s:elseif test="%{#item.isClean == true}">
		  				干净房
		  			</s:elseif>
		  			<s:else >
		  				脏房
		  			</s:else>
		  		</td>
		  	</tr>
		  	</s:iterator>
		  	
		  </table>
	  	</div>
  
  </body>
</html>
