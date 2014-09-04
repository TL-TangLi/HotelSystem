<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>innitial</title>
    
<script src="../jquery/jquery-1.9.1.js"></script>
<script >

  $(function() {
    $("#requestAllRoom_submit").click();
  });
</script>
  
  <body>
   initialing..... <br>
   
    <s:form id="requestAllRoomForm" cssStyle="display:none" action="requestAllRoomAction" executeScripts="true" namespace="/main"  >
			<s:submit id = "requestAllRoom_submit"  executeScripts="true"  formId="requestAllRoomForm"></s:submit>
	</s:form>
  </body>
</html>
