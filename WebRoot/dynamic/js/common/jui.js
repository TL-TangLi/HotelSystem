//////
function initialMiniJuiRoom(paginationDIV,roomTypeId,DBFUNC)
{
	$("#" + paginationDIV).jui_datagrid(
	{
		dlgFiltersClass : 'miniPaginationRoomTypeFiltersClass', // 过滤器对话框的风格
		//		ajaxFetchDataURL : getRootPath()+'/room/roomMiniPage.do?roomTypeId=d0e54de8-626f-4da2-9d4a-a615a61de752', // 数据请求地址
		ajaxFetchDataURL : getRootPath()+'/room/roomMiniPage.do?roomTypeId=' + roomTypeId, // 数据请求地址
		row_primary_key : 'id',// 列主键 必须为不重复的整数 。。。。太尼玛坑爹了。
		containerClass : 'miniPaginationRoomTypeDataClass ui-state-default ui-corner-all', // 整个表格的风格
		datagridClass : 'miniPaginationRoomTypeDataClass ui-widget-content',// 数据区的风格
		// caption : '房间类型',//表格显示名字
		rowsPerPage : 7,
		rowSelectionMode : 'single', // 'multiple', 'single', 'false'
		showExportButton : false,// 不显示导出
		showPrintButton : false,// 不显示打印
		showDeleteButton : false,
		showAddButton : false,
		// showRowNumbers : true,
		// showPrefButtonText : false,
		// showDeleteButtonText : true,
		// showPrintButtonText : true,
		// showAddButtonText : true,
		// showExportButtonText : true,
		showRefreshButtonText : true,
		showSortingButtonText : true,
		showFiltersButtonText : true,
		selectedTrTrClass : 'ui-widget-header',
		// selectedTrTdClass: 'selectedRowClass',
		selectedTrTdClass : 'ui-widget-header',
		// select dropdown options
		dropdownSelectOptions :
		{
			menuClass : 'dropdown_select_menu',
			launcherClass : 'dropdown_select_launcher',
			launchOnMouseEnter : false
		}, // 'launcher_id', 'launcher_container_id', 'menu_id', 'onSelect'
		// will be ignored
		// pagination options
		usePagination : true,
		paginationOptions :
		{
			visiblePageLinks : 5,
			useSlider : true,
			showGoToPage : true,
			showRowsPerPage : true,
			showRowsInfo : true,
			showPreferences : false,
			disableSelectionNavPane : true,
		}, // 'currentPage', 'rowsPerPage', 'totalPages', 'containerClass',
		// 'onSetRowsPerPage', 'onChangePage', 'onDisplay' will be ignored
		columns :
		[
				{
					field : "room.name",
					visible : "yes",
					"header" : '房间名称',
					"headerClass" : "miniPaginationRoom_th_nameClass",
					"dataClass" : "miniPaginationRoom_td_nameClass"
				},
				{
					field : "room.roomType.name",
					visible : "yes",
					"header" : '房间类型',
					"headerClass" : "miniPaginationRoom_th_roomTypeNameClass",
					"dataClass" : "miniPaginationRoom_td_roomTypeNameClass"
				},
				{
					field : "stateName",
					visible : "yes",
					"header" : '状态',
					"headerClass" : "miniPaginationRoom_th_stateClass",
					"dataClass" : "miniPaginationRoom_td_stateClass"
				},
				{
					field : "room.description",
					visible : "yes",
					"header" : '说明信息',
					"headerClass" : "miniPaginationRoom_th_descriptionClass",
					"dataClass" : "miniPaginationRoom_td_descriptionClass"
				},
				{
					field : "room.genTime",
					visible : "yes",
					"header" : '生成时间',
					"headerClass" : "miniPaginationRoom_th_genTimeClass",
					"dataClass" : "miniPaginationRoom_td_genTimeClass",
				}
		],
		sorting :
		[
				{
					sortingName : "生成时间排序",
					field : "genTime",
					order : "DESC"
				},
				{
					sortingName : "房间类型排序",
					field : "roomType.name",
					order : "ASC"
				},
				{
					sortingName : "状态排序",
					field : "state",
					order : "DESC"
				},
				{
					sortingName : "名称排序",
					field : "name",
					order : "none"
				},
				{
					sortingName : "全天价格排序",
					field : "roomType.dayPrice",
					order : "none"
				},
				{
					sortingName : "小时价格排序",
					field : "roomType.hourPrice",
					order : "none"
				},
		],
		filterOptions :
		{
			filters :
			[
					{
						filterName : "nameFilter",
						"filterType" : "text",
						field : "name",
						filterLabel : "房间名",
						excluded_operators :
						[
								"in", "not_in"
						],
						filter_interface :
						[
							{
								filter_element : "input",
								filter_element_attributes :
								{
									"type" : "text"
								}
							}
						]
					},
					{
						filterName : "stateFilter",
						"filterType" : "text",
						field : "state",
						filterLabel : "状态",
						excluded_operators :
						[
								"equal", "not_equal", "less", "less_or_equal", "greater", "greater_or_equal", "contains", "not_contains", "ends_with", "not_ends_with", "begins_with", "not_begins_with", "null", "not_null", "is_empty", "is_not_empty"
						],
						filter_interface :
						[
							{
								filter_element : "input",
								filter_element_attributes :
								{
									type : "checkbox"
								},
								vertical_orientation : "yes"
							}
						],
						lookup_values :
						[
								//状态（1干净、2在住、3脏房、4不可用房）
								{
									lk_option : "干净房",
									lk_value : 1,
								},
								{
									lk_option : "在住房",
									lk_value : 2,
								},
								{
									lk_option : "脏房",
									lk_value : 3,
								},
								{
									lk_option : "不可用房",
									lk_value : 4,
								}
						]
					},
					{
						filterName : "roomTypeNameFilter",
						"filterType" : "text",
						field : "roomType.name",
						filterLabel : "类型名",
						excluded_operators :
						[
								"in", "not_in"
						],
						filter_interface :
						[
							{
								filter_element : "input",
								filter_element_attributes :
								{
									"type" : "text"
								}
							}
						]
					},
					{
						filterName : "genTimeFilter",
						"filterType" : "date",
						field : "genTime",
						filterLabel : "生成时间",
						excluded_operators :
						[
								"in", "not_in","equal", "not_equal"
						],
						filter_interface :
						[
							{
								filter_element : "input",
								filter_element_attributes :
								{
									type : "text",
									title : "Set the date and time using format: dd/mm/yyyy hh:mm:ss"
								},
								filter_widget : "datetimepicker",
								filter_widget_properties :
								{
									dateFormat : "yy-mm-dd",
									timeFormat : "HH:mm:ss",
									changeMonth : true,
									changeYear : true,
									showSecond : true
								}
							}
						],
						validate_dateformat :
						[
							"YYYY-MM-DD HH:mm:ss"
						],
					},
			],
			decimal_separator : "."
		},
		onChangeSelectedRows : function()
		{
			var elem_jui_dropdown_launcher_span = $('#' + $("#" + paginationDIV).jui_datagrid('getDropdownLauncherID') + ' span');
			elem_jui_dropdown_launcher_span.addClass('juib_fix');
		},
		onDisplay : function()
		{
			var elem_jui_dropdown_launcher_span = $('#' + $("#" + paginationDIV).jui_datagrid('getDropdownLauncherID') + ' span');
			elem_jui_dropdown_launcher_span.addClass('juib_fix');
		},
		onDBClick : function(event,data)
		{
			DBFUNC(data.row_id, $("#tbl_" + paginationDIV + "_tr_" + data.row_id + " td")[0].innerHTML);
		},
	});
}

function initialMiniJuiRoomType(paginationDIV,DBFUNC)
{
	$("#" + paginationDIV).jui_datagrid(
	{
		dlgFiltersClass : 'miniPaginationRoomTypeFiltersClass', // 过滤器对话框的风格
		ajaxFetchDataURL : getRootPath()+'/roomType/roomTypePage.do', // 数据请求地址
		row_primary_key : 'id',// 列主键 必须为不重复的整数 。。。。太尼玛坑爹了。
		containerClass : 'miniPaginationRoomTypeDataClass ui-state-default ui-corner-all', // 整个表格的风格
		datagridClass : 'miniPaginationRoomTypeDataClass ui-widget-content',// 数据区的风格
		// caption : '房间类型',//表格显示名字
		rowsPerPage : 7,
		rowSelectionMode : 'single', // 'multiple', 'single', 'false'
		showExportButton : false,// 不显示导出
		showPrintButton : false,// 不显示打印
		showDeleteButton : false,
		showAddButton : false,
		// showRowNumbers : true,
		// showPrefButtonText : false,
		// showDeleteButtonText : true,
		// showPrintButtonText : true,
		// showAddButtonText : true,
		// showExportButtonText : true,
		showRefreshButtonText : true,
		showSortingButtonText : true,
		showFiltersButtonText : true,
		selectedTrTrClass : 'ui-widget-header',
		// selectedTrTdClass: 'selectedRowClass',
		selectedTrTdClass : 'ui-widget-header',
		// select dropdown options
		dropdownSelectOptions :
		{
			menuClass : 'dropdown_select_menu',
			launcherClass : 'dropdown_select_launcher',
			launchOnMouseEnter : false
		}, // 'launcher_id', 'launcher_container_id', 'menu_id', 'onSelect'
		// will be ignored
		// pagination options
		usePagination : true,
		paginationOptions :
		{
			visiblePageLinks : 5,
			useSlider : true,
			showGoToPage : true,
			showRowsPerPage : true,
			showRowsInfo : true,
			showPreferences : false,
			disableSelectionNavPane : true,
		}, // 'currentPage', 'rowsPerPage', 'totalPages', 'containerClass',
		// 'onSetRowsPerPage', 'onChangePage', 'onDisplay' will be ignored
		columns :
		[
				{
					field : "id",
					visible : "no",
					"header" : 'id',
					"headerClass" : "miniPaginationRoomType_th_idClass", // 列头的风格
					"dataClass" : "miniPaginationRoomType_td_idClass"// 列数据的风格
				},
				{
					field : "name",
					visible : "yes",
					"header" : '房间类型',
					"headerClass" : 'miniPaginationRoomType_th_nameClass',
					"dataClass" : 'miniPaginationRoomType_td_nameClass',
				},
				{
					field : "dayPrice",
					visible : "yes",
					"header" : '全天价格',
					"headerClass" : "miniPaginationRoomType_th_dayPriceClass",
					"dataClass" : "miniPaginationRoomType_td_dayPriceClass"
				},
				{
					field : "hourPrice",
					visible : "yes",
					"header" : '小时价格',
					"headerClass" : "miniPaginationRoomType_th_hourPriceClass",
					"dataClass" : "miniPaginationRoomType_td_hourPriceClass"
				},
				{
					field : "description",
					visible : "yes",
					"header" : '说明信息',
					"headerClass" : "miniPaginationRoomType_th_descriptionClass",
					"dataClass" : "miniPaginationRoomType_td_descriptionClass"
				},
				{
					field : "genTime",
					visible : "yes",
					"header" : '生成时间',
					"headerClass" : "miniPaginationRoomType_th_genTimeClass",
					"dataClass" : "miniPaginationRoomType_td_genTimeClass",
				}
		],
		sorting :
		[
				{
					sortingName : "生成时间排序",
					field : "genTime",
					order : "DESC"
				},
				{
					sortingName : "全天价格排序",
					field : "dayPrice",
					order : "none"
				},
				{
					sortingName : "小时价格排序",
					field : "hourPrice",
					order : "none"
				},
				{
					sortingName : "名称排序",
					field : "name",
					order : "none"
				}
		],
		filterOptions :
		{
			filters :
			[
					{
						filterName : "nameFilter",
						"filterType" : "text",
						field : "name",
						filterLabel : "类型名称",
						excluded_operators :
						[
								"in", "not_in"
						],
						filter_interface :
						[
							{
								filter_element : "input",
								filter_element_attributes :
								{
									"type" : "text"
								}
							}
						]
					},
					{
						filterName : "genTimeFilter",
						"filterType" : "date",
						field : "genTime",
						filterLabel : "生成时间",
						excluded_operators :
						[
								"in", "not_in","equal", "not_equal"
						],
						filter_interface :
						[
							{
								filter_element : "input",
								filter_element_attributes :
								{
									type : "text",
									title : "Set the date and time using format: dd/mm/yyyy hh:mm:ss"
								},
								filter_widget : "datetimepicker",
								filter_widget_properties :
								{
									dateFormat : "yy-mm-dd",
									timeFormat : "HH:mm:ss",
									changeMonth : true,
									changeYear : true,
									showSecond : true
								}
							}
						],
						validate_dateformat :
						[
							"YYYY-MM-DD HH:mm:ss"
						],
					},
			],
			decimal_separator : "."
		},
		onChangeSelectedRows : function()
		{
			var elem_jui_dropdown_launcher_span = $('#' + $("#" + paginationDIV).jui_datagrid('getDropdownLauncherID') + ' span');
			elem_jui_dropdown_launcher_span.addClass('juib_fix');
		},
		onDisplay : function()
		{
			var elem_jui_dropdown_launcher_span = $('#' + $("#" + paginationDIV).jui_datagrid('getDropdownLauncherID') + ' span');
			elem_jui_dropdown_launcher_span.addClass('juib_fix');
		},
		onDBClick : function(event,data)
		{
			//			alert(JSON.stringify(data) );
			//			alert($("#tbl_" + paginationDIV + "_tr_" + data.row_id + " td")[0].innerHTML);
			DBFUNC(data.row_id, $("#tbl_" + paginationDIV + "_tr_" + data.row_id + " td")[0].innerHTML);
		},
	});
}

/**
 * @param paginationDIV
 * @param roomTypeIdInputId   双击后房间类型id要 放入的input 的id
 * @param roomTypeNameInputId 双击后房间类型name要 放入的input 的id
 * 添加房间时	打开房间类型对话框选择房间类型
 * initialMiniJuiRoomType 函数 位于 jui.js文件中。
 */
function chooseRoomTypeForAdd(paginationDIV,roomTypeIdInputId,roomTypeNameInputId)
{
	initialMiniJuiRoomType(paginationDIV, function(id,name)
	{
		$("#" + roomTypeNameInputId).val(name);
		$("#" + roomTypeIdInputId).val(id);
		$("#" + paginationDIV).dialog("close");
	});
	$("#" + paginationDIV).dialog(
	{
		modal : true,
		autoOpen : false,
		title : "双击选择一个房间类型",
		width : 750,
		height : 370,
		hide : "fade",
		zIndex : 500,
		open : function()
		{
			$("#" + paginationDIV).jui_datagrid('refresh');
		}
	});
	$("#" + paginationDIV).dialog("open");
}


//打开房间类型对话框选择房间类型
function chooseRoomForSwitchRoom(paginationDIV)
{
	initialMiniJuiRoom(paginationDIV, $("#enteredFormId input[name=roomTypeId]").val(), function(newRoomId,newRoomName)
	{
		destroyDialog("enteredDialogId");
		openConfirmDialog("确定要从" + $("#enteredFormId input[name=roomName]").val() + "换到" + newRoomName + "吗？", "换房确认", function()
		{
			$.ajax(
			{
				url : getRootPath()+'/entered/enteredSwitch.do',
				data :
				{
					newRoomId : newRoomId,
					oldRoomId : $("#enteredFormId input[name=roomId]").val(),
					id : $("#enteredFormId input[name=id]").val()
				},
				type : 'POST',
				success : function(data)
				{
					openMessageDialog(data, "换房提示");
					freshFunctionAfterModify("#roomId_" + newRoomId + ",#roomId_"+$("#enteredFormId input[name=roomId]").val() );
				},
			});
		});
		$("#" + paginationDIV).dialog("close");
	});
	$("#" + paginationDIV).dialog(
	{
		modal : true,
		autoOpen : false,
		title : "换房操作：双击选择一个房间",
		width : 750,
		height : 370,
		hide : "fade",
		zIndex : 500,
		open : function()
		{
			$("#" + paginationDIV).jui_datagrid('refresh');
		}
	});
	$("#" + paginationDIV).dialog("open");
}