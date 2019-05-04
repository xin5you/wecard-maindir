$(function () {
	var pageManager = {
		isPayState:true,
        init: function () {
        	pageManager.initEvent();
        	pageManager.userCheckMoney();
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
        	 $('#confirm_next').on("click", pageManager.scanPayComfirmOrder);
        },
        userCheckMoney:function(){
        	var payAmount=$("#payAmount").val();
        	var userMchntAccBal= $('#userMchntAccBal').val();
        	var accHkbAccBal= $('#accHkbAccBal').val();
        	var mchntType = $('#mchntType').val();
        	if(payAmount==null || payAmount.trim()==''){
        		return false;
        	}else{
        		var paymoney= parseFloat(payAmount).toFixed(2);
        		if(parseInt(paymoney*100)<=parseInt(userMchntAccBal*100) ){
        			$('input:checkbox[name="payType"]').eq(1).removeAttr("checked");
        			$('input:checkbox[name="payType"]').eq(0).removeAttr("disabled");
        			$('input:checkbox[name="payType"]').eq(0).attr("checked",true);
        		} else if ('00'==mchntType && parseInt(paymoney*100)<=parseInt(accHkbAccBal*100)) {
        			$('input:checkbox[name="payType"]').eq(1).removeAttr("checked");
        			$('input:checkbox[name="payType"]').eq(0).removeAttr("disabled");
        			$('input:checkbox[name="payType"]').eq(0).attr("checked",true);
        		} else{
        			$('input:checkbox[name="payType"]').eq(0).removeAttr("checked");
        			$('input:checkbox[name="payType"]').eq(0).attr("disabled",true);
        			$('input:checkbox[name="payType"]').eq(1).attr("checked",true);
        			$('#card_pay').addClass("no_pay");
        		}
        	}
        },
        java2TxnBusiness: function(tableNum,settleDate,wxPrimaryKey,insCode,merchantCode,shopCode,transAmt,uploadAmt,pinTxn,cOpenid,userId) {
        	if(pageManager.isPayState){
        		pageManager.isPayState=false;
	        	$.ajax({
	        		url : CONETXT_PATH + '/pay/scanCodeJava2TxnBusiness.html',// 调交易核心
	        		type : 'post',
	    	        dataType : "json",
	        	    data: {
	        	    	tableNum : tableNum,
	        	    	settleDate : settleDate,
	        	    	wxPrimaryKey : wxPrimaryKey, 
	        	    	insCode : insCode, 
	        	    	merchantCode : merchantCode, 
	        	    	mchntName : $("#mchntName").val(), 
	        	    	shopCode : shopCode, 
	        	    	shopName : $("#shopName").val(), 
	        	    	transAmt : transAmt, 
	        	    	uploadAmt : uploadAmt,
	        	    	pinTxn : pinTxn,
	        	    	openid : cOpenid,
	        	    	userId : userId
	        	    },
	        	    success:function(data2){
	        	    	if(data2 != null && data2 != '') {
	        	    		if (data2.code == "00") {// 交易成功
	        	    			location.href= CONETXT_PATH + '/pub/paySuccess.html?transId=W10&transAmt='+data2.transAmt+"&mchntCode="+merchantCode+"&wxTransLogKey="+wxPrimaryKey;
	        	    		} else {
	        	    			location.href= CONETXT_PATH + '/pub/payFailed.html?&transId=W10&transAmt='+data2.transAmt+'&transCode='+data2.code+"&mchntCode="+merchantCode;
	        	    		}
	        	    	} else {
	        	    		WecardCommon.errorMsg();
	        	    	}
	        	    },
	        	    error: function(XMLHttpRequest, textStatus, errorThrown) {  
	        	    	WecardCommon.errorMsg();
	        	    	pageManager.isPayState=true;
	        	    }
	        	});
        	}
        },
        hkbkeybroadShow:function(resp,wxTransLog,openid){
        	new hkbpswKeybroad.keyBroadShow({
       	     'hideButton': 'pay_close',   //其他的关闭按钮
       	     'showNumEle': 'numBox',   //点击出现键盘的元素，并且存储数字，ID选择器
       	     'saveNumEle': 'num_box',  //用于显示当前金额的值的元素，用于判断光标，class选择器
       	     'clearButton': false,       //清除数字按钮
       	     'saveInput': 'pasd_input',   //存入键盘的值，input元素的class选择器
       	     'MaxNum': '6', //键盘可以输入的最大位数
       	        Fn:function(){   //输入密码之后所做的function
           			var pass = $("#pasd_input").val();
           			var encrypedPwd = RSAUtils.encryptedString(key, pass.split("").reverse().join(""));
           			pageManager.java2TxnBusiness(wxTransLog.tableNum,wxTransLog.settleDate,wxTransLog.wxPrimaryKey,wxTransLog.insCode,wxTransLog.mchntCode,wxTransLog.shopCode,resp.transAmt,wxTransLog.uploadAmt,encrypedPwd,openid,resp.userId);
       	        }
       	    }).run();
        },
        //扫码支付确认订单
		scanPayComfirmOrder:function(){
			if(pageManager.isPayState){
				jfShowTips.loadingShow({
            		'text' : '支付中',
            		'thisNode':loadInnerHtml.node.loading,//loading动画
            		'thisClass':'small'//loading动画为'small'
            	});
        		pageManager.isPayState=false;
    			var openid = $('#openid').val();
    			var payType=$('input:checkbox[name="payType"]:checked').val();
    			var money = $('#payAmount').val();
    			$.ajax({
    				url : CONETXT_PATH + '/pay/insertWxTransLog.html',// 插入微信端流水
    				type : 'post',
    		        dataType : "json",
    			    data: {
    			    	'sponsor' : '00',
    			    	'openid' : openid,
    			    	'merchantCode' : $.trim($("#merchantCode").val()),
    			    	'shopCode' : $.trim($("#shopCode").val()),
    			    	'insCode' : $.trim($("#insCode").val()),
    			    	'payType':payType,
    			    	'money' : money
    			    },
    			    success:function(wxTransLog){
    			    	pageManager.isPayState=true;
    		    		if (wxTransLog.wxPrimaryKey == null || wxTransLog.wxPrimaryKey == '') {
    		    			WecardCommon.errorMsg();
    		    		}
    		    		if('VIPCARD_PAY'==payType){ //会员卡支付
    		    	    	$.ajax({
    			        		url : CONETXT_PATH + '/pay/doCustomerNeed2EnterPassword.html',// 验密
    			        		type : 'post',
    			    	        dataType : "json",
    			        	    data: {
    			        	    	'tableNum' : wxTransLog.tableNum,
    			        	    	'cOpenid' : openid,
    			        	    	'merchantCode' : wxTransLog.mchntCode, 
    			        	    	'wxPrimaryKey' : wxTransLog.wxPrimaryKey,
    			        	    	'uploadAmt' : wxTransLog.uploadAmt
    			        	    },
    			        	    success:function(resp){
    			        	    	jfShowTips.loadingRemove();
    		        	    		if (resp.code == "1") {// 需要密码
    		        	    			passWordBox.passWordBoxShow();
    		        	    			pageManager.hkbkeybroadShow(resp,wxTransLog,openid);
    		        	    		} else if (resp.code == "0") {
    		        	    			pageManager.java2TxnBusiness(wxTransLog.tableNum,wxTransLog.settleDate,wxTransLog.wxPrimaryKey,wxTransLog.insCode,wxTransLog.mchntCode,wxTransLog.shopCode,resp.transAmt,wxTransLog.uploadAmt,'',openid,resp.userId);
    		        	    		}
    			        	    },
    			        	    error: function(XMLHttpRequest, textStatus, errorThrown) {
    				        	    	WecardCommon.errorMsg();
    			        	    }
    			        	});
    		    		} else if('WECHAT_PAY'==payType){  //微信支付
    		    			jfShowTips.loadingRemove();
    		    			$.ajax({
    		    				url : CONETXT_PATH + '/pay/CSBweChatQuickPay.html',
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
//    			    						    	$.showLoading('正在请求支付.....');
    		    						    	$.ajax({
    		    					        		url : CONETXT_PATH + '/pay/CSBweChantQuickPayOrderQuery.html',// 微信客服消息接口
    		    					        		type : 'post',
    		    					    	        dataType : "json",
    		    					        	    data: {
    		    					        	    	'wxPrimaryKey' : wxTransLog.wxPrimaryKey,
    		    					        	    },
    		    					        	    success:function(data2){
    		    					        	    	if(data2 != null && data2 != '') {
    		    					        	    		if (data2.code == "00") {// 交易成功
    		    					        	    			location.href = CONETXT_PATH + '/pub/paySuccess.html?transId=W71&transAmt='+data2.transAmt+"&wxTransLogKey="+wxTransLog.wxPrimaryKey;
    		    					        	    		} else {
    		    					        	    			location.href = CONETXT_PATH + '/pub/payFailed.html?transId=W71&transAmt='+data2.transAmt
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
    			    							 $("#confirm_next").removeAttr('disabled');
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
    			    error: function(XMLHttpRequest, textStatus, errorThrown){  
    		 	    	jfShowTips.loadingRemove();
    			    }
    			
    			});
			}	
		}	
    };
    pageManager.init();
});