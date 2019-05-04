$(document).ready(function(){

	mchntReg.initEvent();
});	

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
router.push(msg);

var mchntReg={
		initEvent: function(){
			 $('#sendPhoneCode').on('click', mchntReg.sendPhoneSMS);
			 $('#submitEmployeeRegBtn').on('click', mchntReg.mchntRegCommit);
		},
		sendPhoneSMS:function(){
			 var phoneNumber=$.trim($('#phoneNumberPage').val());
			 if(phoneNumber==''){
				 $('#weui_cells_tips').html('请输入手机号，获取动态验证码');
		 		return;
			 }
			 var that=$('#sendPhoneCode');
			 WecardCommon.sendMsgAgain(that);
			 WecardCommon.sendPhoneSMS(phoneNumber,"01");
		},
		mchntRegCommit:function(){
        	var $that=$('#employeeRegBtn');
         	var phoneNumber=$.trim($('#phoneNumberPage').val());
         	var phoneCode=$.trim($('#phoneCodePage').val());
         	var userName=$.trim($('#userNamePage').val());
         	var empNo=$.trim($('#empNoPage').val());
           	if(phoneNumber==''){
           		$('#weui_cells_tips').html('请输入手机号，获取动态验证码');
           		return;
           	}
           	if(phoneCode==''){
           		$('#weui_cells_tips').html('请输手机发送的动态验证码');
           		return;
           	}
          	$.ajax({
                 type: 'POST',
                 data: {
                 	'phoneNumber':phoneNumber,
                 	'phoneCode':phoneCode,
                 	'userName':userName,
                 	'empNo':empNo
     	        },
                 url:CONETXT_PATH+'/wxclient/manager/employeeCommit.html',
                 dataType: 'json',
                 success: function(data){
                	 $that.removeAttr('disabled');
                 	if(data.status){
                 		$('#weui_cells_tips').html('注册成功');
                 		 router.go('/msg');
                         return;
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