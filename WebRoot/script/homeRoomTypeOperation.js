
//////////////////////////////////////////////////////////房间类型操作 增 改
//添加房间 类型
function openAddRoomTypeDialog(){
	$("#addRoomTypeDialog").dialog({width:380,height:240,modal:true});
	$("#addRoomTypeDialog" ).dialog( "option", "title","添加房间类型");
}

function addRoomTypeAvalidate(){
	
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	
	
	var permission =true;
	var value;
	//校验 description 
	value =	$("#requestAddRoomType_description").val();
	if(value ==""){
		permission = false;
		$("#requestAddRoomType_description_warn").html("*必填");
	}
	else
		$("#requestAddRoomType_description_warn").html("");
	
	
	//校验房价  整数 必填
	value =	$("#requestAddRoomType_price").val();
	if(value ==""){
		permission = false;
		$("#requestAddRoomType_price_warn").html("*必填");
	}
	else if(value.match(/\D/) != null){
		permission = false;		
		$("#requestAddRoomType_price_warn").html("*请填写整数");
	}
	else
		$("#requestAddRoomType_price_warn").html("");
	
	//校验小时房价  整数 必填
	value =	$("#requestAddRoomType_hourPrice").val();
	if(value ==""){
		permission = false;
		$("#requestAddRoomType_hourPrice_warn").html("*必填");
	}
	else if(value.match(/\D/) != null){
		permission = false;
		$("#requestAddRoomType_hourPrice_warn").html("*请填写整数");
	}
	else
		$("#requestAddRoomType_hourPrice_warn").html("");
		
	
	if(permission == true)
		isOperating = true;
	return permission;
}

dojo.event.topic.subscribe("/addRoomTypeResult",function myfunction(data,type,request){
	
	if(type =='load'){
		getResult(data,'addRoomTypeDialog');
	}
});


function openUpdateRoomTypeDialog(){
	$("#updateRoomTypeDialog").dialog({width:410,height:270,modal:true});
	$("#updateRoomTypeDialog" ).dialog( "option", "title","修改房间类型");
	
}
//修改房间类型
function updateRoomTypeAvalidate(){
	
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	
	
	var permission =true;
	var value;
	
	//校验房间类型
	var type = document.getElementById("roomType_list_updateRoomType").value;
	if(type == -1){
		$("#requestUpdateRoomType_type_warn").html("*必填");
		permission = false;
	}
	else
		$("#requestUpdateRoomType_type_warn").html("");
	
	//校验 description 
	value =	$("#requestUpdateRoomType_description").val();
	if(value ==""){
		permission = false;
		$("#requestUpdateRoomType_description_warn").html("*必填");
	}
	else
		$("#requestUpdateRoomType_description_warn").html("");
	
	
	//校验房价
	value =	$("#requestUpdateRoomType_price").val();
	if(value ==""){
		permission = false;
		$("#requestUpdateRoomType_price_warn").html("*必填");
	}
	else if(value.match(/\D/) != null){
		permission = false;		
		$("#requestUpdateRoomType_price_warn").html("*请填写整数");
	}
	else
		$("#requestUpdateRoomType_price_warn").html("");
	
	//校验小时房价
	value =	$("#requestUpdateRoomType_hourPrice").val();
	if(value ==""){
		permission = false;
		$("#requestUpdateRoomType_hourPrice_warn").html("*必填");
	}
	else if(value.match(/\D/) != null){
		permission = false;
		$("#requestUpdateRoomType_hourPrice_warn").html("*请填写整数");
	}
	else
		$("#requestUpdateRoomType_hourPrice_warn").html("");
		
	
	if(permission == true)
		isOperating = true;
	return permission;
	
}

dojo.event.topic.subscribe("/UpdateRoomTypeResult",function myfunction(data,type,request){
	
	if(type =='load'){
		getResult(data,'updateRoomTypeDialog');
	}
});

function delRoomTypeAvalidate(){
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;
	if($("#roomType_list_delRoomType").val()==-1)
		return false;
	if(confirm("确定要删除该房型？删除后将不可恢复，该类型房间的订单也将删除！") == false)
		return false;
	if(window.prompt("请输入验证  ","") != "sa_sa"){
		alert("验证失败");
		return false;
	}
		
	isOperating = true;
	return true;
}

dojo.event.topic.subscribe("/delRoomTypeResult",function myfunction(data,type,request){
	
	if(type =='load'){
		getResult(data,'delRoomTypeDialog');
	}
});
