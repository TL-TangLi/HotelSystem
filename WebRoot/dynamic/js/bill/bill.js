
function initialBillPay(paginationDIV,url,DBFUNC)
{
	$("#" + paginationDIV).jui_datagrid(
	{
		dlgFiltersClass : 'grid1_filters', //过滤器对话框的风格
		ajaxFetchDataURL : url, //数据请求地址
		row_primary_key : 'id',//列主键 必须为不重复的整数 。。。。太尼玛坑爹了。
		containerClass : 'grid1_container ui-state-default ui-corner-all', //整个表格的风格
		datagridClass : 'grid1_data ui-widget-content',//数据区的风格
//				caption : '入账列表',//表格显示名字
		rowsPerPage : 15,
		rowSelectionMode : 'multiple', // 'multiple', 'single', 'false'
		showExportButton : false,//不显示导出
		showPrintButton : false,//不显示打印
		showRowNumbers : true,
		showPrefButtonText : false,
		showRefreshButtonText : true,
		showDeleteButtonText : true,
		showPrintButtonText : true,
		showExportButtonText : true,
		showSortingButtonText : true,
		showFiltersButtonText : true,
		showAddButtonText : false,
		showAddButton : false,
		selectedTrTrClass : 'ui-widget-header',
		//selectedTrTdClass: 'selectedRowClass',
		selectedTrTdClass : 'ui-widget-header',
		// select dropdown options
		dropdownSelectOptions :
		{
			menuClass : 'dropdown_select_menu',
			launcherClass : 'dropdown_select_launcher',
			launchOnMouseEnter : false
		}, // 'launcher_id', 'launcher_container_id', 'menu_id', 'onSelect' will be ignored
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
		}, // 'currentPage', 'rowsPerPage', 'totalPages', 'containerClass', 'onSetRowsPerPage', 'onChangePage', 'onDisplay' will be ignored
		
		sorting :
		[
				{
					sortingName : "入账时间",
					field : "genTime",
					order : "DESC"
				},
				{
					sortingName : "联系人",
					field : "enteredInfo.name",
					order : "none"
				},
				{
					sortingName : "房间名",
					field : "room.name",
					order : "none"
				},
				{
					sortingName : "房间类型",
					field : "room.roomType.name",
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
						field : "enteredInfo.name",
						filterLabel : "联系人",
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
						filterName : "payWayFilter",
						"filterType" : "text",
						field : "payWay",
						filterLabel : "入账方式",
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
									type: "checkbox"
								},
								vertical_orientation : "yes"
							}
						],
						lookup_values :
						[
								//状态（1干净、2在住、3脏房、4不可用房）
								{
									lk_option : "银行卡",
									lk_value : 1,
								},
								{
									lk_option : "现金",
									lk_value : 2,
								},
								{
									lk_option : "其他",
									lk_value : 3,
								}
						]
					},
					{
						filterName : "genTimeFilter",
						"filterType" : "date",
						field : "genTime",
						filterLabel : "入账时间",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal","less_or_equal", "greater_or_equal"
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
					{
						filterName : "phoneNumberFilter",
						"filterType" : "text",
						field : "enteredInfo.phoneNumber",
						filterLabel : "电话号码",
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
						filterName : "roomFilter",
						"filterType" : "text",
						field : "room.name",
						filterLabel : "房间名称",
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
						filterName : "roomTypeFilter",
						"filterType" : "text",
						field : "room.roomType.name",
						filterLabel : "房间类型名称",
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
						filterName : "isCheckOutFilter",
						"filterType" : "text",
						field : "enteredInfo.isCheckOut",
						filterLabel : "是否退房",
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
									type: "radio"
								},
								vertical_orientation : "yes"
							}
						],
						lookup_values :
						[
								//状态（1干净、2在住、3脏房、4不可用房）
								{
									lk_option : "已退",
									lk_value : 1,
								},
								{
									lk_option : "未退",
									lk_value : 0,
								}
						]
					},
					{
						filterName : "isHourRoomFilter",
						"filterType" : "text",
						field : "enteredInfo.isHourRoom",
						filterLabel : "入住方式",
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
									type: "radio"
								},
								vertical_orientation : "yes"
							}
						],
						lookup_values :
						[
								//状态（1干净、2在住、3脏房、4不可用房）
								{
									lk_option : "小时房入住",
									lk_value : 1,
								},
								{
									lk_option : "普通入住",
									lk_value : 0,
								}
						]
					},
					{
						filterName : "descriptionFilter",
						"filterType" : "text",
						field : "description",
						filterLabel : "入账说明",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal"
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
						filterName : "edescriptionFilter",
						"filterType" : "text",
						field : "enteredInfo.description",
						filterLabel : "入住说明",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal"
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
						filterName : "enteredTimeFilter",
						"filterType" : "date",
						field : "enteredInfo.enteredTime",
						filterLabel : "入住时间",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal"
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
					{
						filterName : "outTimeFilter",
						"filterType" : "date",
						field : "enteredInfo.outTime",
						filterLabel : "退房日期",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal"
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
									changeMonth : true,
									changeYear : true,
									showSecond : true
								}
							}
						],
						validate_dateformat :
						[
							"YYYY-MM-DD"
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
			DBFUNC(data.row_id);
		},
		onDelete : function()
		{
			var ids = $(this).jui_datagrid("getSelectedIDs"), num = ids.length;
			if (num == 0)
			{
				//首先清除验证信息 居然无效。
				openMessageDialog("没有数据被选中，请标记后再删除", "提示");
			}
			else
			{
				deleteBill(JSON.stringify(ids), getRootPath()+"/billPay/billPayDelete.do", paginationDIV);
			}
		},
	});
	$("button").button();
}
function initialBillRoom(paginationDIV,url,DBFUNC)
{
	$("#" + paginationDIV).jui_datagrid(
	{
		dlgFiltersClass : 'grid1_filters', //过滤器对话框的风格
		ajaxFetchDataURL : url, //数据请求地址
		row_primary_key : 'id',//列主键 必须为不重复的整数 。。。。太尼玛坑爹了。
		containerClass : 'grid1_container ui-state-default ui-corner-all', //整个表格的风格
		datagridClass : 'grid1_data ui-widget-content',//数据区的风格
		//		caption : '房费列表',//表格显示名字
		rowsPerPage : 15,
		rowSelectionMode : 'multiple', // 'multiple', 'single', 'false'
		showExportButton : false,//不显示导出
		showPrintButton : false,//不显示打印
		showRowNumbers : true,
		showPrefButtonText : false,
		showRefreshButtonText : true,
		showDeleteButtonText : true,
		showPrintButtonText : true,
		showExportButtonText : true,
		showSortingButtonText : true,
		showFiltersButtonText : true,
		showAddButtonText : false,
		showAddButton : false,
		selectedTrTrClass : 'ui-widget-header',
		//selectedTrTdClass: 'selectedRowClass',
		selectedTrTdClass : 'ui-widget-header',
		// select dropdown options
		dropdownSelectOptions :
		{
			menuClass : 'dropdown_select_menu',
			launcherClass : 'dropdown_select_launcher',
			launchOnMouseEnter : false
		}, // 'launcher_id', 'launcher_container_id', 'menu_id', 'onSelect' will be ignored
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
		}, // 'currentPage', 'rowsPerPage', 'totalPages', 'containerClass', 'onSetRowsPerPage', 'onChangePage', 'onDisplay' will be ignored
		
		sorting :
		[
				{
					sortingName : "生成日期",
					field : "genDate",
					order : "DESC"
				},
				{
					sortingName : "联系人",
					field : "enteredInfo.name",
					order : "none"
				},
				{
					sortingName : "房间名",
					field : "room.name",
					order : "none"
				},
				{
					sortingName : "房间类型",
					field : "room.roomType.name",
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
						field : "enteredInfo.name",
						filterLabel : "联系人",
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
						filterName : "genDateFilter",
						"filterType" : "date",
						field : "genDate",
						filterLabel : "产生时间",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal","less_or_equal", "greater_or_equal"
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
					{
						filterName : "phoneNumberFilter",
						"filterType" : "text",
						field : "enteredInfo.phoneNumber",
						filterLabel : "电话号码",
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
						filterName : "roomFilter",
						"filterType" : "text",
						field : "room.name",
						filterLabel : "房间名称",
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
						filterName : "roomTypeFilter",
						"filterType" : "text",
						field : "room.roomType.name",
						filterLabel : "房间类型名称",
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
						filterName : "isCheckOutFilter",
						"filterType" : "text",
						field : "enteredInfo.isCheckOut",
						filterLabel : "是否退房",
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
									lk_option : "已退",
									lk_value : 1,
								},
								{
									lk_option : "未退",
									lk_value : 0,
								}
						]
					},
					{
						filterName : "isHourRoomFilter",
						"filterType" : "text",
						field : "enteredInfo.isHourRoom",
						filterLabel : "入住方式",
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
									lk_option : "小时房入住",
									lk_value : 1,
								},
								{
									lk_option : "普通入住",
									lk_value : 0,
								}
						]
					},
					{
						filterName : "edescriptionFilter",
						"filterType" : "text",
						field : "enteredInfo.description",
						filterLabel : "入住说明",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal"
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
						filterName : "enteredTimeFilter",
						"filterType" : "date",
						field : "enteredInfo.enteredTime",
						filterLabel : "入住时间",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal"
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
					{
						filterName : "outTimeFilter",
						"filterType" : "date",
						field : "enteredInfo.outTime",
						filterLabel : "退房日期",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal"
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
									changeMonth : true,
									changeYear : true,
									showSecond : true
								}
							}
						],
						validate_dateformat :
						[
							"YYYY-MM-DD"
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
			DBFUNC(data.row_id);
		},
		onDelete : function()
		{
			var ids = $(this).jui_datagrid("getSelectedIDs"), num = ids.length;
			if (num == 0)
			{
				//首先清除验证信息 居然无效。
				openMessageDialog("没有数据被选中，请标记后再删除", "提示");
			}
			else
			{
				deleteBill(JSON.stringify(ids), getRootPath()+"/billRoom/billRoomDelete.do", paginationDIV);
			}
		},
	});
	$("button").button();
}
function initialBillConsume(paginationDIV,url,DBFUNC)
{
	$("#" + paginationDIV).jui_datagrid(
	{
		dlgFiltersClass : 'grid1_filters', //过滤器对话框的风格
		ajaxFetchDataURL : url, //数据请求地址
		row_primary_key : 'id',//列主键 必须为不重复的整数 。。。。太尼玛坑爹了。
		containerClass : 'grid1_container ui-state-default ui-corner-all', //整个表格的风格
		datagridClass : 'grid1_data ui-widget-content',//数据区的风格
		//		caption : '消费列表',//表格显示名字
		rowsPerPage : 15,
		rowSelectionMode : 'multiple', // 'multiple', 'single', 'false'
		showExportButton : false,//不显示导出
		showPrintButton : false,//不显示打印
		showRowNumbers : true,
		showPrefButtonText : false,
		showRefreshButtonText : true,
		showDeleteButtonText : true,
		showPrintButtonText : true,
		showExportButtonText : true,
		showSortingButtonText : true,
		showFiltersButtonText : true,
		showAddButtonText : false,
		showAddButton : false,
		selectedTrTrClass : 'ui-widget-header',
		//selectedTrTdClass: 'selectedRowClass',
		selectedTrTdClass : 'ui-widget-header',
		// select dropdown options
		dropdownSelectOptions :
		{
			menuClass : 'dropdown_select_menu',
			launcherClass : 'dropdown_select_launcher',
			launchOnMouseEnter : false
		}, // 'launcher_id', 'launcher_container_id', 'menu_id', 'onSelect' will be ignored
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
		}, // 'currentPage', 'rowsPerPage', 'totalPages', 'containerClass', 'onSetRowsPerPage', 'onChangePage', 'onDisplay' will be ignored
		
		sorting :
		[
				{
					sortingName : "消费时间",
					field : "genTime",
					order : "DESC"
				},
				{
					sortingName : "联系人",
					field : "enteredInfo.name",
					order : "none"
				},
				{
					sortingName : "房间名",
					field : "room.name",
					order : "none"
				},
				{
					sortingName : "房间类型",
					field : "room.roomType.name",
					order : "none"
				},
		],
		filterOptions :
		{
			filters :
			[
					{
						filterName : "genTimeFilter",
						"filterType" : "date",
						field : "genTime",
						filterLabel : "消费时间",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal","less_or_equal", "greater_or_equal"
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
					{
						filterName : "nameFilter",
						"filterType" : "text",
						field : "enteredInfo.name",
						filterLabel : "联系人",
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
						filterName : "phoneNumberFilter",
						"filterType" : "text",
						field : "enteredInfo.phoneNumber",
						filterLabel : "电话号码",
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
						filterName : "roomFilter",
						"filterType" : "text",
						field : "room.name",
						filterLabel : "房间名称",
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
						filterName : "roomTypeFilter",
						"filterType" : "text",
						field : "room.roomType.name",
						filterLabel : "房间类型名称",
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
						filterName : "isCheckOutFilter",
						"filterType" : "text",
						field : "enteredInfo.isCheckOut",
						filterLabel : "是否退房",
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
									lk_option : "已退",
									lk_value : 1,
								},
								{
									lk_option : "未退",
									lk_value : 0,
								}
						]
					},
					{
						filterName : "isHourRoomFilter",
						"filterType" : "text",
						field : "enteredInfo.isHourRoom",
						filterLabel : "入住方式",
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
									lk_option : "小时房入住",
									lk_value : 1,
								},
								{
									lk_option : "普通入住",
									lk_value : 0,
								}
						]
					},
					{
						filterName : "descriptionFilter",
						"filterType" : "text",
						field : "description",
						filterLabel : "入账说明",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal"
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
						filterName : "edescriptionFilter",
						"filterType" : "text",
						field : "enteredInfo.description",
						filterLabel : "入住说明",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal"
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
						filterName : "enteredTimeFilter",
						"filterType" : "date",
						field : "enteredInfo.enteredTime",
						filterLabel : "入住时间",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal"
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
					{
						filterName : "outTimeFilter",
						"filterType" : "date",
						field : "enteredInfo.outTime",
						filterLabel : "退房日期",
						excluded_operators :
						[
								"in", "not_in", "equal", "not_equal"
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
									changeMonth : true,
									changeYear : true,
									showSecond : true
								}
							}
						],
						validate_dateformat :
						[
							"YYYY-MM-DD"
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
			DBFUNC(data.row_id);
		},
		onDelete : function()
		{
			var ids = $(this).jui_datagrid("getSelectedIDs"), num = ids.length;
			if (num == 0)
			{
				//首先清除验证信息 居然无效。
				openMessageDialog("没有数据被选中，请标记后再删除", "提示");
			}
			else
			{
				deleteBill(JSON.stringify(ids), getRootPath()+"/billConsume/billConsumeDelete.do", paginationDIV);
			}
		},
	});
	$("button").button();
}
