$(document).ready(function () {
	CardShow.init();
});

var CardShow = {
		init:function(){
			CardShow.initEvent();
		},
	    initEvent: function () {
	    	$('#sellNum').on('keyup', CardShow.checkAmount);
	    	$('#reduceBut').on('click', CardShow.checkAmount);
	    	$('#addBut').on('click', CardShow.checkAmount);
	    	$('#unifiedOrder').on('click', CardShow.unifiedOrder);
	    },
	    checkAmount:function(){
	    	var fee = "0.04";
	    	var sellNum = $('#sellNum').val();
	    	var amount = $('#amount').val();
	    	var loseAmount = (parseFloat(amount) * parseFloat(fee) * parseInt(sellNum)).toFixed(2);
	    	var buyAmount = (parseFloat(amount) * parseInt(sellNum)).toFixed(2);
	    	if (!sellNum) {
	    		loseAmount = 0.00;
	    		buyAmount = 0.00;
	    	} else {
	    		if (parseInt(sellNum) > 100) {
	    			$('#sellNum').val("100");
	    			loseAmount = (parseFloat(amount) * parseFloat(fee) * 100).toFixed(2);;
	    			buyAmount = (parseFloat(amount) * 100).toFixed(2);
	    			jfShowTips.toastShow({'text' : '卡券兑换张数不能超过 100 张'});
	    		} else if (parseInt(buyAmount) > 50000) {
	    			jfShowTips.toastShow({'text' : '卡券兑换总金额不能超过 五万 元'});
	    		}
	    	}
	    	$("#loseAmount").val(loseAmount);
	    	$("#sellAmount").text(buyAmount);
	    	return;
	    },
	    unifiedOrder:function () {
	    	if($('#checkAgreement').is(':checked')) {
	    		var sellNum = $('#sellNum').val();
		    	var amount = $('#amount').val();
		    	var productCode = $('#productCode').val();
				if (!sellNum) {
					jfShowTips.toastShow({'text' : '请输入卡券兑换张数'});
					$('#sellNum').focus();
					return;
				} else {
					if (parseInt(sellNum) > 100) {
						jfShowTips.toastShow({'text' : '卡券兑换张数不能超过 100 张'});
						$('#sellNum').focus();
						return;
					} else {
						var money = parseInt(sellNum) * parseInt(amount);
						if (parseInt(money) > 50000) {
							jfShowTips.toastShow({'text' : '卡券兑换总金额不能超过 五万 元'});
							$('#sellNum').focus();
							return;
						} else {
							$.ajax({
			    				url : CONETXT_PATH + '/welfareMart/isWithdrawBlacklistInf.html',
			    				type : 'post',
			    				dataType : "json",
			    				data : {},
			    				success : function(data) {
			    					if (data) {
			    						window.location.href = CONETXT_PATH + '/welfareMart/welfareBuyCardCommit.html?num='+sellNum+'&productCode='+productCode;
//			    						$("#unifiedOrder").attr("href", CONETXT_PATH+"/welfareMart/welfareBuyCardCommit.html?num="+sellNum+"&productCode="+productCode);
			    					} else {
			    						jfShowTips.toastShow({'text' : '对不起，您暂时无法购买'});
			    						return;
			    					}
			    				},
			    				error : function () {
			    					jfShowTips.toastShow({'text' : '对不起，您暂时无法购买'});
			    					return;
			    				}
			    			});
						}
					}
				}
			} else {
				jfShowTips.toastShow({'text' : '请选择购买协议'});
				return;
			}
	    }
}

