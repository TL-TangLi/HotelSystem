<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>innitial</title>
    
<script >

  $(function() {
  	
    $("#requestRoomStatistics_submit").click();
  });
</script>

</head>
  
  <body>
  		initial.....
  
   		 <s:form id="requestRoomStatisticsForm" cssStyle="display:none" action="requestRoomStatisticsAction" executeScripts="true" namespace="/main"  >
    		<s:textfield name="days" id="days_id" value="7"></s:textfield>
			<s:submit id = "requestRoomStatistics_submit"  executeScripts="true"  formId="requestRoomStatisticsActionForm"></s:submit>
		</s:form>
  </body>
</html>
