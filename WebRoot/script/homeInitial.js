
// //////////////////////////////////////////////////menu
$(function() {
	
	
	$(".room").hover(
	      /*  function(){
	            $(this).addClass('ui-state-hover');
	        },
	        function(){
	            $(this).removeClass('ui-state-hover');
	        }*/
			  function(){
	            $(this).addClass('ui-state-Hightlight');
	        },
	        function(){
	            $(this).removeClass('ui-state-Hightlight');
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
	
	
	$(".menu").menu();
	
	$(".todayMenu").hover(
			function(){
				hoverMenuId = "todayInfoHead";
				openMenu("todayInfoHead",3);
			},
			function(){
				
				hoverMenuId = null;
				delaymenuClose("todayInfoHead",3);
			}
	);
	
	
	$(".roomMenu").hover(
			function(){
				hoverMenuId = "roomMenu";
				openMenu("roomMenu",1);
			},
			function(){
				
				hoverMenuId = null;
				delaymenuClose("roomMenu",1);
			}
	);
	
	$(".orderMenu").hover(
			function(){
				hoverMenuId = "orderMenu";
				openMenu("orderMenu",2);
			},
			function(){
				
				hoverMenuId = null;
				delaymenuClose("orderMenu",2);
			}
	);
	
	$(".checkInInfoMenu").hover(
			function(){
				hoverMenuId = "checkInInfoMenu";
				openMenu("checkInInfoMenu",4);
			},
			function(){
				
				hoverMenuId = null;
				delaymenuClose("checkInInfoMenu",4);
			}
	);
	
	
	
	
	
	$( "input[type=submit],button,input[type=button]" ).button();
	$( "#balanceButton" ).button();
	
	
	
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
	
	$( "#addOrder_od_enterDate" ).datepicker({ dateFormat: "yy-mm-dd" });
	$( "#updateOrder_enterDate" ).datepicker({ dateFormat: "yy-mm-dd" });
	
	$("#tabs").tabs({
		event : "mouseover"
	});


});
// //////////////////////////////////////////position
$(function() {
	function position() {
		$("#roomMenu").position({
			of : $("#roomMenuHead"),
			my : "center top",
			at : "center bottom",
			collision : "flip flip"
		});
	}
	position();
});

$(function() {
	function position() {
		$("#orderMenu").position({
			of : $("#orderMenuHead"),
			my : "center top",
			at : "center bottom",
			collision : "flip flip"
		});
	}
	position();

});

$(function() {
	function position() {
		$("#checkInInfoMenu").position({
			of : $("#checkInInfoMenuHead"),
			my : "center top",
			at : "center bottom",
			collision : "flip flip"
		});
	}
	position();
	
});


$(function() {
	function position() {
		$("#todayInfoHead").position({
			of : $("#todaySummary"),
			my : "left top",
			at : "left bottom",
			collision : "flip flip"
		});
	}
	position();

});
