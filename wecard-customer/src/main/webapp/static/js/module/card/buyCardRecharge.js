$(function () {
	var buyCardRecharge = {
			initEvent:function(){
				$('#confirmPayBtn').on('click', buyCardRecharge.buyCardRechargeCheck);
				buyCardRecharge.init();
			},
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
	        	    		    jsApiList: ['hideOptionMenu','chooseWXPay'] //使用接口时，这里必须先声明 
	        	    		});
	        	    		wx.ready(function(){
	                    		wx.hideOptionMenu();// 隐藏右上角菜单接口
	                    	});
	        	    	}
	        	    }
	        	});
	        },
	        wechatPayFun:function(){
	        	 var $that=$("#confirmPayBtn");
				 $that.attr("disabled", true);
	        	  var mchntCode=$.trim($('#mchntCode').val());
		  		  var commodityCode=$.trim($('#commodityCode').val());
//		  		  $.showLoading('提交中...'); 
			  		jfShowTips.loadingShow({
	            		'text' : '支付中',
	            		'thisNode':loadInnerHtml.node.loading,//loading动画
	            		'thisClass':'small'//loading动画为'small'
	            	});
		  		   $.ajax({
		  	    		url : CONETXT_PATH + '/customer/card/buyCardRechargeCheck.html',
		  	     		type : 'post',
		  	  	        dataType : 'json',
		  	      	    data:{
		  	                  mchntCode:mchntCode,
		  	                  commodityCode:commodityCode
		  	      	    },
		  	        	success:function(data1){
//		  	        		$.hideLoading();
		  	        		$that.removeAttr("disabled");
		  	        		if(data1.code=='00'){
		  	        			if(data1.accountFlag !='1'){
		  	        				 $.confirm("您还没注册会员，是否前往注册?", "系统提示", function() {
		  	        					 window.location.href = CONETXT_PATH + '/customer/user/userRegister.html';
		  	        			     }, function() {
		  	        			          //取消操作
		  	        			     });
		  	        				 return false;
		  	        			}else if(data1.cardFlag!='1'){
		  	        				
		  	        			}else{
		  	        			  //微信支付
		  	    	    			$.ajax({
		  	    	    				url : CONETXT_PATH + '/pay/buyCardWechatPay.html',
		  	    	    				type : 'post',
		  	    	    				dataType : 'json',
		  	    	    				data : {
		  	    	    					'wxTransLogKey' : data1.wxPrimaryKey,
		  	    	    					'commodityCode' : commodityCode,
		  	    	    				},
		  	    	    				success : function(result){
		  	    	    					jfShowTips.loadingRemove(); 
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
		  	    	    					        		url : CONETXT_PATH + '/pay/wxBuyCardOrderQuery.html',// 微信客服消息接口
		  	    	    					        		type : 'post',
		  	    	    					    	        dataType : "json",
		  	    	    					        	    data: {
		  	    	    					        	    	'wxPrimaryKey' :data1.wxPrimaryKey,
		  	    	    					        	    },
		  	    	    					        	    success:function(data2){
		  	    	    					        	    	if(data2 != null && data2 != '') {
		  	    	    					        	    		if (data2.code == "00") {// 交易成功
		  	    	    					        	    			location.href = CONETXT_PATH + '/pub/paySuccess.html?transId=W20';
		  	    	    					        	    		} else {
		  	    	    					        	    			location.href = CONETXT_PATH + '/pub/payFailed.html?transId=W20';
		  	    	    					        	    		}
		  	    	    					        	    	} else {
		  	    	    					        	    		WecardCommon.errorMsg();
		  	    	    					        	    	}
		  	    	    					        	    },
		  	    	    					        	    error: function(XMLHttpRequest, textStatus, errorThrown) {  
		  	    	    					        	    	WecardCommon.errorMsg();    
		  	    	    					        	    }
		  	    	    					        	});
		  	    	    						    },cancel:function(res){
		  	    		 						    	
		  	    		 			                }
		  	    	    						});
		  	    	    					} 
		  	    	    				},
		  	    	    				error : function() {
//		  	    	    					 WecardCommon.errorMsg();
		  	    	    				}
		  	    	    			});
		  	        			}
		  	        		}else{
//		  	        			 WecardCommon.errorMsg();
		  	        		}
		  	             },
		  	             error:function(){
//		  	            	$.hideLoading();
//		  	            	 $that.removeAttr("disabled");
		  	             }
		  	    	});
	        },
			buyCardRechargeCheck:function(){
//				 $.showLoading('支付校验中...'); 
				jfShowTips.loadingShow({
            		'text' : '支付校验中...',
            		'thisNode':loadInnerHtml.node.loading,//loading动画
            		'thisClass':'small'//loading动画为'small'
            	});
		  		 var payTypeCheck = $('input:checkbox[name="payType"]:checked').val();
		  		 if('wechatPay'==payTypeCheck){
		  			  	buyCardRecharge.wechatPayFun(); //调用微信支付
		  		 }
			}
	};
	buyCardRecharge.initEvent();
    
});