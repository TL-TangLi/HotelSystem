///////////////////////////////////////////////////function and global



//////属性不能以 is 开头！  如：isAvaliable 被转换成 avaliable 了！！！
var obj;

//保存干净房 RoomState
var cleanRoomObj;
//保存入住房ROomState
var checkInRoomObj;
//保存不可入住房RoomState
var dirtyRoomObj;


function dialogClose(dialogname){
	$("#"+dialogname).dialog("destroy");
	
};


///房间类型  表单 生成  List列表
function  jsonToRoomTypeListSelect(trId,selectId,data){

	roomTypeObj = eval(eval("("+data+")").jsonData);
	
	var str_select = "<select id="+selectId+">";
	str_select += "<option value = '-1'>请指定房间类型........</option>";
	for(var i = 0;i<roomTypeObj.length;i++)
	{
		str_select += "<option value="+roomTypeObj[i].id+">"+roomTypeObj[i].description+"</option>";
	}
	str_select += "</select>";
	
	$("#"+trId).empty();
	$("#"+trId).append(str_select);
	
}

///////////////////////////////////////////////////////////访问单个房间 。。第二个参数 openDialog 唯一作用便是在打开账单时，
//////////////////////////////////////////////请求房间信息，不打开对话框,而是调用 balancDetail.js 文件中的 afterAccessCheckInRoom()

function accessRoom(rid,openDialog){
	
	//如果infoDialog 存在，那么此时的 请求在requestResult.jsp 页面上，此时accesRoom 将产生异常！
	if(document.getElementById("infoDialog").style.display != 'none')
		return;
	
	$.ajax({ 
		   url : "infoRoomAction", 
		   data : { 
		       "roomId":rid
		   }, 
		   type : "GET", 
		   success : function(data){ 
			   
			   if(!data.rs){
				   openMessageDialog(JSON.parse(data).message,'提示');
				   return;
			   }
			   	obj = data.rs;
			   	
				//房间状态
				switch(obj.roomColor.id)
				{
				//干净房
				case 1:
					
					cleanRoomObj  = obj;
					openCleanRoom();
					return;
				//已入住
				case 3:
				case 4:
				case 5:
					
					checkInRoomObj = obj;
					if(openDialog == false){
						afterAccessCheckInRoom();
						return;
					}
						
					openUpdateCheckInInfoDialog();
					
					return;
				//脏房
				case 2:
				//不可用房
				case 6:
				default:
					dirtyRoomObj = obj;
					//开始请求 房间类型列表
					menueClickRequestRoomTypeList(6);
					break;
				}
				
				return;
		   }
		}); 
};





/////////////////////////////////////////////////////////////////////////入住操作

function openCleanRoom(){
	$("#checkInDialog").dialog({width:450,height:510});
	$( "#checkInDialog").dialog( "option", "title", cleanRoomObj.room.id +"(" +cleanRoomObj.roomPrice.description+")--"+cleanRoomObj.roomPrice.price+"/天--"+cleanRoomObj.roomPrice.hourPrice+"/时");
}
//入住校验，以及填写roomid 到表单
function checkInAvalidate(){
	
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	
	
	//填写申请入住表单的房间号
	document.getElementById("checkIn_cif_rId").value = cleanRoomObj.room.id;
	
	var submitPermission = true;
	
	
	//姓名不为空 校验
	var cif_name = document.getElementById("checkIn_cif_name");
	if(cif_name.value==""){
		document.getElementById("checkIn_warn_name").innerHTML="*必填";
		submitPermission = false;
	}
	else
		document.getElementById("checkIn_warn_name").innerHTML="";
	
	
	//////////////////////////////////
	//入住天数校验
	var cif_days = document.getElementById("checkIn_days");
	if(cif_days.value==""){
		document.getElementById("checkIn_warn_days").innerHTML="*必填";
		submitPermission = false;
	}
	
	//字符串匹配数字正则表达式 :(如果匹配到有任何一个非数字的字符串，那么可以肯定输入非法了)
	else if(cif_days.value.match(/\D/) !=null || cif_days.value == 0){	
		document.getElementById("checkIn_warn_days").innerHTML="*必须为大于0的整数";
		submitPermission = false;
	}
	else
		document.getElementById("checkIn_warn_days").innerHTML="";
	
	
	///////////////////////////////////
	//小时房选择校验
	var cif_isHourRoom = document.getElementById("checkIn_cif_isHourRoom");
	if(cif_isHourRoom.value==-1){
		submitPermission = false;
		document.getElementById("checkIn_warn_isHourRoom").innerHTML="*必填";
	}
	else
		document.getElementById("checkIn_warn_isHourRoom").innerHTML="";
	
	///////////////////////////////////
	//入住人数校验
	var cif_numberPeople = document.getElementById("checkIn_cif_numberPeople");
	if(cif_numberPeople.value ==""){
		submitPermission = false;
		document.getElementById("checkIn_warn_numberPeople").innerHTML="*必填";
	}
	//字符串匹配数字正则表达式 :(如果匹配到有任何一个非数字的字符串，那么可以肯定输入非法了)
	else if(cif_numberPeople.value.match(/\D/) !=null||cif_numberPeople.value == 0){	
		document.getElementById("checkIn_warn_numberPeople").innerHTML="*必须为大于0的整数";
		submitPermission = false;
	}
	else
		document.getElementById("checkIn_warn_numberPeople").innerHTML="";
	
	///////////////////////////////////
	//入账校验
	var reg = /^\d*(\d+\.)?\d+$/;
	var cif_balance = $("#checkIn_cif_balance").val();
	if(cif_balance == ""){
		submitPermission = false;
		$("#checkIn_warn_balance").html("*必填");
	}
	else if(reg.test(cif_balance) == false){
		$("#checkIn_warn_balance").html("*必须为整数或小数");
		submitPermission = false;
	}
	else
		$("#checkIn_warn_balance").html("");
	
	
	///////////////////////////////////
	//入账类型校验
	if($("#checkIn_balanceType").val() == -1){
		submitPermission = false;
		$("#checkIn_warn_balanceType").html("*必选");
	}
	else
		$("#checkIn_warn_balanceType").html("");

	///////////////////////////////////
	//订单非法处理！ （必须要处理，否者后台调用setter 异常，根本不能进入action）
	var cif_orderId =  document.getElementById("checkIn_cif_orderId");
	if(cif_orderId.value.match(/\D/) !=null || cif_orderId.value=="")
		cif_orderId.value = 0;

	
	//如果表单现在没有处理，并且允许提交，那么设置isOperating 为true 防止重复提交
	if(submitPermission == true)
		isOperating = true;
	
	return submitPermission;
	
};







/////////////////////////////////////////////////////////////////////////修改房间类型
//填写表单 
function changeRoomTypeAvaliable(){
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	//校验 类型选择
	if($("#changeRoomType_list").val() <= 0)
		return false;
	
	if(confirm("确定要修改该房间类型?")==false)
		return false;
	else{
		if(window.prompt("请输入验证  ","") != "sa_sa") {
			alert("验证失败");
			return false;
		}
	}
	$("#changeRoomType_rId").val(dirtyRoomObj.room.id);
	$("#changeRoomType_oldType").val(dirtyRoomObj.room.type);
	
	isOperating = true;
	return true;
}



/////////////////////////////////////////////////////////////////////////脏房 不可用房 干净房 切换  以及房间删除
var changeRoomStateComeFrom;
//房间状态改变来源， 1 代表 来自干净房，2代表非干净房
function beforChangeRoomState(n){
	changeRoomStateComeFrom = n;
}

//按下改变房间状态按钮时，将房间号填入修改房间状态表单
function changeRoomStateAvaliable(){
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	if(changeRoomStateComeFrom ==1){
		//干净房改为 脏房
		$("#changeRoomState_select").empty();
		$("#changeRoomState_select").append("<option value=2></option>");
		//填写修改房间状态表单中的房间号
		document.getElementById("changeRoomState_rId").value = cleanRoomObj.room.id;
	}
	else if(changeRoomStateComeFrom ==2){
		//填写修改房间状态表单中的房间号
		document.getElementById("changeRoomState_rId").value = dirtyRoomObj.room.id;
		
		//如果是删除房间 要询问
		if($("#changeRoomState_select").val() == 0)
			if(confirm("确定要删除？删除后不可恢复,但是关于该房间的入住信息将被保留")==false)
				return false;
			else{
				if(window.prompt("请输入验证  ","") != "sa_sa") {
					alert("验证失败");
					return false;
				}
			}
	}
	
	isOperating = true;
	return true;
}






/////////////////////////////////////////////////////////////////////////已入住操作

function openUpdateCheckInInfoDialog(){
	//记录修改前的订单号
	$("#orderId2_beforModify").attr("value",checkInRoomObj.checkInInfo.orderId);
	//状态
	$("#state2").html(checkInRoomObj.roomColor.description+" ("+checkInRoomObj.checkInInfo.outTime+")到期");
	//订单
	if(checkInRoomObj.checkInInfo.orderId==0)
		$("#orderId2").attr("value","无");
	else
		$("#orderId2").attr("value",checkInRoomObj.checkInInfo.orderId);
	$("#name2").attr("value",checkInRoomObj.checkInInfo.name);
	$("#numberPeople2").attr("value",checkInRoomObj.checkInInfo.numberPeople);
	$("#phoneNumber2").attr("value",checkInRoomObj.checkInInfo.phoneNumber);
	$("#description2").attr("value",checkInRoomObj.checkInInfo.description);
	$("#enterTime2").html(checkInRoomObj.checkInInfo.enterTime);
	$("#balance2").html(checkInRoomObj.checkInInfo.allAddBalance.toFixed(2));
	$("#leftMoney2").html(checkInRoomObj.leftMoney.toFixed(2));
	//房费显示
	if(checkInRoomObj.countByHour==true)
		$("#enterCount2").html("已入住"+checkInRoomObj.enterCount+"小时");
	else
		$("#enterCount2").html("产生"+checkInRoomObj.enterCount+"天房费");
	
	//如果是小时房  便显示修改成正常入住的select，不显示 订单关联一栏,不显示续住按钮
	if(checkInRoomObj.checkInInfo.hourRoom == true){
		document.getElementById("updateCheckInInfo_isHourRoom_tr").style.display = "table-row";
		document.getElementById("updateCheckInInfo_orderId_tr").style.display = "none";
		document.getElementById("continueCheckInButton").disabled=true;
		$("#continueCheckInButton").addClass(" ui-state-disabled");
		
		document.getElementById("updateCheckInInfo_cif_isHourRoom").value = -1;
	}
	else{
		document.getElementById("updateCheckInInfo_orderId_tr").style.display = "table-row";
		document.getElementById("updateCheckInInfo_isHourRoom_tr").style.display = "none";
		document.getElementById("continueCheckInButton").disabled=false;
		$("#continueCheckInButton").removeClass("ui-state-disabled");
		//这一步是必要的。。
		document.getElementById("updateCheckInInfo_cif_isHourRoom").value = false;
	}
	//如果是 干净房 和 已入住房
	$("#updateCheckInInfoDialog").dialog({width:450,height:470});
	$( "#updateCheckInInfoDialog" ).dialog( "option", "title", checkInRoomObj.room.id +"(" +checkInRoomObj.roomPrice.description+")--"+checkInRoomObj.roomPrice.price+"/天--"+checkInRoomObj.roomPrice.hourPrice+"/时");

}

//////////////////////////// 修改
//修改信息校验，以及将房间号填入表单
function updateCheckInInfoAvalidate(){
	
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	
	
	var permission =  true;
	
	//校验入住人数
	var numberPeople = document.getElementById("numberPeople2");
	if(numberPeople.value.match(/\D/) != null){
		document.getElementById("updateCheckInInfo_warn_numberPeople").innerHTML="*格式不正确";
		permission =  false;
	}
	else if(numberPeople.value==""){
		document.getElementById("updateCheckInInfo_warn_numberPeople").innerHTML="*必填！";
		permission =  false;
	}
	else
		document.getElementById("updateCheckInInfo_warn_numberPeople").innerHTML="";
	
	
	//小时房选择校验
	var cif_isHourRoom = document.getElementById("updateCheckInInfo_cif_isHourRoom");
	if(cif_isHourRoom.value==-1){
		document.getElementById("updateCheckInInfo_warn_isHourRoom").innerHTML="*必填";
		permission =  false;
	}
	else
		document.getElementById("updateCheckInInfo_warn_isHourRoom").innerHTML="";
	
	
	
	//姓名 校验
	var name = document.getElementById("name2");
	if(name.value==""){
		document.getElementById("updateCheckInInfo_warn_name").innerHTML="*必填";
		permission =  false;
	}
	else
		document.getElementById("updateCheckInInfo_warn_name").innerHTML="";
	
	
	//校验 orderId 如果非法将直接赋值为0
	var orderId =  document.getElementById("orderId2");
	if(orderId.value.match(/\D/) !=null || orderId.value=="")
		orderId.value = 0;
	
	
	
	if(permission == true){
		//记录当前房间号
		document.getElementById("updateCheckInInfo_cif_rId").value = checkInRoomObj.room.id;
		isOperating = true;
	}
	
	
	return permission ;
} 



//////////////////////////// 换房
//将房间号以及余额填入退房表单
function switchRoomAvalidate(){
	
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	
	isOperating = true;
	return true;
}



////////////////////////////账单

function balanceButtonClick(){
	
	//传递 房间 id 以及 入住信息号ss
	dialogClose("updateCheckInInfoDialog");
	window.open("balanceDetail.jsp?roomId="+checkInRoomObj.room.id);
}


////////////////////////////续住
function openContinueCheckIn(){
		//打开对话框
	$("#continueCheckInDialog").dialog({width:350,height:180,modal: true});
	$( "#continueCheckInDialog").dialog( "option", "title", "续住");
}
//续住天数校验 ，将房间号填入续住表单
function continueCheckInAvalidate(){
	
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	
	var reg = /^-?\d+$/;
	if(reg.test($("#continueDays").val()) == false){
		$("#continueDays_warn").html("*输入格式不正确");
		return false;
	}
	$("#continueDays_warn").html("*");
	
	//获取当前房间号
	$("#continueCheckIn_rId").val(checkInRoomObj.room.id);
	
	isOperating = true;
	return true;
}





/////////////////////////////////////////////////////////////////////////房间添加
//房间添加校验
function addRoomAvalidate(){
	
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	
	var permission = true;
	
	//校验id
	var id = document.getElementById("addRoom_room_id").value;
	if(id.match(/\D/) != null){
		permission = false;
		$("#addRoom_warn_id").html("*填入整数");
	}
	else if(id == ""){
		permission = false;
		$("#addRoom_warn_id").html("*必填");
	}
	else
		$("#addRoom_warn_id").html("");
		
	
	//校验房间类型
	var type = document.getElementById("roomType_list_addRoom").value;
	if(type == -1){
		$("#addRoom_warn_type").html("*必填");
		permission = false;
	}
	else
		$("#addRoom_warn_type").html("");
	
	
	if(permission == true)
		isOperating = true;
	
	return permission;
	
}








/////////////////////////////////////////////////////////////////////////房间类型列表请求

//保存 房型列表请求 类型  1代表来自添加房间请求 2代表添加订单请求 3.代表删除房间类型 4.修改房间种类，5.订单请求 6.修改房间类型
var requestRoomTypeListComFrom;
///订单 房间 添加 请求 前请求 RoomTypeList 同时记录 请求来源，以便知道后何去何从
function menueClickRequestRoomTypeList(requestId){
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	
	requestRoomTypeListComFrom = requestId;
	$("#requestRoomTypeList_submit").click();
	
	isOperating = true;
}





$(function(){
	
	//入住
	dojoSubscribe("/checkInResult","checkInDialog");
	//添加房间
	dojoSubscribe("/addRoomResult","addRoomDialog");
	//改变房间类型
	dojoSubscribe("/requestChangeRoomTypeResult","changeRoomDialog");
	//改变房间状态--到脏房
	dojoSubscribe("/requestChangeRoomStateFromCleanToDirtyResult","checkInDialog");
	//改变房间状态
	dojoSubscribe("/requestChangeRoomStateResult","changeRoomDialog");
	//入住信息修改
	dojoSubscribe("/updateCheckInInfoResult","updateCheckInInfoDialog");
	//换房
	dojoSubscribe("/switchRoomResult","updateCheckInInfoDialog");
	//续住
	dojoSubscribe("/continueCheckInResult","continueCheckInDialog");

	//房间类型列表
	dojo.event.topic.subscribe("/requestRoomTypeListResult",function myfunction(data,type,request){
		
		if(type =='load'){
			
			switch(requestRoomTypeListComFrom)
			{
			
			//添加房间对话框
			case 1:
				
				jsonToRoomTypeListSelect("addRoom_room_type_td","roomType_list_addRoom",data);
				
				$("#addRoomDialog").dialog({width:450,height:260,modal:true});
				$( "#addRoomDialog" ).dialog( "option", "title","添加房间");
	
				$("#roomType_list_addRoom").attr("name","type");
				break;
			
				
			//添加订单对话框
			case 2:
				//将select 加入 表单
				jsonToRoomTypeListSelect("addOrder_od_roomType_td","roomType_list_addOrder",data);
				$("#addOrderDialog").dialog({width:450,height:460,modal:true});
				$( "#addOrderDialog" ).dialog( "option", "title","添加订单");
				$("#roomType_list_addOrder").attr("name","od.roomType");
				break;
			//删除 房间类型	
			case 3:
				jsonToRoomTypeListSelect("delRoomType-select","roomType_list_delRoomType",data);
				$("#delRoomTypeDialog").dialog({width:280,height:190,modal:true});
				$( "#delRoomTypeDialog" ).dialog( "option", "title","删除房间类型");
				//指定select 名字
				$("#roomType_list_delRoomType").attr("name","type");
				
				break;
			//修改房间类型	
			case 4:
				
				jsonToRoomTypeListSelect("updateRoomType-select","roomType_list_updateRoomType",data);
				$("#roomType_list_updateRoomType").attr("name","type");
				openUpdateRoomTypeDialog();
				break;
				
			//订单修改	 显示 并填写
			case 5:
				jsonToRoomTypeListSelect("updateOrderRoomType_td","roomType_list_updateOrder",data);
				
				$("#roomType_list_updateOrder").attr("name","od.roomType");
				
				$("#roomType_list_updateOrder").val(orderObj.roomType);
				
				$("#accessOneOrderDialog").dialog({width:470,height:460});
				$( "#accessOneOrderDialog" ).dialog( "option", "title","修改订单");
				
				break;
				
			//修改某个房间
			case 6:
				
				//清空 select
				$("#changeRoomState_select").empty();
				
				//脏房
				if(obj.roomColor.id == 2){
					//添加相应select item
					$("#changeRoomState_select")
						.append("<option value=1>干净房</option>")
						.append("<option value=6>不可用房</option>");
					if(userLevel <=0)
						$("#changeRoomState_select").append("<option value=0>删除房间</option>");
				}
				//不可用房 obj.roomColor.id == 6
				else{
					$("#changeRoomState_select")
						.append("<option value=2>脏房</option>")
						.append("<option value=1>干净房</option>");
						if(userLevel <=0)
							$("#changeRoomState_select").append("<option value=0>删除房间</option>");
				}
				
				//填写 类型列表
				jsonToRoomTypeListSelect("changeRoomType_list_td","changeRoomType_list",data);
				
				$("#changeRoomType_list").attr("name","changeRoomType");
				$("#changeRoomDialog").dialog({width:320,height:270});
				$("#changeRoomDialog" ).dialog( "option", "title",obj.room.id +"(" +obj.roomPrice.description+")" + "修改房间");
				
				break;
				
			}
			
			//处理完成 isOperating 置为false
			isOperating =false;
		}
		
	});
});







