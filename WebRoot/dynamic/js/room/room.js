///////////////////////////////////////////////////////////initial

var freshFunctionAfterModify; //这是一个刷新函数，需要在jsp加载时下定义. 

var freshFunctionAfterDelete; //在删除房间后的刷新函数

$(function() {
	$("button").button();

	//修改房间表单验证 ajax 提交  valid.form 是一个事件。
	$('#entityRoomForm').bind('valid.form', function() {
		$.ajax({
			url : getRootPath()+'/room/roomModify.do',
			type : 'POST',
			data : $(this).serialize(),
			success : function(data) {
				openMessageDialog(data, "修改提示");
				destroyDialog("entityRoomDialog");
				//刷新
				freshFunctionAfterModify("#roomId_" + $("#entityRoomForm input[name=id]").val());
			}
		});
	});

	//入住操作验证
	$('#enterInFormId').bind('valid.form', function() {
		$.ajax({
			url : getRootPath()+'/entered/enteredAdd.do',
			type : 'POST',
			data : $(this).serialize(),
			success : function(data) {
				openMessageDialog(data, "入住提醒");
				destroyDialog("enterInDialogId");
				//刷新
				freshFunctionAfterModify("#roomId_" + $("#enterInFormId input[name='room.id']").val());
			}
		});
	});
	//入住后修改操作验证
	$('#enteredFormId').bind('valid.form', function() {
		$.ajax({
			url : getRootPath()+'/entered/enteredModify.do',
			type : 'POST',
			data : $(this).serialize(),
			success : function(data) {
				openMessageDialog(data, "修改提醒");
				destroyDialog("enteredDialogId");
				//刷新
				freshFunctionAfterModify("#roomId_" + $("#enteredFormId input[name=roomId]").val());
			}
		});
	});
	//入住后续住操作验证
	$('#enteredConinueFormId').bind('valid.form', function() {
		$.ajax({
			url : getRootPath()+'/entered/enteredContinue.do',
			type : 'POST',
			data : $(this).serialize(),
			success : function(data) {
				openMessageDialog(data, "续住提醒");
				destroyDialog("enteredContinueDialogId");
				//刷新
				freshFunctionAfterModify("#roomId_" + $("#enteredConinueFormId input[name='roomId']").val());
			}
		});
	});
});
//////////////////////////////////function
//删除单个
function deleteARoom() {
	var idJson = "[\"" + $("#entityRoomForm input[name=id]").val() + "\"]";
	deleteRoom(idJson);
	destroyDialog("entityRoomDialog"); //单个删除时调用。
}
//ajax 删除。参数为id的json形式
function deleteRoom(idJson) {
	openConfirmDialog("确定要删除吗？将不可恢复", "确认", function() {
		$.ajax({
			url : getRootPath()+'/room/roomDelete.do',
			data : idJson,
			contentType : "application/json",
			type : 'POST',
			success : function(data) {
				openMessageDialog(data, "删除提示");
				freshFunctionAfterDelete();
			}
		});
	});
}
function billDetailOpen() {
	window.open(getRootPath()+"/common/enteredBillPage.do?enteredId=" + $("#enteredFormId input[name=id]").val());
	destroyDialog("enteredDialogId");
}
function openEnterInDialog() {
	//校验
	$('#enterInFormId')[0].reset();

	$("#enterInFormId input[name='room.id']").val($("#entityRoomForm input[name=id]").val());
	$("#enterInFormId input[name=roomName]").val($("#entityRoomForm input[name=name]").val());
	destroyDialog("entityRoomDialog");
	$("#enterInDialogId").dialog({
		width : 380,
		height : 450,
		title : $("#entityRoomForm input[name=name]").val() + "入住操作",
	});
}
function isHourRoomChange() {
	if ($('input:radio[name="isHourRoom"]:checked').val() == "false")
		$("#enterDaysTrId").show();
	else
		$("#enterDaysTrId").hide();
}
function resetEnterInForm() {
	$("#enterInDialogId textarea[name=description").val("");
	$("#enterInDialogId input[name=name").val("");
	$("#enterInDialogId input[name=numberPeople").val("");
	$("#enterInDialogId input[name=phoneNumber").val("");
	$("#enterInDialogId input[name=enterDays").val("");
}
function openContinueDialog() {
	//校验
	$('#enteredConinueFormId')[0].reset();

	if ($("#enteredFormId input[name=isHourRoom]").val() == "true")
		$("#enteredConinueFormId input[name=days]").attr("readonly", "readonly");
	else
		$("#enteredConinueFormId input[name=days]").removeAttr("readonly");

	$("#enteredConinueFormId input[name='roomId']").val($("#enteredFormId input[name=roomId]").val());
	$("#enteredConinueFormId input[name='outTime']").val($("#enteredFormId input[name=outTime]").val());
	$("#enteredConinueFormId input[name='id']").val($("#enteredFormId input[name=id]").val());
	destroyDialog("enteredDialogId");
	$("#enteredContinueDialogId").dialog({
		width : 380,
		height : 180,
		title : $("#enteredFormId input[name=roomName]").val() + "续住操作",
	});
}

function requestARoom(id) {
	$.ajax({
		url : getRootPath()+'/room/roomGet.do',
		data : JSON.stringify(id),
		contentType : "application/json",
		type : 'POST',
		success : function(data) {
			afterRequestARoom(data);
		}
	});
}
/**
 * 	双击一个房间以后的处理
 * @param data
 */
function afterRequestARoom(data) {
	// 已经入住状态。
	if (data.rVo.room.state == 2) {
		//为了触发校验
		$('#enteredFormId')[0].reset();

		$("#enteredDialogId").dialog({
			width : 360,
			height : 450,
			title : data.rVo.room.name + "入住信息" + " ( " + data.rVo.room.roomType.dayPrice + " /天， " + data.rVo.room.roomType.hourPrice + " /小时 )",
		});

		//服务端在设置的时候将 enteredInfo的Room字段设置成了null，目的是减少传输量，因此这个地方不能够使用eVo.enteredInfo.room,而直接使用rVo.room
		$("#enteredFormId input[name=roomTypeId]").val(data.rVo.room.roomType.id);
		$("#enteredFormId input[name=roomId]").val(data.rVo.room.id);
		$("#enteredFormId input[name=id]").val(data.eVo.enteredInfo.id);
		$("#enteredFormId input[name=roomName]").val(data.rVo.room.name);
		$("#enteredFormId input[name=isHourRoom]").val(data.eVo.enteredInfo.isHourRoom);
		$("#enteredFormId input[name=outTime]").val(data.eVo.enteredInfo.outTime);
		$("#enteredFormId input[name=name]").val(data.eVo.enteredInfo.name);
		$("#enteredFormId input[name=phoneNumber]").val(data.eVo.enteredInfo.phoneNumber);
		$("#enteredFormId input[name=numberPeople]").val(data.eVo.enteredInfo.numberPeople);
		$("#enteredFormId textarea[name=description]").val(data.eVo.enteredInfo.description);
		$("#enteredFormId span[class=enteredTimeDescriptionClass]").html(data.eVo.enteredInfo.enteredTime);
		$("#enteredFormId span[class=enteredWayDescriptionClass]").html(
				data.eVo.hourRoomDescription + " ( " + data.rVo.room.roomType.dayPrice + " /天， " + data.rVo.room.roomType.hourPrice + " /小时 )");
		$("#enteredFormId span[class=outTimeDescriptionClass]").html(data.eVo.enteredInfo.outTime);

		$("#enteredFormId span[class=billPayDescriptionClass]").html(data.eVo.enteredInfo.billPay.toFixed(2));
		$("#enteredFormId span[class=billConsumeDescriptionClass]").html(data.eVo.enteredInfo.billConsume.toFixed(2));
		$("#enteredFormId span[class=billRoomDescriptionClass]").html(data.eVo.enteredInfo.billRoom.toFixed(2));
		$("#enteredFormId span[class=balanceDescriptionClass]").html((data.eVo.enteredInfo.billPay - data.eVo.enteredInfo.billRoom - data.eVo.enteredInfo.billConsume).toFixed(2));
	}
	else {

		//为了触发校验
		$('#entityRoomForm')[0].reset();

		$("#entityRoomDialog").dialog({
			width : 360,
			height : 345,
			title : data.rVo.room.name + "详细信息" + " ( " + data.rVo.room.roomType.dayPrice + " /天， " + data.rVo.room.roomType.hourPrice + " /小时 )",
		});
		$("#entityRoomForm input[name=id]").val(data.rVo.room.id);
		$("#entityRoomForm input[name=genTime]").val(data.rVo.room.genTime);
		$("#entityRoomForm input[name=name]").val(data.rVo.room.name);
		$("#entityRoomForm textarea[name=description]").val(data.rVo.room.description);
		$("#entityRoomForm input[name=roomTypeName]").val(data.rVo.room.roomType.name);
		$("#entityRoomForm input[name='roomType.id']").val(data.rVo.room.roomType.id);
		$("#entityRoomForm option[value=" + data.rVo.room.state + "]").attr("selected", true);
		if (data.rVo.room.state == 1) {
			$("#enterInButtonId").show();
		}
		else {
			$("#enterInButtonId").hide();
		}

	}
}
