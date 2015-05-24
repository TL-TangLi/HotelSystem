/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////此文件函数要慎重修改，（多个页面使用文件中的函数）
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///****************************************************************begin pagination
//尽量使用destroyDialog

/**
 * @returns 返回根路径 如： http://localhost:8080/inhome
 */
function getRootPath() {
	var strFullPath = window.document.location.href;
	var strPath = window.document.location.pathname;
	var pos = strFullPath.indexOf(strPath);
	var prePath = strFullPath.substring(0, pos);
	var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
	return (prePath + postPath);
}
function closeDialog(id) {
	$("#" + id).dialog("close");
}
function destroyDialog(id) {
	$("#" + id).dialog("destroy");
}
/**
 * ajax返回后的消息提示框
 * @param message
 * @param title
 */
function openMessageDialog(message,title) {
	$("<div title='" + title + "'><p>" + message + "</p></div>").dialog({
		width : 220,
		height : 220,
		buttons : {
			确定 : function() {
				$(this).dialog("destroy");
			}
		}
	});
}
/**
 * @param id  图片设置为返回的验证码
 */
function authCodeClick(id) {
	$('#' + id).attr('src', getRootPath()+'/common/authCode.do?' + Math.random());
}

/**
 * 打开对话框 验证码确认后执行
 * @param message
 * @param title
 * @param Func,执行的回调函数
 */
function openConfirmDialog(message,title,Func) {
	$(
			"<div id=\"confirmDialog\" title='" + title + "'><p>" + message + "</p>"
					+ "<table><tr><td><img id='authImag' style='padding-left:20px;' src=getRootPath()+'/common/authCode.do' " + "onclick='authCodeClick(\"authImag\")'></img></td></tr>"
					+ "<tr><td><input id=\"authCodeInput\" name=\"authCode\"/></td></tr></div>").dialog({
		width : 220,
		height : 220,
		modal : true,
		buttons : {
			继续 : function() {
				$.ajax({
					url : getRootPath()+'/common/checkAuthCode.do',
					data : "authCode=" + $('#authCodeInput').val(),
					type : 'POST', // 这个地方如果不用post
					success : function(data) {
						if (data == null || data == '') {
							Func();
							$("#confirmDialog").dialog("destroy"); // 必须要destroy
							// 不能够close
						}
						else {
							authCodeClick('authImag');
						}
					}
				});
			},
			取消 : function() {
				$(this).dialog("destroy");// 必须要destroy 不能够close
			}
		}
	});
}



//防止表单重复提交的 标记，如果为 ture 说明已经提交了，处理完后，将其置为true
var isOperating = false;

////sx:submit 异步获取数据 在infoDialog 上显示
function getResult(data,dialog){
	document.getElementById("info").innerHTML=data;
	dialogClose(dialog);
	$("#infoDialog").dialog({width:270,height:200, modal: true});
	$( "#infoDialog" ).dialog( "option","title","消息");
	
	//处理完成 isOperating 置为false
	isOperating =false;
};

/**
 * @param eventName 		发布事件名字
 * @param closeDialogId		处理完成后将要关闭的 dialogId
 */
function dojoSubscribe(eventName,closeDialogId){
	
	dojo.event.topic.subscribe(eventName,function myfunction(data,type,request){
		
		if(type =='load')
			getResult(data,closeDialogId);
		
	});
}




/**
 * @param entityFormId		当前表单id
 * @param id				要删除的Entity的标识符表单名字（会从表单中取出该字段）。
 * @param entityDialogId	开始执行删除要关闭的dialog
 * @param paginationDIV		删除后要刷新的分页div。
 * @param url				删除地址
 */
function deleteAEntity(entityFormId,id,entityDialogId,paginationDIV,url) {

	var idJson = "[\"" + $("#" + entityFormId + " input[name=" + id + "]").val() + "\"]";
	deleteEntity(paginationDIV, url, idJson);
	destroyDialog(entityDialogId); //单个删除后关闭对话框。
}

/**
 * 使用deleteAEntity
 * @param paginationDIV
 * @param url
 * @param idJson
 */
function deleteEntity(paginationDIV,url,idJson) {
	openConfirmDialog("确定要删除吗？", "确认", function() {
		$.ajax({
			url : url,
			data : idJson,
			contentType : "application/json",
			type : 'POST',
			success : function(data) {
				openMessageDialog(data, "删除提示");
				paginationDIVRefresh(paginationDIV);
			}
		});
	});
}

/**
 * 分页div重置
 * @param paginationDIV
 */
function paginationDIVRefresh(paginationDIV) {
	//更新标记
	$("#tools_" + paginationDIV + "_drop_select_launcher").button({
		label : "标记:0",
	});
	//清除标记数据
	$("#" + paginationDIV).data('jui_datagrid_status')['a_selected_ids'] = [];
	$("#" + paginationDIV).data('jui_datagrid_status')['count_selected_ids'] = 0;
	//设置为第一页 会产生一次刷新
	$("#" + paginationDIV).jui_datagrid({
		pageNum : 1,
	});

}
