<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>innitial</title>
    
<script >

  $(function() {
  	
  	var today = todayToString();
  	$("#enterDateBegin").val(today);
  	$("#enterDateEnd").val(today);
  	
    $("#requestqueryCheckIn_submit").click();
  });
</script>

</head>
  
  <body>
  		initial.....
  
   		 <s:form id="requestqueryCheckInForm" cssStyle="display:none" action="queryCheckInAction" executeScripts="true" namespace="/main"  >
			<s:textfield name="enterDateBegin" id="enterDateBegin"></s:textfield>
			<s:textfield name="enterDateEnd"    id="enterDateEnd"></s:textfield>
			<s:textfield name="outDateBegin"></s:textfield>
			<s:textfield name="outDateEnd"></s:textfield>
			<s:submit id = "requestqueryCheckIn_submit"  executeScripts="true"  formId="requestqueryCheckInForm"></s:submit>
		</s:form>
  </body>
</html>
