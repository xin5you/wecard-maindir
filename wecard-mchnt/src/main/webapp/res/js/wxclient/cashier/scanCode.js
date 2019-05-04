$(function () {
	var webSocket = new ReconnectingWebSocket("ws://"+wsUrl+"websocket_cashier.html?bizId=scanCode&openId="+$.trim($("#openid").val()));

    webSocket.onmessage = function(event) {
    	var data=JSON.parse(event.data);
    	
    	$.hideLoading();
    	if(data.sendType=='20'){//请求用户输入密码
    		$.showLoading('用户输入密码中...'); 
    		return false;
    	}else if(data.sendType=='90'){
    		
	    	if (data.code == "00") {// 交易成功
	            location.href = CONETXT_PATH + '/pub/paySuccess.html?transAmt='+data.transAmt;
			} else {
				location.href = CONETXT_PATH + '/pub/payFailed.html?transAmt='+data.transAmt
								+'&errorInfo='+encodeURIComponent(encodeURIComponent(data.text));
			}
	    	return false;
    	}else if(data.sendType=='99'){
    		
    	}
    };
	
    
    setInterval(function(){
	   	var sendData={};
	   	sendData["fromUser"]=$.trim($("#openid").val());
		sendData["toUser"]="";
		sendData["text"]="";
		sendData["reqType"]="B";
		sendData["transAmt"]="";
		sendData["oriTxnAmount"]="";
		sendData["sendType"]="00"; //保持心跳
		sendData["payType"]="";
		sendData["wxTransLogKey"]="";
		webSocket.send(JSON.stringify(sendData));
    },15000);//1000为1秒钟
    
	$('#scanCodeBtn').bind("click", function(){
		pageManager.scanCode();
    });
	
	var pageManager = {
        init: function () {
         
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
        	
//            function onMessage(event) {
//            	var customerOpenid = $.trim($("#cOpenid").val());
//            	var msg = event.data;
//            	if (msg != '' && msg != null) {
//            		if (msg == 'start_trans') {
//            			$.ajax({
//    		        		url : CONETXT_PATH + '/pay/insertWxTransLog.html',// 插入微信端流水
//    		        		type : 'post',
//    		    	        dataType : "json",
//    		        	    data: {
//    		        	    	sponsor : '10',
//    		        	    	openid : customerOpenid, 
//    		        	    	merchantCode : $.trim($("#merchantCode").val()), 
//    		        	    	shopCode : $.trim($("#shopCode").val()),
//    		        	    	insCode : $.trim($("#insCode").val()),
//    		        	    	money : $.trim($("#money_page").val())
//    		        	    },
//    		        	    success:function(wxTransLog){
//		        	    		if (wxTransLog.wxPrimaryKey == null || wxTransLog.wxPrimaryKey == '') {
//		        	    			WecardCommon.error();
//		        	    		}
//		        	    		$("#wxPrimaryKey").val(wxTransLog.wxPrimaryKey);
//    		        	    	$.ajax({
//    	    		        		url : CONETXT_PATH + '/pay/doCustomerNeed2EnterPassword.html',// 验密
//    	    		        		type : 'post',
//    	    		    	        dataType : "json",
//    	    		        	    data: {
//    	    		        	    	tableNum : wxTransLog.tableNum,
//    	    		        	    	cOpenid : customerOpenid,
//    	    		        	    	merchantCode : wxTransLog.mchntCode, 
//    	    		        	    	wxPrimaryKey : wxTransLog.wxPrimaryKey,
//    	    		        	    	uploadAmt : wxTransLog.uploadAmt
//    	    		        	    },
//    	    		        	    success:function(resp){
//    	    		        	    	$("#hsm_param").val(resp.userId);
//	    		        	    		if (resp.code == "1") {// 需要密码
//	    		        	    			$.showLoading("用户输入密码中...");
//	            	                        webSocket.send("need_password" + '@' + customerOpenid + '@' + resp.transAmt);// 如果需要密码，将用户openid发送至后台
//	    		        	    		} else if (resp.code == "0") {
//	    		        	    			java2TxnBusiness(customerOpenid, wxTransLog.tableNum, wxTransLog.settleDate, resp.transAmt, wxTransLog.uploadAmt, '');
//	    		        	    		} else {
//    	    		        	    		WecardCommon.error();
//    	    		        	    	}
//    	    		        	    },
//    	    		        	    error: function(XMLHttpRequest, textStatus, errorThrown) {  
//    	    		        	    	WecardCommon.error();    
//    	    		        	    }
//    	    		        	});
//    		        	    },
//    		        	    error: function(XMLHttpRequest, textStatus, errorThrown) {  
//    		        	    	WecardCommon.error();    
//    		        	    }
//    		        	});
//            		} else {
//            			var array = msg.split("@");
//            			if (array.length > 1) {
//            				$.ajax({
//        		        		url : CONETXT_PATH + '/pay/getWxTransLogById.html',// 根据流水主键得到流水
//        		        		type : 'post',
//        		    	        dataType : "json",
//        		        	    data: {
//        		        	    	wxPrimaryKey : $("#wxPrimaryKey").val()
//        		        	    },
//        		        	    success:function(log){
//        		        	    	if(log != null && log != '') {
//        		        	    		var userId = $("#hsm_param").val();
//        		        	    		java2TxnBusiness(customerOpenid, log.tableNum, log.settleDate, array[1], log.uploadAmt, array[0]);
//        		        	    	} else {
//        		        	    		WecardCommon.error();
//        		        	    	}
//        		        	    },
//        		        	    error: function(XMLHttpRequest, textStatus, errorThrown) {  
//        		        	    	WecardCommon.error();    
//        		        	    }
//        		        	});
//            			}
//            		}
//			    }
//            }
//            
//            function java2TxnBusiness(customerOpenid,tableNum,settleDate,transAmt,uploadAmt,pinTxn) {
//            	$.ajax({
//	        		url : CONETXT_PATH + '/pay/scanCodeJava2TxnBusiness.html',// 调交易核心
//	        		type : 'post',
//	    	        dataType : "json",
//	        	    data: {
//	        	    	tableNum : tableNum,
//	        	    	settleDate : settleDate,
//	        	    	wxPrimaryKey : $.trim($("#wxPrimaryKey").val()), 
//	        	    	insCode : $.trim($("#insCode").val()), 
//	        	    	merchantCode : $.trim($("#merchantCode").val()), 
//	        	    	mchntName : $.trim($("#mchntName").val()), 
//	        	    	shopCode : $.trim($("#shopCode").val()), 
//	        	    	shopName : $.trim($("#shopName").val()), 
//	        	    	transAmt : transAmt, 
//	        	    	uploadAmt : uploadAmt,
//	        	    	pinTxn : pinTxn,
//	        	    	openid : customerOpenid,
//	        	    	userId : $.trim($("#hsm_param").val())
//	        	    },
//	        	    success:function(data2){
//	        	    	$.hideLoading();
//	        	    	if(data2 != null && data2 != '') {
//	        	    		$('#container').html('');
//	        	    		if (data2.code == "00") {// 交易成功
//    	                        webSocket.send('success@' + customerOpenid);
//    	                        location.href = CONETXT_PATH + '/pub/paySuccess.html?transAmt='+data2.transAmt;
//	        	    		} else {
//	        	    			webSocket.send('failed@' + customerOpenid);
//	        	    			location.href = CONETXT_PATH + '/pub/payFailed.html?transAmt='+data2.transAmt
//	        	    							+'&errorInfo='+encodeURIComponent(encodeURIComponent(data2.info));
//	        	    		}
//	        	    	} else {
//	        	    		WecardCommon.error();
//	        	    	}
//	        	    },
//	        	    error: function(XMLHttpRequest, textStatus, errorThrown) {  
//	        	    	WecardCommon.error();    
//	        	    }
//	        	});
//            }
        },
	
		scanCode:function(){
         	var money = $.trim($("#money_page").val());
         	if(money == '') {
         		$("#alert_info").text("请输入金额");
         		return false;
         	}
         	$("#alert_info").text("");
         	wx.scanQRCode({// 调起微信扫一扫接口
			    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
			    success: function (res) {
				    var result = res.resultStr;
				    var data={};
					data["fromUser"]=$.trim($("#openid").val());
					data["toUser"]="";
					data["text"]=result;
					data["reqType"]="B";
					data["transAmt"]=$.trim($("#money_page").val());
					data["sendType"]="10";
					data["wxTransLogKey"]="";
					data["payType"]="";
					webSocket.send(JSON.stringify(data));
					 
//					$("#cOpenid").val(result);
//				    webSocket.send(result);// 扫描后给websocket后台推送已扫描消息
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {  
					 alert("扫描二维码异常，请重新扫描");
	        	 }
			});
		}
    };

    pageManager.init();
});