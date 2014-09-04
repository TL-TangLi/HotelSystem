
// 判断现在时间 是否为凌晨  0：00 --- 6：00 之间
function isBeforDawn(){
	var date = new Date();
	var hour = date.getHours();
	if(hour < 6)
		return true;
	return false;
}

/// 日期对象转换成  str
function dateToStr(date){
	var str = date.getFullYear()+"-";
	
	if(date.getMonth()<9)
		str += "0"+(date.getMonth()+1)+"-";
	else
		str += (date.getMonth()+1)+"-";
	
	if(date.getDate()<10)
		str += "0"+date.getDate();
	else
		str += date.getDate();
	
	return str;
}

// 判断 日期是否为昨天的日期。
function dateIsYesterDay(str){
	//构建一个昨日 date对象
	var date = new Date();
	date.setDate(date.getDate() -1); // 系统会自动转换
	
	if(dateToStr(date) == str)
		return true;
	
	return false;
}



//////////////////////////////////////////////////////////////////////////////order 
//请求（包含房间类型列表请求home.js menueClickRequestRoomTypeList函数   先请求 订单，再请求房间列表 ，最后填写表单）
var orderObj;
function requestOrder(oid){

	//请求订单
	$.ajax({ 
		   url : "requestOrderAction", 
		   data:	{
			   "orderId":oid
			   },
		   dataType:"json",
		   type : "GET", 
		   success : function(data){
			   
			    orderObj = eval(data.jsonOrder)[0];
			   	$("#updateOrder_orderTime").html(orderObj.orderTime);
				$("#updateOrder_name").val(orderObj.name);
				$("#updateOrder_extendHour").val(orderObj.extendHour);
				$("#updateOrder_phoneNumber").val(orderObj.phoneNumber);
				$("#updateOrder_enterDate").val(orderObj.enterDate);
				$("#updateOrder_description").val(orderObj.description);
				$("#updateOrder_days").val(orderObj.days);
				$("#updateOrder_id").val(orderObj.id);
				
				
				var enableCheckIn = false;
				
				if(orderObj.info == 0){
					//今天 非黎明 就可以入住
					if(orderObj.enterDate == todayToString() && isBeforDawn() == false)
						enableCheckIn = true;
					//昨天 黎明时刻可以入住
					else if(dateIsYesterDay(orderObj.enterDate)==true && isBeforDawn() == true)
						enableCheckIn = true;
				}
				
				///如果订单info不是0 即订单已经入住或者放弃  或者入住日期不是今天，那么不能入住
				if(enableCheckIn == false){
					$("#chooseRoomToCheckIn_Button").attr("disabled",true);
					$("#chooseRoomToCheckIn_Button").addClass(" ui-state-disabled");
				}
				else{
					$("#chooseRoomToCheckIn_Button").attr("disabled",false);
					$("#chooseRoomToCheckIn_Button").removeClass(" ui-state-disabled");

				}
				//已入住的订单不能修改  并且不显示改变 状态的一栏
				if(orderObj.info ==1){
					$("#updateOrder_submit").attr("disabled",true);
					$("#updateOrder_submit").addClass(" ui-state-disabled");
					document.getElementById("updateOrder_info_tr").style.display = "none";
					
				}
				else{
					$("#updateOrder_submit").attr("disabled",false);
					$("#updateOrder_submit").removeClass(" ui-state-disabled");
					document.getElementById("updateOrder_info_tr").style.display = "table-row";
					$("#updateOrder_info").val(orderObj.info);
				}
				//请求房间类型列表 一定要等requestOrder 请求完后才行
				menueClickRequestRoomTypeList(5);
		   }
	});
	
	
}

dojo.event.topic.subscribe("/accessOneOrderResult",function myfunction(data,type,request){
	if(type =='load'){
		document.getElementById("accessOneOrderDialog").innerHTML=data;
		$("#accessOneOrderDialog").dialog({width:450,height:400});
		$( "#accessOneOrderDialog" ).dialog( "option", "title","订单操作");
		
		//处理完成 isOperating 置为false
		isOperating =false;
	}
		
});






//////////////////////////////////////////////////////////////////////////////order 删 改
function updateOrderAvalidate(){
	
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true ){
		return false;
	}
	
	var permission = true;
	
	
	//校验姓名
	var name = $("#updateOrder_name").val();
	if(name==""){
		permission = false;		
		$("#updateOrder_warn_name").html("*必填");
	}
	else
		$("#updateOrder_warn_name").html("");
	
	
	//校验房型
	if($("#roomType_list_updateOrder").val() == -1){
		permission = false;		
		$("#updateOrder_warn_roomType").html("*必填");
	}
	else
		$("#updateOrder_warn_roomType").html("");
	
	
	//校验订单天数
	var days= document.getElementById("updateOrder_days").value;
	if(days==""){
		permission = false;		
		$("#updateOrder_warn_days").html("*必填");
	}
	else if(days.match(/\D/)!= null || days == 0){
		permission = false;		
		$("#updateOrder_warn_days").html("*请输入大于0的整数");
	}
	else
		$("#updateOrder_warn_days").html("");
	
	
	
	if(permission == true )
		isOperating = true;
	
	return permission;
	
}

function delOrderAvalidate(){
	
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true )
		return false;

	if(confirm("确认要删除？删除后将不可恢复！")==false)
		return false;
	
	$("#delOrder_id").val(orderObj.id);
	
	isOperating = true; 
	return true;
}

dojo.event.topic.subscribe("/delAndUpdateOrderResult",function somefunction(data,type,request){
	if(type =='load'){
		getResult(data,'accessOneOrderDialog');
	}
});





//////////////////////////////////////////////////////////////////////////////order 添加

function beforeAddOrder(){
	
	$("#addOrUpdateOrder").val("0");
}
//添加订单校验
function addOrderAvalidate(){
	
	//如果表单已经提交正在处理，那么不要重复提交表单
	if(isOperating == true ){
		return false;
	}
	
	var permission = true;
	
	//校验房间类型
	if(document.getElementById("roomType_list_addOrder").value == -1){
		permission = false;
		$("#addOrder_warn_roomType").html("*必填");
	}
	else
		$("#addOrder_warn_roomType").html("");
	
	//校验入住日期
	if(document.getElementById("addOrder_od_enterDate").value==""){
		permission = false;		
		$("#addOrder_warn_enterDate").html("*必填");
	}
	else
		$("#addOrder_warn_enterDate").html("");
	
	//校验姓名
	if(document.getElementById("addOrder_od_name").value==""){
		permission = false;		
		$("#addOrder_warn_name").html("*必填");
	}
	else
		$("#addOrder_warn_name").html("");
	
	
	
	//校验订单天数
	var days= document.getElementById("addOrder_od_days").value;
	if(days==""){
		permission = false;		
		$("#addOrder_warn_days").html("*必填");
	}
	else if(days.match(/\D/)!= null || days == 0){
		permission = false;		
		$("#addOrder_warn_days").html("*请输入大于0的整数");
	}
	else
		$("#addOrder_warn_days").html("");
	
	//校验订房间数
	var rooms= document.getElementById("addOrder_od_rooms").value;
	if(rooms==""){
		permission = false;		
		$("#addOrder_warn_rooms").html("*必填");
	}
	else if(rooms.match(/\D/)!= null || rooms == 0){
		permission = false;		
		$("#addOrder_warn_rooms").html("*请输入大于0的整数");
	}
	else
		$("#addOrder_warn_rooms").html("");
	
	//extendHour 处理
	var extendHour =  document.getElementById("addOrder_od_extendHour");
	if(extendHour.value.match(/\D/) !=null || extendHour.value=="")
		extendHour.value = 0;
	
	
	if(permission == true )
		isOperating = true;
	
	return permission;
}

dojo.event.topic.subscribe("/addOrderResult",function myfunction(data,type,request){
	
	if(type =='load')
		getResult(data,'addOrderDialog');
});

