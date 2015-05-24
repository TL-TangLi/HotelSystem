<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>innitial</title>
    
<script >

  $(function() {
  
  	var str = todayToString();
  	$("#beginDate_id").val(str);
  	$("#endDate_id").val(str);
    $("#queryAccountSum_submit").click();
  });
</script>

</head>
  
  <body>
  		initial.....
  
   		 <s:form id="queryAccountSumForm" cssStyle="display:none" action="requestqueryAccountSumActionForBalance"  executeScripts="true" namespace="/main"  >
			<s:textfield name="type" value="-1"></s:textfield>
    		<s:textfield name="beginDate" id="beginDate_id" value=""></s:textfield>
    		<s:textfield name="endDate" id="endDate_id" value=""></s:textfield>
			<s:submit id = "queryAccountSum_submit"  executeScripts="true"  formId="queryAccountSumForm"></s:submit>
		</s:form>
  </body>
</html>
