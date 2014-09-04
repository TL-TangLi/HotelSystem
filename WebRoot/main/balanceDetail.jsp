<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>innitial</title>
    
<script src="../jquery/jquery-1.9.1.js"></script>
<script >
	function getQueryString(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null) 
	    	return unescape(r[2]);
	    
	     return null;
	}
 	
 	 $(function() {
 	 
 	 	$("#roomId").val(getQueryString("roomId"));

   		$("#requestBalanceDetail_submit").click();
  	});
</script>
  
  <body>
   initialing..... <br>
   
    <s:form  cssStyle="display:none"  action="requestBalanceDetailActionForBalance"  executeScripts="true" namespace="/main"  >
    		<s:textfield name="roomId" id="roomId" ></s:textfield>
    		<s:textfield name="type" value="-1"></s:textfield>
    		<s:textfield name="beginDate" value=""></s:textfield>
    		<s:textfield name="endDate" value=""></s:textfield>
			<s:submit id = "requestBalanceDetail_submit"  executeScripts="true"  ></s:submit>
	</s:form>
  </body>
</html>
