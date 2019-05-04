$(function () {
	
	var router = new Router({
		container: '#container',
	    enterTimeout: 50,
	    leaveTimeout: 50
	});

	var mchntReg={
			initEvent: function(){
				 $('#mchantRegBtn').on('click', mchntReg.mchntRegCommit);
				 $('#sendPhoneCode').on('click', mchntReg.sendPhoneSMS);
			},
			sendPhoneSMS:function(){
				 var phoneNumber=$.trim($('#phoneNumber_page').val());
				 if(phoneNumber==''){
			  		$.toptip('请输入手机号，获取动态验证码');
			 		return;
				 }
				 var that=$('#sendPhoneCode');
				 WecardCommon.sendMsgAgain(that)
				 WecardCommon.sendPhoneSMS(phoneNumber,"01");
			  
			},
			mchntRegCommit:function(){
            	var $that=$('#mchantRegBtn');
             	var inviteCode=$.trim($('#inviteCode_page').val());
             	var phoneNumber=$.trim($('#phoneNumber_page').val());
             	var phoneCode=$.trim($('#phoneCode_page').val());
             	
               	if(inviteCode==''){
               		$.toptip('请输入平台邀请码');
               		return;
               	}
               	if(phoneNumber==''){
               		$.toptip('请输入手机号，获取动态验证码');
               		return;
               	}
               	if(phoneCode==''){
               		$.toptip('请输手机发送的动态验证码');
               		return;
               	}
             	$that.attr('disabled', true);
             	$.showLoading('提交中...'); 
              	$.ajax({
                     type: 'POST',
                     data: {
                     	'inviteCode':inviteCode,
                     	'phoneNumber':phoneNumber,
                     	'phoneCode':phoneCode,
         	        },
                     url:CONETXT_PATH+'/wxclient/mchnt/mchantRegCommit.html',
                     dataType: 'json',
                     success: function(data){
                    	 $that.removeAttr('disabled');
                    	 $.hideLoading();
                     	if(data.status){
                     		$('#container').html('');
                     		router.go('/msg');
                     	}else{
                     		$('#weui_cells_tips').html(data.msg)
                     	}
                     },
         	        error:function (XMLHttpRequest, textStatus, errorThrown) {
         	        	 $.hideLoading();
         	        	 $that.removeAttr('disabled');
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
    mchntReg.initEvent();
});