$(document).ready(function(){


var ruleRouter = new Router({
	container: '#weuiCellsForm',
    enterTimeout: 0,
    leaveTimeout: 0
});

var addRule={
		initEvent: function(){
			$('#submitBtn').on('click', addRule.addRuleTypeCheck);
			
			$('#container').html('');
			ruleRouter.go('/'+ruleTypeCode);
		},
		changefunc:function(){
			var rcode=$.trim( $('#ruleTypeCode option:checked').val());
			$('#container').html('');
			ruleRouter.go('/'+rcode);
		},
		addRuleTypeCheck:function(){
        	var rcode=$.trim( $('#ruleTypeCode option:checked').val());
         	var factor1=$.trim($('#factor1').val());
         	var param1=$.trim($('#param1').val());
         	var bTime=$.trim($('#bTime').val());
         	var eTime=$.trim($('#eTime').val());
         	var dataStat=$.trim($('#dataStat').attr('data-values'));
         	
         	if(rcode !=null && rcode=='10001000'){
         		addRule.submitBtn10001000(rcode,factor1,param1,bTime,eTime,dataStat);
         	}
         	else if(rcode !=null && rcode=='10002000'){
         		addRule.submitBtn10002000(rcode,factor1,param1,bTime,eTime,dataStat);
         	}
         	else if(rcode !=null && rcode=='20003000'){
         		addRule.submitBtn20003000(rcode,factor1,param1,bTime,eTime,dataStat);
         	}
         	else if(rcode !=null && rcode=='20004000'){
         		addRule.submitBtn20004000(rcode,factor1,param1,bTime,eTime,dataStat);
         	}
		},
		submitBtn10001000:function(rcode,factor1,param1,bTime,eTime,dataStat){
	         if(factor1==''){
	     		$.toptip('请输入充值额度');
	  			return;
	     	}else{
	     		if(!WecardCommon.isAmountCheck(factor1)){
	     			$.toptip('充值额度请输入数字，且只能保留两位小数点');
	     			return;
		  		    }
	     	}
	     	if(param1==''){
	     		$.toptip('请输入赠送额度');
	  			return;
	     	}else{
	     		if(!WecardCommon.isAmountCheck(param1)){
	     			$.toptip('赠送额度请输入数字，且只能保留两位小数点');
	     			return;
		  		    }
	     	}
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
	     	addRule.addRuleCommit(rcode,factor1,param1,bTime,eTime,dataStat);
		},
		submitBtn10002000:function(rcode,factor1,param1,bTime,eTime,dataStat){
	        if(factor1==''){
	     		$.toptip('请输入充值额度');
	  			return;
	     	}else{
	     		if(!WecardCommon.isAmountCheck(factor1)){
	     			$.toptip('充值额度请输入数字，且只能保留两位小数点');
	     			return;
		  		    }
	     	}
	     	if(param1==''){
	     		$.toptip('请输入折扣值');
	  			return;
	     	}else{
	     		if(!WecardCommon.isAmountCheck(param1) || param1>1){
	     			$.toptip('折扣值请输入小于1的数字，且只能保留两位小数点');
	     			return;
		  		    }
	     	}
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
	     	addRule.addRuleCommit(rcode,factor1,param1,bTime,eTime,dataStat);
		},
		submitBtn20003000:function(rcode,factor1,param1,bTime,eTime,dataStat){
			if(factor1==''){
         		$.toptip('请输入消费额度');
	  			return;
         	}else{
         		if(!WecardCommon.isAmountCheck(factor1)){
         			$.toptip('消费额度请输入数字，且只能保留两位小数点');
         			return;
 	  		    }
         	}
         	if(param1==''){
         		$.toptip('请输入赠送额度');
	  			return;
         	}else{
         		if(!WecardCommon.isAmountCheck(param1)){
         			$.toptip('赠送额度请输入数字，且只能保留两位小数点');
         			return;
 	  		    }
         	}
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
         	addRule.addRuleCommit(rcode,factor1,param1,bTime,eTime,dataStat);
		},
		submitBtn20004000:function(rcode,factor1,param1,bTime,eTime,dataStat){
         	if(factor1==''){
         		$.toptip('请输入消费额度');
	  			return;
         	}else{
         		if(!WecardCommon.isAmountCheck(factor1)){
         			$.toptip('消费额度请输入数字，且只能保留两位小数点');
         			return;
 	  		    }
         	}
         	if(param1==''){
         		$.toptip('请输入折扣值');
	  			return;
         	}else{
         		if(!WecardCommon.isAmountCheck(param1) || param1>1){
         			$.toptip('折扣值请输入小于1的数字，且只能保留两位小数点');
         			return;
 	  		    }
         	}
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
         	addRule.addRuleCommit(rcode,factor1,param1,bTime,eTime,dataStat);
		},
		addRuleCommit:function(rcode,factor1,param1,bTime,eTime,dataStat){
		   var transRuleDesp=$.trim($('#transRuleDesp').val());
           $.showLoading('提交中...');
  		   $.ajax({
                url:CONETXT_PATH+'/wxclient/account/addRuleCommit.html',
  	     		type : 'post',
  	  	        dataType : 'json',
  	      	    data:{
  	      	       'ruleTypeCode':rcode,
                   'factor1':factor1,
                   'param1':param1,
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
	},
    bind: function () {
	  $('#weuiCellsForm').on('change', '#ruleTypeCode', function () {
		  addRule.changefunc();
	  });
    }
};

var r10002000 = {
	url: '/10002000',
	className: 'home',
	render: function () {
	    return $('#tpl_10002000').html();
	},
	bind: function () {
	  $('#weuiCellsForm').on('change', '#ruleTypeCode', function () {
		  addRule.changefunc();
	  });
	}
}

var r20003000 = {
		url: '/20003000',
		className: 'home',
		render: function () {
		    return $('#tpl_20003000').html();
		},
		bind: function () {
		  $('#weuiCellsForm').on('change', '#ruleTypeCode', function () {
			  addRule.changefunc();
		  });
		}
	}

var r20004000 = {
		url: '/20004000',
		className: 'home',
		render: function () {
		    return $('#tpl_20004000').html();
		},
		bind: function () {
		  $('#weuiCellsForm').on('change', '#ruleTypeCode', function () {
			  addRule.changefunc();
		  });
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
 





addRule.initEvent();

})