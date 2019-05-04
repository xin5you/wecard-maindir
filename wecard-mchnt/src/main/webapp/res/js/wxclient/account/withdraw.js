$(document).ready(function(){
	

var router = new Router({
	container: '#container',
    enterTimeout: 50,
    leaveTimeout: 50
});


var msg = {
	url: '/msg',
    className: 'msg',
    render: function () {
        return $('#tpl_msg').html();
    }
};

var emsg = {
		url: '/emsg',
	    className: 'msg',
	    render: function () {
	        return $('#tpl_error_msg').html();
	    }
	};

router.push(msg).push(emsg);


var withdraw={
		initEvent: function(){
			 $('#submitBtn').on('click', withdraw.widthdrawCommit);
		},
		widthdrawCommit:function(){
         	var pwd=$.trim($('#password').val());
         	var amount=$.trim($('#withdrawMoney').val());
         	if(amount==''){
         		$.toptip('请输入您的提现金额');
	  			return;
         	}else{
         		if(!WecardCommon.isAmountCheck(amount)){
         			$.toptip('提现金额请输入数字，且只能保留两位小数点');
         			return;
 	  		    }
         	}
         	if(pwd==''){
         		$.toptip('请输入您的6位数字交易密码');
	  			return;
         	}else{
 	  		   if(!WecardCommon.isTradeCodeCheck(pwd)){
	  			    $.toptip('交易密码请输入6位数字');
				  		return;
	  		   }
         	}
         	$.showLoading('提交中...'); 
          	$.ajax({
                 type: 'POST',
                 data: {
                    'amount':amount,
                    'password':RSAUtils.encryptedString(publicKey,pwd.split("").reverse().join(""))
     	         },
                 url:CONETXT_PATH+'/wxclient/account/widthdrawCommit.html',
                 dataType: 'json',
                 success: function(data){
                	 $.hideLoading();
                	
                	 if(data.code=='00'){
		  	            router.go('/msg');
		  	            $('#retTransAmt').html(data.transAmt);
		  	            $('#retCardHolderFee').html(data.cardHolderFee);
	        		 }else{
	        			router.go('/emsg');
	        			$('.weui_msg_desc').html(data.info);
	        		 }
                 },
     	         error:function (XMLHttpRequest, textStatus, errorThrown) {
     	    	}
          	})
		}
};

withdraw.initEvent();
})