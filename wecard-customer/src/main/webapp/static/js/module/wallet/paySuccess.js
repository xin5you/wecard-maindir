$(function () {
	
	var paySuccess = {
        init: function () {
        	paySuccess.initJsTicket();
        	//关闭页面
        	$('.main_btn').bind("click", function(){
	        	wx.closeWindow();
	        });
        	//扫一扫
	        $('#scanBtn').bind("click", function(){
		        wx.scanQRCode({
				    needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
				    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
				    success: function (res) {
					}
				});
			});
        },
        initJsTicket:function(){
        	$.ajax({
        		url : CONETXT_PATH + '/wxapi/jsTicket.html' ,
        		type : 'post',
    	        dataType : "json",
        	    data:{url : location.href.split('#')[0]},
        	    success:function(data){
        	    	var ajaxobj=data;
        	    	var errcode = ajaxobj.errcode;
        	    	var sign = ajaxobj.data;
        	    	if(errcode == 0){//没有错误
        	    		wx.config({
        	    		    debug: false,//true的时候可以alert信息 
        	    		    appId: sign.appId,
        	    		    timestamp: sign.timestamp,
        	    		    nonceStr: sign.nonceStr, 
        	    		    signature: sign.signature,
        	    		    jsApiList: ['hideOptionMenu','scanQRCode'] //使用接口时，这里必须先声明 
        	    		});
        	    		wx.ready(function(){
                    		wx.hideOptionMenu();// 隐藏右上角菜单接口
                    	});
        	    	}else{
        	    		WecardCommon.error();
        	    	}
        	    }
        	});
        }
    };
	paySuccess.init();
});