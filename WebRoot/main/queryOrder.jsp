<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>innitial</title>
    
<script src="../jquery/jquery-1.9.1.js"></script>
<script >

  $(function() {
    $("#queryOrder_submit").click();
  });
</script>

</head>
  
  <body>
  		initial.....
  
   		 <s:form id="queryOrderForm" cssStyle="display:none" action="requestQueryOrderAction" executeScripts="true" namespace="/main"  >
			<s:submit id = "queryOrder_submit"  executeScripts="true"  formId="queryOrderForm"></s:submit>
		</s:form>
  </body>
</html>
