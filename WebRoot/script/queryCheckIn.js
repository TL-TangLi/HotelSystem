$(function() {
  
    $( "#accordion" ).accordion({
      collapsible: true,
      heightStyle: "content"
    });
    
    
    $(".checkIninfo-item").hover(
            function(){
                $(this).addClass('over');
            },
            function(){
                $(this).removeClass('over');
            }
        );

    
    $(".checkIninfo-item").dblclick(function () {
    	
    			var id =  $(this).children(".del_tr").children(".delInput").val();
    			
    			//如果还未退房
    			if($(this).children(".del_tr").children(".checkOut").html() == 0){
    				var roomId = $(this).children(".roomId").html();
    				window.open("requestBalanceDetailActionForBalance?checkId="+id +"&roomId="+roomId);
    			}
    			else
    				window.open("requestBalanceDetailHadCheckOutActionForBalance?checkId="+id);
    			
            }
          );
    
    
    $( "input[type=submit],button" ).button();
    
    $( ".ignorButton" ).button({
    	icons: {
    		primary: "ui-icon-close"
    	},
    	text: false
    });
    
    $( "#delButton" ).button({
    	icons: {
    		primary: "ui-icon-minusthick"
    	},
    	text: false
    });
    $( "#query_submit" ).button({
    	icons: {
    		primary: "ui-icon-search"
    	},
    	text: false
    });
    
    $( "#enterDateBegin_id" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#enterDateEnd_id" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#outDateBegin_id" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#outDateEnd_id" ).datepicker({ dateFormat: "yy-mm-dd" });
  });



function ignoreById(id){
	$("#"+id).val("");
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
	 str_cid += "enterDateBegin="+$("#enterDateBegin_id").val() + "&";
	 str_cid += "enterDateEnd="+$("#enterDateEnd_id").val() + "&";
	 str_cid += "outDateBegin="+$("#outDateBegin_id").val() + "&";
	 str_cid += "outDateEnd="+$("#outDateEnd_id").val() ;
	 
	 var answer=confirm('警告：确定删除选中项？选择删除将不可恢复！(删除入住信息 相应账目不会删除！)');
	 if(answer){
		 //页面跳转。
		 if(window.prompt("请输入验证  ","") == "sa_sa") 
			 window.location.href='CheckInAction!del.action?'+str_cid;
			else
				alert("验证失败");
	 }
}





