$(function () {
	var webSocket = new ReconnectingWebSocket("ws://"+wsUrl+"websocket_cashier.html?bizId=qrCode&openId="+$.trim($("#openid").val()));
	
    webSocket.onmessage = function(event){
    	var data=JSON.parse(event.data);
     	var sendData={};
    	 if(data.sendType=='20' && data.payType=='VIPCARD_PAY'){  //请输入密码的请求 会员卡支付
    			$("#transAmt").text(data.transAmt);
    			var publicKey = new RSAUtils.getKeyPair(data.publicKeyExponent, "", data.publicKeyModulus);
    			new hkbpswKeybroad.keyBroadShow({
    	       	     'hideButton': 'pay_close',   //其他的关闭按钮
    	       	     'showNumEle': 'numBox',   //点击出现键盘的元素，并且存储数字，ID选择器
    	       	     'saveNumEle': 'num_box',  //用于显示当前金额的值的元素，用于判断光标，class选择器
    	       	     'clearButton': false,       //清除数字按钮
    	       	     'saveInput': 'pasd_input',   //存入键盘的值，input元素的class选择器
    	       	     'MaxNum': '6', //键盘可以输入的最大位数
    	       	        Fn:function(){   //输入密码之后所做的function
    	           			var pass = $("#pasd_input").val();
    	           			var encrypedPwd = RSAUtils.encryptedString(publicKey, pass.split("").reverse().join(""));
    	           			sendData["fromUser"]=$.trim($("#openid").val());
                        	sendData["toUser"]=data.fromUser;
                        	sendData["text"]=encrypedPwd;
                        	sendData["reqType"]="C";
                        	sendData["transAmt"]=data.transAmt;
                        	sendData["oriTxnAmount"]=data.oriTxnAmount;
                        	sendData["sendType"]="20";
                        	sendData["payType"]=data.payType;
                        	sendData["wxTransLogKey"]=data.wxTransLogKey;
    			        	webSocket.send(JSON.stringify(sendData));
    	       	        }
    	       	    }).run();
    			passWordBox.passWordBoxShow();
    		}else if(data.sendType=='20' && data.payType=='WECHAT_PAY'){//微信支付
    			$.ajax({
    				url : CONETXT_PATH + '/pay/BSCweChatQuickPay.html',
    				type : 'post',
    				dataType : 'json',
    				data : {
    					'wxTransLogKey' : data.wxTransLogKey
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
//    						    	$.showLoading('正在请求支付.....');
    						    	$.ajax({
    					        		url : CONETXT_PATH + '/pay/CSBweChantQuickPayOrderQuery.html',// 微信客服消息接口
    					        		type : 'post',
    					    	        dataType : "json",
    					        	    data: {
    					        	    	'wxPrimaryKey' : wxTransLog.wxPrimaryKey
    					        	    },
    					        	    success:function(data2){
    					        	    	if(data2 != null && data2 != '') {
    					        	    		if (data2.code == "00") {// 交易成功
    					        	    			location.href = CONETXT_PATH + '/jfPay/paySuccess.html?transId=W71&transAmt='+data2.transAmt+"&wxTransLogKey="+wxTransLog.wxTransLogKey;
    					        	    		} else {
    					        	    			location.href = CONETXT_PATH + '/jfPay/payFailed.html?transId=W71&transAmt='+data2.transAmt
    																+'&transCode='+data2.code;
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
	 						    	wx.closeWindow();//支付取消
	 			                }
    						});
    					} 
    				},
    				error : function() {
    					$that.removeAttr("disabled");
    					$.toptip('微信支付失败，请稍后重试。');
    				}
    			});
    		}
    		if(data.sendType=='90'){  //支付结果
    			if(data.code=='00'){
    				 window.location.href = CONETXT_PATH + '/jfPay/paySuccess.html?transId=W10&transAmt='+data.transAmt+"&wxTransLogKey="+data.wxTransLogKey;
    			}else{
    				 window.location.href = CONETXT_PATH + '/jfPay/payFailed.html?transId=W10&transAmt='+data.transAmt+'&transCode='+data.code+'&mchntCode='+data.mchntCode;
    			}
    		}
    };
    
    setInterval(function(){
	   	var sendData={};
	   	sendData["fromUser"]=$.trim($("#openid").val());
		sendData["toUser"]="";
		sendData["text"]="";
		sendData["reqType"]="C";
		sendData["transAmt"]="";
		sendData["oriTxnAmount"]="";
		sendData["sendType"]="00"; //保持心跳
		sendData["payType"]="";
		sendData["wxTransLogKey"]="";
		webSocket.send(JSON.stringify(sendData));
    },15000);//1000为1秒钟
    
	var pageManager = {
        init: function () {
        	
        	 $('#vipCardPay').on('click', pageManager.vipCardPayFun);
			 $('#wechatPay').on('click', pageManager.wechatPayFun);
        	
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
        	    	}
        	    }
        	});
            pageManager.genQrcode();
            
        },
        
        vipCardPayFun:function(){
        	$('#payType').val('VIPCARD_PAY');
        	pageManager.genQrcode();
        	$('#Popup').hide();
        	$('#payTypeHtml').html('会员卡余额支付');
        },
        wechatPayFun:function(){
        	$('#payType').val('WECHAT_PAY');
        	pageManager.genQrcode();
        	$('#Popup').hide();
        	$('#payTypeHtml').html('微信支付');
        },
        
        genQrcode: function() {
        	var payType=$('#payType').val();
        	var jfUserId=$('#jfUserID').val();
        	var hkbUserId=$('#hkbUserID').val();
        	var openId=$('#openid').val();
        	$.ajax({
        		url : CONETXT_PATH + '/jfPay/user/genCustomerQrcode.html',
        		type : 'post',
    	        dataType : "json",
    	        data:{
	                  'payType':payType,
	                  'jfUserID':jfUserId,
	                  'hkbUserID':hkbUserId,
	                  'openID':openId
    	        },
    	        success:function(data){
    	        	$("#QR_code").empty();
        	    	var qrcode = new QRCode('QR_code',{
        	    	    text:data.codeValue,             //输入二维码解析网址
        	    	    correctLevel : QRCode.CorrectLevel.H     //容错率30%
        	    	})
        	    }
        	});
        },
        
        push: function (config) {
            this._configs.push(config);
            return this;
        }
    };

    pageManager.init();
    
    $('#qr_refresh').bind("click", function(){
    	pageManager.genQrcode();
    });
    setInterval(pageManager.genQrcode, 60000);
        
    //$("#barcode").empty().barcode($("#openid").val(), "code128",{barWidth:1, barHeight:100, showHRI:true});
    /*$("#qrcode").qrcode({ 
        render: "table", //table方式 
        width: 200, //宽度 
        height:200, //高度 
        text: $("#openid").val() //任意内容 
    });*/
});