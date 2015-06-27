<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>房间剩余统计</title>
    
<script>
   $(function(){
    $(".requestRoomStatistic-item").hover(
        function(){
            $(this).addClass('over');
        },
        function(){
            $(this).removeClass('over');
        }
    );
  /*   $(".requestRoomStatistic-item").dblclick(
          function () {
			var roomId = $(this).children(".requestAllRoomType-id").html();
			accessRoom(roomId);	
          }
        );
 */
 
 });
</script>
  </head>
  <style>
	tr.requestRoomStatistic-item{
	    background:#9fdbe9;
	}
	tr.over{
	    background:#65c6e1;
	}
	
	div.StatisticsDiv{
		margin:5px;
		float:left;
		padding : 10px;
		
	}
	p.time_p{
		font-size: 23;
		font-style: inherit;
		color:green;
	}
	p.description{
		color:green;
		font-size:12;
	}
</style>
  
  <body>
  
  	
  	
  	 
  	<div class="ui-widget ui-widget-content ">
  		<p class="description">注意：1)可用总数不包含‘不可用房’.2)续住数指当前在住且续住已经超过当天的房间数.3)待住数指当天以及当天以前将会入住（并且退房时间超过当天）的订单数
  		</p>
  		<table>
	  		<s:form theme="simple" id="requestRoomStatisticsForm" action="requestRoomStatisticsAction" executeScripts="true" namespace="/main"  >
	    		<tr>
	    			<td>最近</td>
	    			<td>
	    				<s:select label="Pets"
					       name="days"
					       list="#{'3':'3','5':'5', '7':'7','15':'15','30':'30','45':'45'}"
					       required="true"
						/>
	
	    			</td>
	    			<td> 天</td>
					<td><s:submit id = "requestRoomStatistics_submit" value="查找" executeScripts="true"  formId="requestRoomStatisticsActionForm"></s:submit></td>
				</tr>
			</s:form>
  		</table>
  	</div>
	<% 
		int i = 0;
		int j = 0;
	 %>
  <s:iterator  var = "listRoomPrice" value = "listListRoomPrice" status="count">
  	<% 
		i++;
		j = 0;
	 %>
	 <div class="StatisticsDiv ui-widget ui-widget-content " >
		<p class="time_p"><s:property value="listDate.get(#count.getIndex())"/></p>
	  	<table >
	  	
	  	<tr class="ui-widget-header ">
	  		<th>序号</th>
	  		<th>房间类型</th>
	  		<th>可用总数</th>
	  		<th>续住数</th>
	  		<th>待住数</th>
	  		<th>剩余数</th>
	  	</tr>	
	  	<s:iterator var = "roomPrice" value = "#listRoomPrice">
	  	<tr class="requestRoomStatistic-item">	
	  		<td><%= ++j %></td>
	  		<td class="requestAllRoomType-id"><s:property value="#roomPrice.description"></s:property></td>
	  		<td><s:property value="#roomPrice.num"></s:property></td>
	  		<td><s:property value="#roomPrice.checkIngCount"></s:property></td>
	  		<td><s:property value="#roomPrice.waitCheckInCount"></s:property></td>	
	  		<td><s:property value="#roomPrice.num-#roomPrice.waitCheckInCount-#roomPrice.checkIngCount"></s:property></td>	
	  	</tr>
	  	</s:iterator>
	  	
	  </table>
  </div>
  </s:iterator>
  
  
  
  </body>
</html>
