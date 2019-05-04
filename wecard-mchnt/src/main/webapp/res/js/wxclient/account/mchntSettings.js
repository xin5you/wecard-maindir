$(document).ready(function(){

var router = new Router({
		container: '#container',
	    enterTimeout: 50,
	    leaveTimeout: 50
});


var mchntSettings={
		initEvent: function(){
			 $('#userSetBtn').on('click', mchntSettings.userSetCommit);
			 $('#sendPhoneCode').on('click', mchntSettings.sendPhoneSMS);
		},
		sendPhoneSMS:function(){
			 var that=$('#sendPhoneCode');
			 WecardCommon.sendMsgAgain(that)
			 WecardCommon.sendMchntPhoneSMS("02"); //商户发送短信验证
		  
		},
		userSetCommit: function(){
			var $that=$("#userSetBtn");
	  		  var phoneCode=$.trim($('#phoneCode').val());
	  		  var password=$.trim($('#password').val());
	  		  var passwordConfirm=$.trim($('#passwordConfirm').val());

	  		  if(phoneCode==''){
		  			$.toptip('请输入手机动态验证码');
		  			return;
			   }
	  		   if(password=='' || passwordConfirm==''){
			  			$.toptip('请输入您的6位数字交易密码');
			  			return;
				   }
	  		   if(password != passwordConfirm){
				  		$.toptip('您的确认密码和交易密码不匹配,请重新输入');
				  		return;
	  		   }
	  		   if(!WecardCommon.isTradeCodeCheck(password)){
	  			    $.toptip('交易密码请重新输入6位数字');
				  		return;
	  		   }
	  		   $.showLoading('提交中...'); 
	  		   $.ajax({
	  	    		url : CONETXT_PATH + '/wxclient/account/mchntPwdRetCommit.html' ,
	  	     		type : 'post',
	  	  	        dataType : 'json',
	  	      	    data:{
	  	                  'phoneCode':phoneCode,
	  	                  'password':RSAUtils.encryptedString(publicKey,password.split("").reverse().join("")),
	  	                  'passwordConfirm':RSAUtils.encryptedString(publicKey,passwordConfirm.split("").reverse().join(""))
	  	      	    },
	  	        	success:function(data){
	  	      		$.hideLoading();
	        		if(data.code=='00'){
		  	            router.go('/msg');
	        		 }else{
	        				if(data.info==null || data.info=='' ){
	        					$.toptip("客户重置密码失败，请重新填写");
	        				}else{
	        					$.toptip(data.info);
	        				}
	        			}
	  	             },
	  	             error:function(){
	  
	  	             }
	  	    	});
		}
};
var msg = {
	url: '/msg',
    className: 'msg',
    render: function () {
        return $('#tpl_msg').html();
    }
};
router.push(msg);
mchntSettings.initEvent();

});