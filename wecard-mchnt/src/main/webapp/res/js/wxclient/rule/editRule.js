$(document).ready(function(){


var ruleRouter = new Router({
	container: '#weuiCellsForm',
    enterTimeout: 0,
    leaveTimeout: 0
});

var editRule={
		initEvent: function(){
			$('#submitBtn').on('click', editRule.editRuleTypeCheck);
			ruleRouter.go('/'+ruleTypeCode);
		},
		editRuleTypeCheck:function(){
        	var transRuleId=$.trim($('#transRuleId').val());
        	var transRuleDesp=$.trim($('#transRuleDesp').val());
         	var bTime=$.trim($('#bTime').val());
         	var eTime=$.trim($('#eTime').val());
         	var dataStat=$.trim( $('#dataStat option:checked').val());
         	if(bTime==''){
         		$.toptip('请输入开始日期');
	  			return;
         	}
         	if(eTime==''){
         		$.toptip('请输入结束日期');
	  			return;
         	}
         	if(bTime>eTime){
         		$.toptip('开始日期不能大于结束日期');
         		return;
         	}
         	if(dataStat==null || dataStat==''){
         		dataStat='0';
         	}
         	editRule.editRuleCommit(transRuleId,transRuleDesp,bTime,eTime,dataStat);
		},
		
		editRuleCommit:function(transRuleId,transRuleDesp,bTime,eTime,dataStat){
		   var transRuleDesp=$.trim($('#transRuleDesp').val());
           $.showLoading('提交中...');
  		   $.ajax({
                url:CONETXT_PATH+'/wxclient/account/editRuleCommit.html',
  	     		type : 'post',
  	  	        dataType : 'json',
  	      	    data:{
  	      	       'transRuleId':transRuleId,
                   'transRuleDesp':transRuleDesp,
                   'bTime':bTime,
                   'eTime':eTime,
                   'dataStat':dataStat
  	      	    },
  	      	    success:function(data){
  	        		$.hideLoading();
  	        		if(data.status){
  	        			$('#cellsFormBtn').html('');
  	        			 ruleRouter.go('/msg');
                	}else{
                		$.toptip(data.msg);
                	}
  	             },
  	             error:function (XMLHttpRequest, textStatus, errorThrown) {
  	            	$.hideLoading();
    	        	$.toptip('提交失败，请联系管理员');
    	        	 
    	    	}
	  	    });
		}
};

var r10001000 = {
	url: '/10001000',
	className: 'home',
	render: function () {
	    return $('#tpl_10001000').html();
	}
};

var r10002000 = {
	url: '/10002000',
	className: 'home',
	render: function () {
	    return $('#tpl_10002000').html();
	}
}

var r20003000 = {
		url: '/20003000',
		className: 'home',
		render: function () {
		    return $('#tpl_20003000').html();
		}
	}

var r20004000 = {
		url: '/20004000',
		className: 'home',
		render: function () {
		    return $('#tpl_20004000').html();
		}
	}

var msg = {
		url: '/msg',
	    className: 'home',
	    render: function () {
	        return $('#tpl_success_msg').html();
	    }
};

ruleRouter.push(r10001000)
          .push(r10002000)
          .push(r20003000)
          .push(r20004000)
          .push(msg);
 
editRule.initEvent();

})