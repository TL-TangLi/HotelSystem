<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<%@taglib prefix="s"  uri="/struts-tags"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <sx:head parseContent="false" />
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script src="../../jquery/jquery-1.9.1.js"></script>
	<script src="../../jquery/jquery-ui-1.10.1.custom.js"></script>
	<script src="../../jquery/jquery.ui.datepicker-zh-TW.js"></script>
	
	<link rel="stylesheet" href="../../jquery/jquery-ui.css" />
	<link rel="stylesheet" href="../../jquery/jquery-ui.min.css" />
	<link rel="stylesheet" href="../../css/home.css">
	
	
	<script type="text/javascript">
		//var user = {name:"${sessionScope.user.userName}",psw:"${sessionScope.user.psw}",level:"${sessionScope.user.level}"};
		
		
		$(function(){
			$( "input[type=submit],button,input[type=button]" ).button();
			$("#td_repeatPsw_id").css("display","none");
		});
		
		
		dojo.event.topic.subscribe("/modifyPswResult",function myfunction(data,type,request){
			
			if(type == "load")
			{
				//var headers = request.getAllResponseHeaders();
				//alert(headers);
				//var begin = headers.indexOf("result")+7;			//result:  7个字符
				//var len = headers.indexOf("\r\n",begin) - begin;	//每个属性 以 \r\n 分开 那么我们查下一个\r\n
				//alert(headers.substr(begin,len));
				
				alert(request.getResponseHeader("result"));	
				
			}
			
		});
		
		/// 主要是检测 当前密码是否输入正确。
		function modifyPswAvalidate(){
		
			if(parent.user.name == null)
				return false;
			
			if(($("[name=currentPsw]").val()=="") ||($("[name=newPsw]").val()=="") || ($("[name=repeatPsw]").val() !=$("[name=newPsw]").val()))
				return false;	
				
			if($("[name=currentPsw]").val() != parent.user.psw){
				alert("当前密码错了！");
				return false;
			}
			return true;
		}
		
		
		function newPswKeyUp(){
			
			var safeLevel = 0;
			
			var psw = $("[name=newPsw]").val();
			if(psw.length == 0)
				return 0;
			
			if(psw.length <5)
				return 1;
				
			var patNum =new RegExp("[0-9]");
			if(patNum.exec(psw) != null)
				safeLevel += 1;
			
			var patChar =new RegExp("[a-zA-Z]");
			if(patChar.exec(psw)!= null)
				safeLevel += 1;
			
			var patSpecial = new RegExp("[^a-zA-Z0-9]");
			if(patSpecial.exec(psw) != null)
				safeLevel += 1;
				
			return safeLevel;				
			
		};
		
		function showSafeLevel(){
			
			var safeLevel = newPswKeyUp();
			switch(safeLevel)
			{
			case 0:
				$("#psw_level_indicator").attr("src","${dynamicRes}/image/psw_level_none.gif");
				break;
			case 1:
				$("#psw_level_indicator").attr("src","${dynamicRes}/image/psw_level_l.gif");
				break;
			case 2:
				$("#psw_level_indicator").attr("src","${dynamicRes}/image/psw_level_m.gif");
				break;
			case 3:
				$("#psw_level_indicator").attr("src","${dynamicRes}/image/psw_level_h.gif");
				break;
			}
		};
		
		
		function inputFocus(i){
			switch(i)
			{
			case 1:
				$("#td_currentPsw_id").css("display","none");
				$("[name=currentPsw]").css("background-color","#fdf9c8");
				break;
				
			case 2:
				$("#td_newPsw_id").css("display","none");
				$("[name=newPsw]").css("background-color","#fdf9c8");
				
				break;
				
			case 3:
				$("#td_repeatPsw_id").css("display","none");
				$("[name=repeatPsw]").css("background-color","#fdf9c8");
			
				break;
				
			}
		};
		function inputBlur(i){
			$("[type=password]").css("background-color","#ffffff");
			switch(i)
			{
			case 1:
				if($("[name=currentPsw]").val()=="")
					$("#td_currentPsw_id").css("display","block");
				break;
				
			case 2:
				if($("[name=newPsw]").val()=="")
					$("#td_newPsw_id").css("display","block");
			case 3:
				if($("[name=newPsw]").val()!= $("[name=repeatPsw]").val())
					$("#td_repeatPsw_id").css("display","block");
				else
					$("#td_repeatPsw_id").css("display","none");
				break;
			}
			
		};
		
	</script>	
	
	<style type="text/css">
		
		.star{color:#ff0000;font-size: 20pt;}
		
		.inputDescription{color: #666666;font-weight: 2pt;font-family: fantasy;text-align: right;}
		.inputAlert{color:#ff9955;font-size: 9pt;text-align: left;}
		.inputTip{color:#999999;font-size: 9pt;text-align: left;}
		
		input{width: 200pt;height: 25pt;border-color:#3197b5;border-width: 1pt; 
				border-left-style:none;border-top-style: none; }
	</style>

  </head>
  
  <body>
  <p>&ensp;</p>
		
	  	<s:form  theme="simple" action="modifyPswActionForSetting"  namespace="/main" onsubmit="javascript:return modifyPswAvalidate()">
	  		<table align="center">
				<tr>
					<td><img src="${dynamicRes}/image/drink.png"></img></td>
					<td class="inputDescription">亲爱的，你可以在这里直接修改密码</td>
				</tr>
				<tr>
					<td class="inputDescription"><span class="star">*</span>当前密码:</td>
					<td><s:textfield maxlength="18" name="currentPsw" type="password" includeContext="false"  onfocus="inputFocus(1)" onblur="inputBlur(1)"></s:textfield></td>
					<td id="td_currentPsw_id"><span class="inputAlert" style=""><img src="${dynamicRes}/image/face.png"/>请输入旧密码</span></td>
				</tr>
				<tr><td>&ensp;</td></tr>
				<tr><td>&ensp;</td></tr>
				<tr>
					<td class="inputDescription"><span class="star">*</span>新密码:</td>
					<td><s:textfield maxlength="18" name="newPsw" type="password"  onfocus="inputFocus(2)" onblur="inputBlur(2)" onkeyup="showSafeLevel()"></s:textfield></td>
					<td id="td_newPsw_id"><span class="inputAlert" style=""><img src="${dynamicRes}/image/face.png"/>密码不能为空</span></td>
				</tr>
				<tr><td></td><td class="inputTip">&ensp;密码长度建议在6-18之间</td></tr>
				<tr><td>&ensp;</td></tr>
				<tr>
					<td class="inputDescription">密码强度:</td>
					<td><img id="psw_level_indicator" src="${dynamicRes}/image/psw_level_none.gif"/></td>
				</tr>
				<tr><td></td><td class="inputTip">&ensp;建议使用英文字母加数字或符号的混合密码</td></tr>
				<tr><td>&ensp;</td></tr>
				<tr>
					<td class="inputDescription"><span class="star">*</span>重复密码:</td>
					<td><s:textfield maxlength="18" name="repeatPsw"  type="password" includeContext="false" onfocus="inputFocus(3)" onblur="inputBlur(3)"></s:textfield></td>
					<td id="td_repeatPsw_id"><span class="inputAlert" style=""><img src="${dynamicRes}/image/face.png"/>两次密码不一致</span></td>
				</tr>
				<tr><td>&ensp;</td></tr>
				<tr>
					<td></td><td> <sx:submit value="确定"  notifyTopics="/modifyPswResult"> </sx:submit></td>				
					<td>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;</td>					
				</tr>
	  		</table>
	  	
	  	</s:form>
  	
  </body>
</html>
