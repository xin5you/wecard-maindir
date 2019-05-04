//solve ie8 bug	
if(!Array.indexOf)
{
    Array.prototype.indexOf = function(obj)
    {              
        for(var i=0; i<this.length; i++)
        {
            if(this[i]==obj)
            {
                return i;
            }
        }
        return -1;
    }
}



$(document).ready(function() {
		WecardCommon.init();
});
    
WecardCommon = {

	init: function(){
			
	},
	isAmountCheck:function(value){
		var amount=/^\d+(\.\d{1,2})?$/;
		return amount.test(value);
	},
	isTradeCodeCheck:function(value){
		var pwd = /^\d{6}$/;
		return pwd.test(value)
	},
    sendMsgCurCount : 60,
	sendMsgAgain: function(that) {
		if (WecardCommon.sendMsgCurCount == 0) { 
			that.removeAttr("disabled");//启用按钮
			that.css('color','#000').val('获取验证码');//结束后重心获取验证码
		    WecardCommon.sendMsgCurCount = 60; 
		 } else { 
			 that.attr("disabled", "true");
		 	var str_t=WecardCommon.sendMsgCurCount;
			if(WecardCommon.sendMsgCurCount<10){
				str_t="0"+WecardCommon.sendMsgCurCount;
			}
			that.attr("disabled", true).val('重新发送'+str_t+'s');
			WecardCommon.sendMsgCurCount--; 
			WecardCommon.sendMsgTimer = setTimeout(function() { WecardCommon.sendMsgAgain(that); }, 1000);
		 }
	},
	sendPhoneSMS: function(phoneNumber,bizCode){
		$.ajax({
	        url:CONETXT_PATH+'/pub/sendPhoneSMS.html',
	        type: 'post',
	        dataType : "json",
	        data: {"phoneNumber": phoneNumber,
	        		"bizCode":bizCode},
	        success: function (resp) {
	            if (!resp.status) {
	            	$.toptip(resp.smg);
	            }else{
	            	$.toast("发送成功");
	            }
	        }
	    });
	},
	sendUserPhoneSMS: function(bizCode){
		$.ajax({
	        url:CONETXT_PATH+'/pub/sendUserPhoneSMS.html',
	        type: 'post',
	        dataType : "json",
	        data: {"bizCode":bizCode},
	        success: function (resp) {
	            if (!resp.status) {
	            	$.toptip(resp.smg);
	            }else{
	            	$.toast("发送成功");
	            }
	        }
	    });
	},
	
	sendMchntPhoneSMS: function(bizCode){
		$.ajax({
	        url:CONETXT_PATH+'/pub/sendMchntPhoneSMS.html',
	        type: 'post',
	        dataType : "json",
	        data: {"bizCode":bizCode},
	        success: function (resp) {
	            if (!resp.status) {
	            	$.toptip(resp.smg);
	            }else{
	            	$.toast("发送成功");
	            }
	        }
	    });
	},
	
	error: function() {
		$.alert("网络异常", "!!");
 		return;
    }
};

	