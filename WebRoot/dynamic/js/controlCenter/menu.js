
////////////////////////////////////////////////Menu function

var hoverMenuId = null; // 如果鼠标停放在 menu 或者menu对应的menuHead上，那么记录这个menuId
var isInfoMenuPin = true; // 记录今日菜单是否被固定了
function todayInfoPinToggle()
{
	// 置为固定状态
	if (isInfoMenuPin == false)
	{
		isInfoMenuPin = true;
		$("#todayInfoPin").button(
		{
			icons :
			{
				primary : "ui-icon-pin-s"
			},
			text : false
		});
	}
	// 置为未固定状态
	else
	{
		isInfoMenuPin = false;
		$("#todayInfoPin").button(
		{
			icons :
			{
				primary : "ui-icon-pin-w"
			},
			text : false
		});
	}
}
function openMenu(id)
{
	hoverMenuId = id;
	$("#" + id).show();
};

function menuClose(id)
{
	if ( hoverMenuId == id)
		return;
	else
	{
		$("#" + id).hide();
	}
}

function delaymenuClose(id)
{
	hoverMenuId = null;
	// 如果今日信息 菜单被固定了 ，那么不要关闭 了！
	if (id == "todayMenuId" && isInfoMenuPin == true)
		return;
	setTimeout(function()
	{
		menuClose(id);
	}, 300);
};





//////////////////////////////////////////////////effective

$(function() {
	
	$("#tabs").tabs({
		/*event : "mouseover"*/
	});

	$(".menu").menu();
	$( "input[type=submit],button,input[type=button]" ).button();
	

//	$(document).tooltip({
//		track: true,
//	    show: {
//	        effect: "slideDown",
//	        delay: 250
//	      },
//	    tooltipClass:"toolTip"
//	});
	
	$('[id$=MenuId],[id$=MenuHeadId]').hover(
			function(){
				var id = $(this).attr("id");
				var patt1 = new RegExp("MenuHeadId");
				if( patt1.test(id)){
					id= id.substr(0,id.length-6) + "Id";
				}
				openMenu(id);
			},
			function(){
				var id = $(this).attr("id");
				var patt1 = new RegExp("MenuHeadId");
				if( patt1.test(id)){
					id= id.substr(0,id.length-6) + "Id";
				}
				delaymenuClose(id);
			}
	);
	
	
	
	$(".todayInfo").hover(
			function(){
				$(this).addClass('ui-state-hover');
			},
			function(){
				$(this).removeClass('ui-state-hover');
			}
	);
	
	
	$(".room").hover(
			function(){
		            $(this).addClass('ui-state-Hightlight');
		        },
		    function(){
		            $(this).removeClass('ui-state-Hightlight');
		        }
		    );
	
	
	
	
	$( "#refreshRoomTrendButton" ).button({
		icons: {
			primary: "ui-icon-refresh"
		},
		text: false
	});
	
	$( "#changeRoomViewButton" ).button({
		icons: {
			primary: "ui-icon-transferthick-e-w"
		},
		text: false
	});
	
	$( "#submitForeGroundButton" ).button({
		icons: {
			primary: "ui-icon-circle-check"
		},
		text: false
	});
	$( "#submitBackGroundButton" ).button({
		icons: {
			primary: "ui-icon-circle-check"
		},
		text: false
	});
	$( "#submitThemeButton" ).button({
		icons: {
			primary: "ui-icon-circle-check"
		},
		text: false
	});
	$( "#searchRoomButton" ).button({
		icons: {
			primary: "ui-icon-search"
		},
		text: false
	});
	$( "#freshButton" ).button({
		icons: {
			primary: "ui-icon-refresh"
		},
		text: false
	});
	$( "#poweroff" ).button({
	      icons: {
	        primary: "ui-icon-power"
	      },
	      text: false
	 });
	$( "#setting" ).button({
		icons: {
			primary: "ui-icon-gear"
		},
		text: false
	});
	$( "#dialogClose" ).button({
		icons: {
			primary: "ui-icon-close"
		},
		text: false
	});
	$( "#todayInfoPin" ).button({
	      icons: {
	        primary: "ui-icon-pin-s"
	      },
	      text: false
	 });
	

});
// //////////////////////////////////////////position


$(function() {
	function position() {
		$("#roomMenuId").position({
			of : $("#roomMenuHeadId"),
			my : "center top",
			at : "center bottom",
			collision : "flip flip"
		});
	}
	position();
});

$(function() {
	function position() {
		$("#orderMenuId").position({
			of : $("#orderMenuHeadId"),
			my : "center top",
			at : "center bottom",
			collision : "flip flip"
		});
	}
	position();

});

$(function() {
	function position() {
		$("#enteredInfoMenuId").position({
			of : $("#enteredInfoMenuHeadId"),
			my : "center top",
			at : "center bottom",
			collision : "flip flip"
		});
	}
	position();
	
});


$(function() {
	function position() {
		$("#todayMenuId").position({
			of : $("#todayMenuHeadId"),
			my : "left top",
			at : "left bottom",
			collision : "flip flip"
		});
	}
	position();

});

///////////////////////////////////////////隐藏
$(function() {
	$("#roomMenuId").hide();
	$("#orderMenuId").hide();
	$("#enteredInfoMenuId").hide();
	
});
