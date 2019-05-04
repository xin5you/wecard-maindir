$(function () {

	var router = new Router({
		container: '#container',
	    enterTimeout: 50,
	    leaveTimeout: 50
	});
	
	var userManagerReg={
		initEvent: function(){
			 $('#mchantRegBtn').on('click', userManagerReg.userManagerRegCommit);
			 $('#sendPhoneCodeBtn').on('click', userManagerReg.sendPhoneSMS);
		},
		sendPhoneSMS:function(){
			 var that=$("#sendPhoneCodeBtn");
    		var phoneNumber=$.trim($("#phoneNumber_page").val());
    		if(phoneNumber==''){
    			$.toptip("请输入手机号，获取动态验证码")
        		return;
        	}
			 WecardCommon.sendMsgAgain(that)
			 WecardCommon.sendPhoneSMS(phoneNumber,"01");
		},
		userManagerRegCommit:function(){
        	var $that=$("#mchantRegBtn");
         	var shareId=$.trim($("#shareId_page").val());
         	var phoneNumber=$.trim($("#phoneNumber_page").val());
         	var phoneCode=$.trim($("#phoneCode_page").val());
         	
           	if(shareId==''){
           		$.toptip("链接访问错误，请重新点击链接");
           		return;
           	}
           	if(phoneNumber==''){
           		$.toptip("请输入手机号，获取动态验证码");
           		return;
           	}
           	if(phoneCode==''){
           		$.toptip("请输手机发送的动态验证码");
           		return;
           	}
         	$that.attr("disabled", true);
         	$.showLoading('提交中...');
          	$.ajax({
                 type: 'POST',
                 data: {
                 	"shareId":shareId,
                 	"phoneNumber":phoneNumber,
                 	"phoneCode":phoneCode,
     	        },
                 url:CONETXT_PATH+'/wxclient/mchnt/employeeRegCommit.html',
                 dataType: 'json',
                 success: function(data){
                	 $.hideLoading();
                	 $that.removeAttr("disabled");
                 	if(data.status){
                 		$('#container').html('');
                 		router.go('/msg');
                 	}else{
                 		$('#weui_cells_tips').html(data.msg)
                 	}
                 },
     	        error:function (XMLHttpRequest, textStatus, errorThrown) {
     	        	 $that.removeAttr("disabled");
     	    	}
          	});
     	
		}
	}
    var msg = {
    		url: '/msg',
    	    className: 'msg',
    	    render: function () {
    	        return $('#tpl_success_msg').html();
    	    }
    	};

	router.push(msg);
	userManagerReg.initEvent();
});