$(function () {


	var unifiedOrder = {
		isPayState:true,
        init: function () {
        	unifiedOrder.initEvent();
        	
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
        	    		    jsApiList: ['hideOptionMenu','chooseWXPay'] //使用接口时，这里必须先声明 
        	    		});
        	    		wx.ready(function(){
                    		wx.hideOptionMenu();// 隐藏右上角菜单接口
                    	});
        	    	}else{
        	    		WecardCommon.errorMsg();
        	    	}
        	    }
        	});
        },
        initEvent:function(){
        	 $('#confirm_next').on("click", unifiedOrder.TransPayComfirmOrder);
        },

        java2TxnBusiness: function(tableNum,wxPrimaryKey,orderKey,pinTxn) {
        	if(unifiedOrder.isPayState){
        		unifiedOrder.isPayState=false;
        		$("#confirm_next").attr('disabled',"true");
	        	$.ajax({
	        		url : CONETXT_PATH + '/trans/order/transOrderJava2TxnBusiness.html',// 调交易核心 请求支付
	        		type : 'post',
	    	        dataType : "json",
	        	    data: {
	        	    	'tableNum' :tableNum,
	        	    	'wxPrimaryKey' : wxPrimaryKey,
	        	    	'orderKey':orderKey,
	        	    	'pinTxn':pinTxn
	        	    },
	        	    success:function(data2){
	        	    	unifiedOrder.isPayState=true;
	        	    	$("#confirm_next").removeAttr('disabled');
	        	    	if(data2 != null && data2 != '') {
	        	    		if (data2.code == "00") {// 交易成功
	        	    			location.href= CONETXT_PATH + '/trans/order/orderPaySuccess.html?wxTransLogKey='+wxPrimaryKey;
	        	    		} else {
	        	    			location.href= CONETXT_PATH + '/trans/order/orderPayFail.html?wxTransLogKey='+wxPrimaryKey;
	        	    		}
	        	    	} else {
	        	    		WecardCommon.errorMsg();
	        	    	}
	        	    },
	        	    error: function(XMLHttpRequest, textStatus, errorThrown) {  
	        	    	WecardCommon.errorMsg();
	        	    	unifiedOrder.isPayState=true;
	        	    	$("#confirm_next").removeAttr('disabled');
	        	    }
	        	});
        	}
        },
        //收银台 支付确认订单
		TransPayComfirmOrder:function(){
			var orderKey=$('#orderKey').val();
         	var payType=$('input:checkbox[name="payType"]:checked').val();
         	if(payType==null  || payType==""){
         		$.alert("请选择支付方式", "系统提示");
         		return;
         	}
         	$.ajax({
        		url : CONETXT_PATH + '/trans/order/insertWxTransLogByTransOrder.html',// 插入微信端流水
        		type : 'post',
    	        dataType : "json",
        	    data: {
        	    	'sponsor' : '00',
        	    	'payType':payType,
        	    	'orderKey':orderKey
        	    },
        	    success:function(wxTransLog){
    	    		if (wxTransLog.orderNum > 0) {
    	    			location.href = CONETXT_PATH + '/trans/order/orderVailFail.html?info=11';
    	    			return false;
    	    		}
    	    		if (wxTransLog==null || typeof(wxTransLog) == "undefined" || wxTransLog.wxPrimaryKey == null || wxTransLog.wxPrimaryKey == '' || typeof(wxTransLog.wxPrimaryKey) == "undefined") {
    	    			WecardCommon.errorMsg();
    	    			return false;
    	    		}
    	    		if('VIPCARD_PAY'==payType){//会员卡支付
	        	    	$.ajax({
			        		url : CONETXT_PATH + '/trans/order/doCustomerNeed2EnterPassword.html',// 验密
			        		type : 'post',
			    	        dataType : "json",
			        	    data: {
			        	    	'orderKey':orderKey
			        	    },
			        	    success:function(resp){
		        	    		if (resp.code == "1") {			// 需要密码
		                			$("#transAmt").text($('#txnAmount').val());
		        	    			unifiedOrder.hkbkeybroad(wxTransLog.tableNum,wxTransLog.wxPrimaryKey,orderKey);
		        	    		} else if (resp.code == "0") {
		        	    			unifiedOrder.java2TxnBusiness(wxTransLog.tableNum,wxTransLog.wxPrimaryKey,orderKey,'');
		        	    		} else {
			        	    		WecardCommon.errorMsg();
			        	    	}
			        	    },
			        	    error: function(XMLHttpRequest, textStatus, errorThrown) {
			        	    	WecardCommon.errorMsg();    
			        	    }
			        	});
    	    		}else if('WECHAT_PAY'==payType){  //微信支付
    	    			$.ajax({
    	    				url : CONETXT_PATH + '/trans/order/transOrderChatQuickPay.html',
    	    				type : 'post',
    	    				dataType : 'json',
    	    				data : {
    	    					'wxTransLogKey' : wxTransLog.wxPrimaryKey,
    	    				},
    	    				success : function(result){
    	    					if (result != null && result != '') {
    	    						wx.chooseWXPay({
    	    						    timestamp: result.timeStamp, // 支付签名时间戳
    	    						    nonceStr: result.nonceStr, // 支付签名随机串
    	    						    package: 'prepay_id='+result.prepay_id, // 统一支付接口返回的prepay_id参数值
    	    						    signType: 'MD5', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
    	    						    paySign: result.paySign, // 支付签名
    	    						    success: function (res){
    	    						    	//微信支付请求结果发送给商户端
    	    						    	$.ajax({
    	    					        		url : CONETXT_PATH + '/trans/order/transOrderWeChantQuickPayOrderQuery.html',// 微信客服消息接口
    	    					        		type : 'post',
    	    					    	        dataType : "json",
    	    					        	    data: {
    	    					        	    	'wxPrimaryKey' : wxTransLog.wxPrimaryKey,
    	    					        	    },
    	    					        	    success:function(data2){
    	    					        	    	if(data2 != null && data2 != '') {
    	    					        	    		if (data2.code == "00") {// 交易成功
    	    					        	    			location.href= CONETXT_PATH + '/trans/order/orderPaySuccess.html?wxTransLogKey='+wxTransLog.wxPrimaryKey;
    	    					        	    		} else {
    	    					        	    			location.href= CONETXT_PATH + '/trans/order/orderPayFail.html?wxTransLogKey='+wxTransLog.wxPrimaryKey;
    	    					        	    		}
    	    					        	    	} else {
    	    					        	    		WecardCommon.errorMsg();
    	    					        	    	}
    	    					        	    },
    	    					        	    error: function(XMLHttpRequest, textStatus, errorThrown) {  
    	    					        	    	WecardCommon.errorMsg();    
    	    					        	    }
    	    					        	});
    	    						    },
    		    						 cancel:function(res){
    		 						    	
    		 			                }
    	    						});
    	    					} 
    	    				},
    	    				error : function() {
    	    					WecardCommon.errorMsg();
    	    				}
    	    			});
    	    		}
        	    },
        	    error: function(XMLHttpRequest, textStatus, errorThrown) {  
        	    	WecardCommon.errorMsg();
        	    	$.hideLoading();
        	    }
        	});
		},
		hkbkeybroad:function(tableNum,wxPrimaryKey,orderKey){
			new hkbpswKeybroad.keyBroadShow({
			     'hideButton': 'pay_close',   //其他的关闭按钮
			     'showNumEle': 'numBox',   //点击出现键盘的元素，并且存储数字，ID选择器
			     'saveNumEle': 'num_box',  //用于显示当前金额的值的元素，用于判断光标，class选择器
			     'clearButton': false,       //清除数字按钮
			     'saveInput': 'pasd_input',   //存入键盘的值，input元素的class选择器
			     'MaxNum': '6', //键盘可以输入的最大位数
			        Fn:function(){   //输入密码之后所做的function
			        	
                    	var pass = $("#numBox").text();
                    	var pinTxn = RSAUtils.encryptedString(key, pass.split("").reverse().join(""));
                    	unifiedOrder.java2TxnBusiness(tableNum,wxPrimaryKey,orderKey,pinTxn);
			        }
			     }).run();
//			confirmChoose.inputChoose();
			passWordBox.passWordBoxShow();
//			passWordBox.passWordBoxHide();
		}
    };
    unifiedOrder.init();
});