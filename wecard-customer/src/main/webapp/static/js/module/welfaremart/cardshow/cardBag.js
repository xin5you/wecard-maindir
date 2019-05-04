$(document).ready(function () {
	CardBag.init();
});
var flag = true;
var CardBag = {
		init:function(){
			CardBag.initEvent();
		},
		initEvent: function () {
			$('.decideRecharge').on('click', CardBag.recharge);
			$('.toResell').on('click', CardBag.toResell);
		},
	    recharge:function () {
	    	var productCode = $(this).attr('data');
	    	var type = $(this).attr('name');
	    	var mobile = $(this).attr('value');
	    	$.ajax({
				url : CONETXT_PATH + '/welfareMart/isWithdrawBlacklistInf.html',
				type : 'post',
				dataType : "json",
				data : {},
				success : function(data) {
					if (!data) {
						jfShowTips.toastShow({'text' : '对不起，您暂时无法充值'});
						return false;
					}
			    	jfShowTips.dialogShow({
			    		"mainText": "您确定为"+mobile+"充值？",
			    		"minText": "  ",
			    		"hasCheck": false,
			    		"hasCancel": true,
			    		"checkFn": function () {
			    			$(".dialog_check").removeAttr("onclick")
			    			$('.dialog_check').attr("disabled",true); 
			    			loadingChange.showLoading();
			    			if(!flag){
			    				return;
			    			}
			    			flag = false;
			    			$.ajax({
			    				url : CONETXT_PATH + '/welfareMart/welfareCardRecharge.html',
			    				type : 'post',
			    				dataType : "json",
			    				data : {
			    					productCode : productCode
			    				},
			    				success : function(data) {
			    					if (data != null) {
			    						if (data.code == "00") {
				    						if (type == "11") {
				    							window.location.href = CONETXT_PATH + '/welfareMart/welfareCardRechargeCommit.html?productCode='+productCode;
				    						} else if (type == "12") {
				    							$('.dialog_check').attr("disabled",false); 
				    	    					loadingChange.hideLoading();
				    	    					$('main.loaded').removeClass('loaded');
				    							jfShowTips.toastShow({'text' : '该卡券暂不支持充值功能'});
				    							flag = true;
				    							jfShowTips.dialogRemove();
				    							return false;
				    						}
			    						} else {
			    							$('.dialog_check').attr("disabled",false); 
			    	    					loadingChange.hideLoading();
			    	    					$('main.loaded').removeClass('loaded');
			    							var msg = data.msg;
			    	    					if (msg == null || msg == "") {
			    	    						jfShowTips.toastShow({'text' : '网络异常，请稍后再试'});
			    	    					} else {
			    	    						jfShowTips.toastShow({'text' : data.msg});
			    	    					}
			    	    					flag = true;
			    	    					jfShowTips.dialogRemove();
			    	    					return false;
			    						}
			    					} else {
			    						$('.dialog_check').attr("disabled",false); 
				    					loadingChange.hideLoading();
				    					$('main.loaded').removeClass('loaded');
			    						jfShowTips.toastShow({'text' : '网络异常，请稍后再试'});
			    						flag = true;
			    						jfShowTips.dialogRemove();
			    						return false;
			    					}
			    				},
			    				error : function () {
			    					$('.dialog_check').attr("disabled",false); 
			    					loadingChange.hideLoading();
			    					$('main.loaded').removeClass('loaded');
			    					jfShowTips.toastShow({'text' : '网络异常，请稍后再试'});
			    					flag = true;
			    					jfShowTips.dialogRemove();
			    					return false;
			    				}
			    			});
			    		}
			    	});
				},
				error : function () {
					jfShowTips.toastShow({'text' : '对不起，您暂时无法充值'});
					return false;
				}
			});
	    },
	    toResell:function () {
	    	var productCode = $(this).attr('data');
	    	$.ajax({
				url : CONETXT_PATH + '/welfareMart/isWithdrawBlacklistInf.html',
				type : 'post',
				dataType : "json",
				data : {},
				success : function(data) {
					if (data) {
						window.location.href = CONETXT_PATH + "/welfareMart/toWelfareResellCard.html?productCode="+productCode;
					} else {
						jfShowTips.toastShow({'text' : '对不起，您暂时无法转让'});
						return false;
					}
				},
				error : function () {
					jfShowTips.toastShow({'text' : '对不起，您暂时无法转让'});
					return false;
				}
			});
	    }
}
