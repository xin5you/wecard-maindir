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
        	if(payAmount==null || payAmount.trim()==''){
        		return false;
        	}
        },
        java2JFBusiness: function(tableNum,wxPrimaryKey,merchantCode,transAmt,jfUserId,userId,payType) {
        	if(pageManager.isPayState){
        		pageManager.isPayState=false;
	        	$.ajax({
	        		url : CONETXT_PATH + '/jfPay/scanCodeJava2JFBusiness.html',// 调交易核心
	        		type : 'post',
	    	        dataType : "json",
	        	    data: {
	        	    	'tableNum' : tableNum,
	        	    	'wxPrimaryKey' : wxPrimaryKey, 
	        	    	'transAmt' : transAmt, 
	        	    	'HKBUserId' : userId,
	        	    	'JFUserId' : jfUserId,
	        	    	'payType' : payType
	        	    },
	        	    success:function(data2){
	        	    	if(data2 != null && data2 != '') {
	        	    		if (data2.code == "00") {// 交易成功
	        	    			location.href= CONETXT_PATH + '/jfPay/paySuccess.html?transId=W71&transAmt='+data2.transAmt+"&mchntCode="+merchantCode+"&wxTransLogKey="+wxPrimaryKey;
	        	    		} else {
	        	    			location.href= CONETXT_PATH + '/jfPay/payFailed.html?&transId=W71&transAmt='+data2.transAmt+'&transCode='+data2.code+"&mchntCode="+merchantCode;
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
        //扫码支付确认订单
		scanPayComfirmOrder:function(){
			if(pageManager.isPayState){
				jfShowTips.loadingShow({
            		'text' : '支付中',
            		'thisNode':loadInnerHtml.node.loading,//loading动画
            		'thisClass':'small'//loading动画为'small'
            	});
        		pageManager.isPayState=false;
    			var HKBUserId = $('#HKBUserId').val();
    			var payType=$('input:checkbox[name="payType"]:checked').val();
    			var money = $('#payAmount').val();
    			var JFUserId = $('#JFUserId').val();
    			var mchntCode = $.trim($("#merchantCode").val());
    			$.ajax({
    				url : CONETXT_PATH + '/jfPay/insertWxTransLog.html',// 插入微信端流水
    				type : 'post',
    		        dataType : "json",
    			    data: {
    			    	'sponsor' : '00',
    			    	'HKBUserId' : HKBUserId,
    			    	'merchantCode' : mchntCode,
    			    	'shopCode' : $.trim($("#shopCode").val()),
    			    	'insCode' : $.trim($("#insCode").val()),
    			    	'money' : money,
    			    	'JFUserId' : JFUserId
    			    },
    			    success:function(wxTransLog){
    			    	pageManager.isPayState=true;
    		    		if (wxTransLog.wxPrimaryKey == null || wxTransLog.wxPrimaryKey == '') {
    		    			WecardCommon.errorMsg();
    		    		}
    		    		if('JFBENEFIT_PAY'==payType){ //嘉福 福利余额 支付
    		    			pageManager.java2JFBusiness(wxTransLog.tableNum,wxTransLog.wxPrimaryKey,mchntCode,wxTransLog.transAmt,JFUserId,HKBUserId,payType);
    		    		} else if('JFSALARY_PAY' == payType){ //嘉福 工资余额 支付
    		    			pageManager.java2JFBusiness(wxTransLog.tableNum,wxTransLog.wxPrimaryKey,mchntCode,wxTransLog.transAmt,JFUserId,HKBUserId,payType);
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