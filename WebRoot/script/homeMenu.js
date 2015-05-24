//依次对应为  (第一个无用),homeMenu（1）, orderMenu（2） , todayInfoMenu（3）,checkInInfoMenu（4）,
var isOpen = [ false, false, false, true ,false]; // 记录菜单是否被打开 （在toogle 调用时同步设定  ）// 每个menu 对应一个 索引 目的是记录是否打开
var hoverMenuId = null; 							// 如果鼠标停放在 menu 或者menu对应的头上，那么记录这个menuId

var isInfoMenuPin = true;							 // 记录今日菜单是否被固定了

function bodyInitial() {

	$("#roomMenu").toggle();
	$("#orderMenu").toggle();
	
	$("#checkInInfoMenu").toggle();
}

function managerExit() {
	window.location.href = "requestLoginOffAction.action";
	// isOperating = true;
}

function todayInfoPinToggle() {
	// 置为固定状态
	if (isInfoMenuPin == false) {
		isInfoMenuPin = true;
		$("#todayInfoPin").button({
			icons : {
				primary : "ui-icon-pin-s"
			},
			text : false
		});
	}
	// 置为未固定状态
	else {
		isInfoMenuPin = false;
		$("#todayInfoPin").button({
			icons : {
				primary : "ui-icon-pin-w"
			},
			text : false
		});
	}
}

function openMenu(id, n) {
	if (isOpen[n] == true) {
		return;
	}
	$("#" + id).toggle(200);
	isOpen[n] = true;

};

function delaymenuClose(id, n) {

	// 如果今日信息 菜单被固定了 ，那么不要关闭 了！
	if (n == 3 && isInfoMenuPin == true)
		return;
	setTimeout(function() {
		menuClose(id, n);
	}, 300);
};

function menuClose(id, n) {

	if (isOpen[n] == false || hoverMenuId == id)
		return;
	else {
		isOpen[n] = false;
		$("#" + id).toggle(200);
	}

}

// 今日信息上 关闭打开的对话框
function closeAllDialog() {

	if (document.getElementById("requestTodayRoomStatisticsDialog").style.display != "none")
		$("#requestTodayRoomStatisticsDialog").dialog("destroy");

	if (document.getElementById("queryRoomDialog").style.display != "none")
		$("#queryRoomDialog").dialog("destroy");

	if (document.getElementById("todayOrderDialog").style.display != "none")
		$("#todayOrderDialog").dialog("destroy");

	if (document.getElementById("queryEmptyRoomDialog").style.display != "none")
		$("#queryEmptyRoomDialog").dialog("destroy");

}

// 打开订单查询
function queryOrderClick() {
	var form = document.createElement("form");
	form.action = "queryOrderAction";
	form.namespace = "/main";
	form.target = "_blank";
	form.executeScript = true;
	form.submit();

}



/////////////////////////////////////////////////////////////////////////今日order 查询

// //记录 请求订单的 来源，1 代表来自干净房請求入住，2代表来自已住房修改,3代表请求今日待住  4代表请求今日新增
var requestOrdercomeFrom;
// 是否同意请求订单（修改订单号的警告结果）
var requestOrderPermission;
// 保存请求订单对应的房间类型
var roomDescription;
// 记录访问者，以便访问后 知道在哪里填写订单号
function beforAccessOrder(n) {

	if (document.getElementById("todayOrderDialog").style.display != "none"
			&& requestOrdercomeFrom == n) {
		$("#todayOrderDialog").dialog("destroy");
		return;
	}
	// 记录 请求订单的 来源，1 代表来自入住，2代表来自CheckInInfo 更新 其他代表今日订单分类查询
	requestOrdercomeFrom = n;
	if (requestOrdercomeFrom == 2) {
		requestOrderPermission = confirm("警告，如果关联新订单，会将该房间续住订单预定的天数！");
	}
	if (n > 2) {
		$("#accessOrder_submit").click();
	}

}

// 填写 订单请求 的form 规定如不指定某过滤器，int 置为 -1 string 置为 ""
function accessOrderAvalidate() {

	// 如果表单已经提交正在处理，那么不要重复提交表单
	if (isOperating == true)
		return false;

	// 如果用户中途选择取消关联，就不用提交 表单了
	if (requestOrdercomeFrom == 2 && requestOrderPermission == false)
		return false;

	// 两件事 1填写 四个过滤器，2.填写访问完action后 定位的资源名！！
	switch (requestOrdercomeFrom) {
	// 入住订单关联 (当日 某类型的 可住订单)
	case 1:
		roomDescription = cleanRoomObj.roomPrice.description;
		$("#accessOrder_type").val(cleanRoomObj.room.type);
		$("#accessOrder_info").val("0");
		$("#accessOrder_enterDate").val("now");
		$("#accessOrder_orderDate").val("");
		$("#accessOrder_orientaionName")
				.val("requestOrderForCheckInResult.jsp");
		break;
	// 更新房间订单关联(当日 某类型的 可住订单)
	case 2:
		roomDescription = checkInRoomObj.roomPrice.description;
		$("#accessOrder_type").val(checkInRoomObj.room.type);
		$("#accessOrder_info").val("0");
		$("#accessOrder_enterDate").val("now");
		$("#accessOrder_orderDate").val("");
		$("#accessOrder_orientaionName")
				.val("requestOrderForCheckInResult.jsp");
		break;

	// 请求当日待住订单（当日 所有 有效订单）
	case 3:
		$("#accessOrder_type").val("-1");
		$("#accessOrder_info").val("0");
		$("#accessOrder_enterDate").val("now");
		$("#accessOrder_orderDate").val("");
		$("#accessOrder_orientaionName").val("requestOrderResult.jsp");
		break;
	// 当日新增订单 （当日 添加的所有订单）
	case 4:
		$("#accessOrder_type").val("-1");
		$("#accessOrder_info").val("-1");
		$("#accessOrder_enterDate").val("");
		$("#accessOrder_orderDate").val("now");
		$("#accessOrder_orientaionName").val("requestOrderResult.jsp");
		break;
	default:
		break;

	}

	isOperating = true;
	return true;
}

dojo.event.topic.subscribe("/accessOrderResult",function myfunction(data, type, request) {

if (type == 'load') {

	// 今日订单访问
	switch (requestOrdercomeFrom) {
	case 1:
	case 2:
		document.getElementById("todayOrderDialogCheckIn").innerHTML = data;
		$("#todayOrderDialogCheckIn").dialog({
			width : 1000,
			height : 500,
			modal : true
		});
		$("#todayOrderDialogCheckIn").dialog(
				"option",
				"title",
				"今日所有可用" + roomDescription + "订单"
						+ "--双击选择");
		break;
	case 3:
		document.getElementById("todayOrderDialog").innerHTML = data;
		$("#todayOrderDialog").dialog({
			width : 1200,
			height : 500
		});
		$("#todayOrderDialog").dialog("option", "title",
				"今日所有待入住订单");
		$("#todayOrderDialog").dialog("option", "position",
				{
					my : "left top",
					at : "left bottom",
					of : $("#todayInfoHead")
				});
		break;

	case 4:
		document.getElementById("todayOrderDialog").innerHTML = data;
		$("#todayOrderDialog").dialog({
			width : 1200,
			height : 500
		});
		$("#todayOrderDialog").dialog("option", "title",
				"今日新增订单");
		$("#todayOrderDialog").dialog("option", "position",
				{
					my : "left top",
					at : "left bottom",
					of : $("#todayInfoHead")
				});

		break;

	}

	// 处理完成 isOperating 置为false
		isOperating = false;
	}

});

// 选择订单之后 填写 订单号到表单
function afterSelectOrder(n) {

	switch (requestOrdercomeFrom) {
	// 入住订单关联
	case 1:
		$("#checkIn_cif_orderId").attr("value", n);
		break;
	// 更新房间订单关联
	case 2:
		$("#orderId2").attr("value", n);
		break;
	default:
		break;

	}

	$("#todayOrderDialogCheckIn").dialog("destroy");
}







/////////////////////////////////////////////////////////////////////////今日Room （空房）
//保存请求 查询房间来源 1。今日信息请求 （所有空房）2.订单入住 3. 换房

var requestQueryEmptyRoomComFrom;

function beforeRequestEmptyRoom(n) {

	// 如果表单已经提交正在处理，那么不要重复提交表单
	if (isOperating == true)
		return false;

	if (document.getElementById("queryEmptyRoomDialog").style.display != "none") {
		$("#queryEmptyRoomDialog").dialog("destroy");
		return;
	}

	requestQueryEmptyRoomComFrom = n;
	switch (requestQueryEmptyRoomComFrom) {
	// 所有可用空房
	case 1:
		$("#queryEmptyRoom_type").val("-1");
		//8 代表可用 详见  StaticClass  ROOM_STATE  字段
		$("#queryEmptyRoom_state").val("8");
		break;
	// 某类型的干净房
	case 2:
		$("#queryEmptyRoom_type").val(orderObj.roomType);
		$("#queryEmptyRoom_state").val("1");
		break;
		
	case 3:
		//当前类型
		$("#queryEmptyRoom_type").val(checkInRoomObj.room.type);
		//1 代表干净房
		$("#queryEmptyRoom_state").val("1");
		
		break;
	}

	isOperating = true;

	$("#requestQueryEmptyRoom_submit").click();
}

dojo.event.topic.subscribe("/requestQueryEmptyRoomResult",function myfunction(data, type, request) {
	if (type == 'load') {
		switch (requestQueryEmptyRoomComFrom) {
		case 1:
			document.getElementById("queryEmptyRoomDialog").innerHTML = data;
			$("#queryEmptyRoomDialog").dialog({
				width : 380,
				height : 380
			});
			$("#queryEmptyRoomDialog").dialog("option",
					"title", "所有空房");
			$("#queryEmptyRoomDialog").dialog("option",
					"position", {
						my : "center top",
						at : "center bottom",
						of : $("#todayInfoHead")
					});
			break;
		case 2:
			document.getElementById("queryEmptyRoomForCheckInDialog").innerHTML = data;
			$("#queryEmptyRoomForCheckInDialog").dialog({
				width : 380,
				height : 380,
				modal : true
			});
			$("#queryEmptyRoomForCheckInDialog").dialog("option", "title", "选择干净房入住");
			break;
			
		case 3:
			document.getElementById("queryEmptyRoomForSwitchDialog").innerHTML = data;
			$("#queryEmptyRoomForSwitchDialog").dialog({
				width : 380,
				height : 380,
				modal : true
			});
			$("#queryEmptyRoomForSwitchDialog").dialog("option", "title", "所有可换房");
			
			break;
		}

		//处理完成 isOperating 置为false
		isOperating = false;
	}
});

function aterSelectRoomForCheckIn(id, n) {
	switch (requestQueryEmptyRoomComFrom) {
	case 1:
		accessRoom(id);
		break;
	case 2:
		accessRoom(id);
		//填写订单
		$("#checkIn_cif_orderId").attr("value", orderObj.id);
		//关闭对话框
		$("#queryEmptyRoomForCheckInDialog").dialog("destroy");
		$("#accessOneOrderDialog").dialog("destroy");
		break;
		
	case 3:
		//填写 目标房间 id
		$("#switchRoom_targetId").attr("value", id);
		//填写 现在房间 id
		$("#switchRoom_roomId").attr("value",checkInRoomObj.room.id);
		//填写 说明信息
		$("#switchRoom_description_id").html("你正在进行的 换房操作：" + "从" + checkInRoomObj.room.id+ "换到" + id +
				"，请仔细核对，换房后客户信息将转移到新房间,但是账目统计中，之前产生的房费、消费以及入账都会记录在以前的房间上！，。是否继续进行？");
		
		//关闭对话框
		$("#queryEmptyRoomForSwitchDialog").dialog("destroy");
		//打开换房对话框
		$("#switchRoomDialog").dialog({
			width : 230,
			height : 250,
			modal : true,
			title : "换房提示"
		});
		break;
	}

}





/////////////////////////////////////////////////////////////////////////今日Room （在住）

// 保存请求 查询房间来源 1。代表请求新入住 2.代表请求待退房 3.代表今日所有在住
var requestQueryRoomComFrom;

function beforQueryRoom(n) {

	if (document.getElementById("queryRoomDialog").style.display != "none"
			&& requestQueryRoomComFrom == n) {
		$("#queryRoomDialog").dialog("destroy");
		return;
	}

	requestQueryRoomComFrom = n;
	$("#requestQueryRoom_submit").click();
}

// 填写请求查询房间表单 规定如不指定某过滤器，int 置为 -1 string 置为 ""
function requestQueryRoomAvalidate() {

	// 如果表单已经提交正在处理，那么不要重复提交表单
	if (isOperating == true)
		return false;
	switch (requestQueryRoomComFrom) {
	// 请求 新入住
	case 1:
		$("#queryRoom_type").val("-1");
		$("#queryRoom_enterDate").val("now");
		$("#queryRoom_outDate").val("");
		break;
	// 请求待退房
	case 2:
		$("#queryRoom_type").val("-1");
		$("#queryRoom_enterDate").val("");
		$("#queryRoom_outDate").val("now");
		break;
	// 所有在住
	case 3:
		$("#queryRoom_type").val("-1");
		$("#queryRoom_enterDate").val("");
		$("#queryRoom_outDate").val("");
		break;
	// //所有空房
	// case 4:
	// $("#queryRoom_type").val("-1");
	// $("#queryRoom_enterDate").val("");
	// $("#queryRoom_outDate").val("");
	// break;
	}

	isOperating = true;
	return true;

}

dojo.event.topic.subscribe("/requestQueryRoomResult", function myfunction(data,
		type, request) {

	if (type == 'load') {
		switch (requestQueryRoomComFrom) {
		// 今日新入
		case 1:
			document.getElementById("queryRoomDialog").innerHTML = data;
			$("#queryRoomDialog").dialog({
				width : 1150,
				height : 500
			});
			$("#queryRoomDialog").dialog("option", "title", "今日新入住房");
			$("#queryRoomDialog").dialog("option", "position", {
				my : "left top",
				at : "left bottom",
				of : $("#todayInfoHead")
			});
			break;
		// 今日待退房
		case 2:
			document.getElementById("queryRoomDialog").innerHTML = data;
			$("#queryRoomDialog").dialog({
				width : 1150,
				height : 500
			});
			$("#queryRoomDialog").dialog("option", "title", "今日待退房");
			break;
		// 今日所有在住
		case 3:
			document.getElementById("queryRoomDialog").innerHTML = data;
			$("#queryRoomDialog").dialog({
				width : 1150,
				height : 500
			});
			$("#queryRoomDialog").dialog("option", "title", "今日所有在住");
			break;
		// 今日所有空房
		// case 4:
		// document.getElementById("queryRoomDialog").innerHTML = data;
		// $("#queryRoomDialog").dialog({width:1150,height:500});
		// $( "#queryRoomDialog" ).dialog( "option", "title","今日所有空房");
		// break;
		}
		$("#queryRoomDialog").dialog("option", "position", {
			my : "left top",
			at : "left bottom",
			of : $("#todayInfoHead")
		});
		// 处理完成 isOperating 置为false
		isOperating = false;
	}
});








/////////////////////////////////////////////////////////////////////////今日Room （统计）
function beforRequestTodayRoomStatistics() {

	if (document.getElementById("requestTodayRoomStatisticsDialog").style.display != "none") {
		$("#requestTodayRoomStatisticsDialog").dialog("destroy");
		return;
	}

	$("#requestTodayRoomStatistics_submit").click();
}
// 填写表单提交
function requestTodayRoomStatisticsAvalidate() {

	// 如果表单已经提交正在处理，那么不要重复提交表单
	if (isOperating == true)
		return false;


	isOperating = true;
	return true;
}

dojo.event.topic.subscribe("/requestTodayRoomStatisticsResult", function myfunction(
		data, type, request) {

	if (type == 'load') {

		document.getElementById("requestTodayRoomStatisticsDialog").innerHTML = data;
		$("#requestTodayRoomStatisticsDialog").dialog({
			width : 780,
			height : 280
		});
		$("#requestTodayRoomStatisticsDialog").dialog("option", "title", "统计");

		$("#requestTodayRoomStatisticsDialog").dialog("option", "position", {
			my : "center top",
			at : "center bottom",
			of : $("#todayInfoHead")
		});
		// 处理完成 isOperating 置为false
		isOperating = false;
	}
});



