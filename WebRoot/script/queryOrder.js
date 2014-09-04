$(function() {
  
    $( "#accordion" ).accordion({
      collapsible: true,
      active:false,
      heightStyle: "content"
    });
    
    
    $(".order-item").hover(
            function(){
                $(this).addClass('over');
            },
            function(){
                $(this).removeClass('over');
            }
        );

    
    $(".order-item").dblclick(function () {
    		
  				var id = $(this).children(".orderId").html();
  				requestOrder(id);	
    	
//    			alert($(this).children(".del_tr").children(".delInput").val());
            }
          );
    $( "input[type=submit],button" ).button();
    $( "#enterDate_ignore_button" ).button({
		icons: {
			primary: "ui-icon-close"
		},
		text: false
	});
    $( "#orderTime_ignore_button" ).button({
    	icons: {
    		primary: "ui-icon-close"
    	},
    	text: false
    });
    $( "#delOrderButton" ).button({
    	icons: {
    		primary: "ui-icon-minusthick"
    	},
    	text: false
    });
    $( "#orderQuery_submit" ).button({
    	icons: {
    		primary: "ui-icon-search"
    	},
    	text: false
    });
    
    $( "#enterDate_id" ).datepicker({ dateFormat: "yy-mm-dd" });
	$( "#orderTime_id" ).datepicker({ dateFormat: "yy-mm-dd" });
	$( "#updateOrder_enterDate" ).datepicker({ dateFormat: "yy-mm-dd" });
  });

function ignoreOrderTime(){
	
	$("#orderTime_id").val("");
}
function ignoreEnterDate(){
	$("#enterDate_id").val("");
	
	
}


function nowToStr(){
	var now=new Date();
	var str = now.getFullYear()+"-";
	if(now.getMonth()+1 <10)
		str+= "0"+(now.getMonth()+1)+"-";
	else
		str+=(now.getMonth()+1)+"-";
	
	if(now.getDate()<10)
		str+= "0"+now.getDate();
	else
		str+=now.getDate();
	return str;
}


function BodyInitial(){
	
//	$("#enterDate_id").val(nowToStr());
}

function chkbox_del(){
	var ell = document.getElementsByTagName('input');
	var llen = ell.length;
	var str_cid='';
	for(var i=0;i<llen;i++)
	{
		if(ell[i].type=="checkbox" && ell[i].checked == true ){
			if(ell[i].value!=''){
				str_cid +="delIds="+ell[i].value;
				str_cid +="&";
			}
		}
	 }
	 if(str_cid==''){
	   alert('请至少选中一项要删除的内容！');
	   return;
	  }
	 
	 //三个过滤器 目的是删除之后 跳转回来 继续保存 以前的信息
	 str_cid += "enterDate="+$("#enterDate_id").val() + "&";
	 str_cid += "orderTime="+$("#orderTime_id").val() + "&";
	 str_cid += "info="+$("#info_id").val();
	 var answer=confirm('警告：确定删除选中项？选择删除将不可恢复！');
	 if(answer){
		 //页面跳转。
		 if(window.prompt("请输入验证  ","") == "sa_sa") 
			 window.location.href='orderQueryAction!delOrder.action?'+str_cid;
			else
				alert("验证失败");
	 }
}









