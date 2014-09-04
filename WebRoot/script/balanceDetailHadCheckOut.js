$(function() {
  

    $( ".accordion" ).accordion({
    	collapsible: true,
    	active:false,
    	heightStyle: "content"
    });
    
    $( "input[type=submit],button" ).button();
    
    $( "#beginDate_id" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#endDate_id" ).datepicker({ dateFormat: "yy-mm-dd" });
    
    $( "#beginDate_ignore_button" ).button({
    	icons: {
    		primary: "ui-icon-close"
    	},
    	text: false
    });
    
    $( "#endDate_ignore_button" ).button({
    	icons: {
    		primary: "ui-icon-close"
    	},
    	text: false
    });
    
    
    $( "#queryBalance_submit" ).button({
    	icons: {
    		primary: "ui-icon-search"
    	},
    	text: false
    });
    
    
    $( "#delAccounts" ).button({
    	icons: {
    		primary: "ui-icon-minus"
    	},
    	text: false
    });
    
    
    
    $(".account-item").hover(
            function(){
                $(this).addClass('over');
            },
            function(){
                $(this).removeClass('over');
            }
        );

    
    
  });



function ignoreDate(n){
	if(n== 1)
		$("#beginDate_id").val("");
	else
		$("#endDate_id").val("");
}



function chkbox_del(){
	
	var answer=confirm('警告：确定删除选中项？选择删除将不可恢复！(删除后，将导致该用户信息入账、消费、房费不平衡！)');
	 if(answer){
		 
		 //页面跳转。
		 if(window.prompt("请输入验证  ","") != "sa_sa"){
			 alert("验证失败");
			 return ;
		 }
	 }
	 else
		 return;
	
	var ell = document.getElementsByTagName('input');
	var llen = ell.length;
	var str_cid='';
	for(var i=0;i<llen;i++)
	{
		if(ell[i].name=="delIds" && ell[i].checked == true ){
			if(ell[i].value!=''){
				str_cid +="delIds="+ell[i].value;
				str_cid +="&";
			}
		}
		if(ell[i].name =="delChargeIds" && ell[i].checked == true ){
			if(ell[i].value!=''){
				str_cid +="delChargeIds="+ell[i].value;
				str_cid +="&";
			}
		}
	 }
	 if(str_cid==''){
	   alert('请至少选中一项要删除的内容！');
	   return;
	  }
	 
	//三个过滤器 目的是删除之后 跳转回来 继续保存 以前的信息
	 str_cid += "checkId="+$("#checkId_id").val() + "&";
	 str_cid += "beginDate="+$("#beginDate_id").val() + "&";
	 str_cid += "endDate="+$("#endDate_id").val() + "&";
	 str_cid += "type="+$("#type_id").val();
	 
	 window.location.href='requestDelAccountInHadCheckOutActionForBalance?'+str_cid;
}







