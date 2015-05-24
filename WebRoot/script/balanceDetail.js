$(function() {
  
	
	 ////一进入 便访问 房间信息  访问成功后调用 afterAccessCheckInRoom()  设置房间信息显示
    accessRoom($("#roomId_id").val(),false);
	

    $( ".accordion" ).accordion({
    	collapsible: true,
    	active:false,
    	heightStyle: "content"
    });
    
    $( "input[type=submit],button" ).button();
    
    $( "#beginDate_id" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#endDate_id" ).datepicker({ dateFormat: "yy-mm-dd" });
    
    $( "#beginDate_ignore_button" ).button({
    	icons: {
    		primary: "ui-icon-close"
    	},
    	text: false
    });
    
    $( "#endDate_ignore_button" ).button({
    	icons: {
    		primary: "ui-icon-close"
    	},
    	text: false
    });
    
    
    $( "#queryBalance_submit" ).button({
    	icons: {
    		primary: "ui-icon-search"
    	},
    	text: false
    });
    
    
    $( "#addBalance_button" ).button({
    	icons: {
    		primary: "ui-icon-plusthick"
    	},
    	text: false
    });
    $( "#addConsume" ).button({
    	icons: {
    		primary: "ui-icon-plusthick"
    	},
    	text: false
    });
    
    $( "#settleAccount" ).button({
    	icons: {
    		primary: "ui-icon-plusthick"
    	},
    	text: false
    });
    $( "#delAccounts" ).button({
    	icons: {
    		primary: "ui-icon-minus"
    	},
    	text: false
    });
    $( "#resetCharge" ).button({
    	icons: {
    		primary: "ui-icon-arrowreturnthick-1-n"
    	},
    	text: false
    });
    
    
    
    $(".account-item").hover(
            function(){
                $(this).addClass('over');
            },
            function(){
                $(this).removeClass('over');
            }
        );

    
    $(".account-item").dblclick(function () {
    		
    	
            }
          );
    
    
    dojoSubscribe("/addBalanceResult","addBalanceDialog");
    dojoSubscribe("/consumeResult","consumeDialog");
    
});





function ignoreDate(n){
	if(n== 1)
		$("#beginDate_id").val("");
	else
		$("#endDate_id").val("");
}


function afterAccessCheckInRoom(){
	
	var str_description = "<p>&ensp;&ensp;&ensp;"+checkInRoomObj.checkInInfo.enterTime+"入住,"
	+checkInRoomObj.checkInInfo.outTime+"过期";
	
	if(checkInRoomObj.checkInInfo.hourRoom==true )
		str_description += "——(是小时房)——，";
	else
		str_description += "，";
	
	str_description += "联系人:"+checkInRoomObj.checkInInfo.name+" , 联系电话："+checkInRoomObj.checkInInfo.phoneNumber
	+",入住人数："+checkInRoomObj.checkInInfo.numberPeople+",入住说明:"+checkInRoomObj.checkInInfo.description+".</p>";
	
	
	
	$("#description_div").append(str_description);
	
	
	str_description = "<p>&ensp;&ensp;&ensp;";
	if(checkInRoomObj.countByHour)
		str_description += "已入住"+checkInRoomObj.enterCount+"小时:"+checkInRoomObj.roomPrice.hourPrice;
	else
		str_description += "产生"+checkInRoomObj.enterCount+"天房费:" +checkInRoomObj.enterCount+" × "+checkInRoomObj.roomPrice.price +" = " +checkInRoomObj.enterCount*checkInRoomObj.roomPrice.price;
	
	
	str_description += "元，当前余额为："+checkInRoomObj.leftMoney+"  元   (余额  = 总入账 - 总消费 - 总房费)</p>";
	
	$("#description_div").append(str_description);
	
		
}




//isSettleAccount 参数只是用于结账
function openAddBalance(isSettleAccount){
	
	//打开对话框
	$("#addBalanceDialog").dialog({width:370,height:240,modal: true});
	if(isSettleAccount){
		$("#willCheckOut_Id").val(true);
		$("#addBalance_description_id").val("结账");
		$("#addbalance_balance_id").val(-checkInRoomObj.leftMoney);
		$( "#addBalanceDialog").dialog( "option", "title", "结账");
		
		//不可修改
		$("#addbalance_balance_id").attr('readonly','readonly');
		$("#addBalance_description_id").attr('readonly','readonly');
		$("#addBalance_description_id").css('background','rgb(224,223,224)');
		$("#addbalance_balance_id").css('background','rgb(224,223,224)');
		
	}
	else{
		
		$("#willCheckOut_Id").val(false);
		$("#addBalance_description_id").val("正常入账");
		$("#addbalance_balance_id").val("0.0");
		$( "#addBalanceDialog").dialog( "option", "title", "入账");
		
		//可修改
		$("#addbalance_balance_id").removeAttr('readonly');
		$("#addBalance_description_id").removeAttr('readonly');
		$("#addBalance_description_id").css('background','rgb(255,255,255)');
		$("#addbalance_balance_id").css('background','rgb(255,255,255)');
		
	}
	
}

//将房间号填入入账表单
function addBalanceAvalidate(){
	
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	
	var permission = true;
	
	//校验输入  匹配  整数 浮点数， 以及，负数！！
	var balance =$("#addbalance_balance_id").val();
	var reg = /^-?(\d+\.)?\d+$/;
	if(reg.test(balance) == false){
		$("#addBalance_balance_warn").html("*输入不正确");
		permission = false;
	}
	else
		$("#addBalance_balance_warn").html("");

	
	
	if($("#addBalance_type_id").val() == -1){
		permission = false;
		$("#addBalance_type_warn").html("*必选");
	}
	else
		$("#addBalance_type_warn").html("");
		
	if(confirm("你正在操作房间入账，请确保交易后再入账！！（如果入账金额为负，代表应当找给客人相应金额）是否继续？") == false){
		permission = false;
	}
	
	if(permission){
		//获取当前房间号
		document.getElementById("addBalance_rId").value = $("#roomId_id").val();
		isOperating = true;
	}
	
	return permission;
}








function openConsume(){
	//打开对话框
	$("#consumeDialog").dialog({width:370,height:210,modal: true});
	$( "#consumeDialog").dialog( "option", "title", "添加消费");
}

function consumeAvalidate(){
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	
	var permission = true;
	
	//校验输入  匹配  整数 浮点数， 以及，负数！！
	var balance =$("#consumeBalance_id").val();
	var reg = /^-?(\d+\.)?\d+$/;
	if(reg.test(balance) == false){
		$("#consumeBalance_warn").html("*输入不正确");
		permission = false;
	}
	else
		$("#consumeBalance_warn").html("");

	
	if($("#consumeDescription_id").val() == "" ||$("#consumeDescription_id").val() == null){
		$("#consumeDescription_warn").html("*必填");
		permission = false;
	}
	else
		$("#consumeDescription_warn").html("");
	
	
	if(permission){
		//获取当前房间号
		document.getElementById("addBalance_rId").value = $("#roomId_id").val();
		isOperating = true;
	}
	
	return permission;
}




function resetRoomCharge(){
	 var answer=confirm('警告：你正进行房费重置操作（以现在的房间价格，重新生成房费记录，操作后不可恢复，请谨慎操作）！');
	 if(answer){
		 //页面跳转。
		if(window.prompt("请输入验证  ","") != "sa_sa"){
			alert("验证失败");
			return;
		} 
	 }
	 else
		 return;
	 
	 var str_cid = "";
	 str_cid += "roomId="+$("#roomId_id").val() + "&";
	 str_cid += "beginDate="+$("#beginDate_id").val() + "&";
	 str_cid += "endDate="+$("#endDate_id").val() + "&";
	 str_cid += "type="+$("#type_id").val();
	 window.location.href='resetRoomChargeActionForBalance?'+str_cid;
}


//删除选中 复选框的 账目以及  房费记录
function chkbox_del(){
	
	 var answer=confirm('警告：确定删除选中项？选择删除将不可恢复！');
	 if(answer){
		 //页面跳转。
		if(window.prompt("请输入验证  ","") != "sa_sa"){
			alert("验证失败");
			return;
		} 
	 }
	 else
		 return;
	 
	var ell = document.getElementsByTagName('input');
	var llen = ell.length;
	var str_cid='';
	for(var i=0;i<llen;i++)
	{
		if(ell[i].type=="checkbox" && ell[i].checked == true ){
			if(ell[i].value!=''){
				str_cid +="delIds="+ell[i].value;
				str_cid +="&";
			}
		}
	 }
	 if(str_cid==''){
	   alert('请至少选中一项要删除的内容！');
	   return;
	  }
	 
	//三个过滤器 目的是删除之后 跳转回来 继续保存 以前的信息
	 str_cid += "roomId="+$("#roomId_id").val() + "&";
	 str_cid += "beginDate="+$("#beginDate_id").val() + "&";
	 str_cid += "endDate="+$("#endDate_id").val() + "&";
	 str_cid += "type="+$("#type_id").val();
	
	 window.location.href='requestDelAccountInRoomActionForBalance?'+str_cid;
}







