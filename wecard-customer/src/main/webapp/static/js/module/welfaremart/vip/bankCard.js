$(document).ready(function () {
	AddVip.init();
});

var AddVip = {
		init:function(){
			AddVip.initEvent();
		},
		initEvent: function () {
			$('.add_bank_card').on("click", AddVip.toAddBankCard);
			$('.delete_all').on("click", AddVip.deleteBankCard);
		},
		
		deleteBankCard:function(){
			jfShowTips.dialogShow({
				"mainText": "您将转让 "+resellNum+" 张"+parseInt(amount)+"元"+productName,
				"minText": "  ",
				"hasCheck": false,
				"hasCancel": true,
				"checkFn": function () {
					$(".dialog_check").removeAttr("onclick")
	    			$('.dialog_check').attr("disabled",true); 
					$('#resellCommit').attr("disabled",true); 
					if(!flag){
	    				return;
	    			}
	    			flag = false;
	    			loadingChange.showLoading();
					$.ajax({
						url : CONETXT_PATH + '/welfareMart/welfareResellCardCommit.html',
						type : 'post',
						dataType : "json",
						data : {
							phoneCode : phoneCode,
							productCode : productCode,
							resellNum : resellNum,
							bankNo : bankNo
						},
						success : function(data) {
							loadingChange.hideLoading();
							$('main.loaded').removeClass('loaded');
							$('#resellCommit').attr("disabled",false); 
							if (data == null) {
								jfShowTips.toastShow({'text' : '网络异常，请稍后再试'});
								flag = true;
								jfShowTips.dialogRemove();
							} else {
								if (data.status) {
									location.href = CONETXT_PATH + '/welfareMart/toWelfareResellSuccess.html?orderId='+data.orderId;
								} else {
									var msg = "";
									if (data.msg == null || data.msg == '') {
										msg = "网络异常，请稍后再试";
									} else {
										msg = data.msg;
									}
									jfShowTips.toastShow({'text' : msg});
									flag = true;
									jfShowTips.dialogRemove();
									return false;
								}
							}
						},
						error : function() {
							jfShowTips.toastShow({'text' : '网络异常，请稍后再试'});
							flag = true;
							jfShowTips.dialogRemove();
							return false;
						}
					});
				}
			});
		},
		
		toAddBankCard:function(){
			$.ajax({
				url : CONETXT_PATH + '/welfareMart/isWithdrawBlacklistInf.html',
				type : 'post',
				dataType : "json",
				data : {},
				success : function(data) {
					if (data) {
						window.location.href = CONETXT_PATH + "/welfareMart/toWelfareAddBank.html?check=1";
					} else {
						jfShowTips.toastShow({'text' : '对不起，您暂时无法添加银行卡'});
						return false;
					}
				},
				error : function () {
					jfShowTips.toastShow({'text' : '对不起，您暂时无法添加银行卡'});
					return false;
				}
			});
		}
		
};

