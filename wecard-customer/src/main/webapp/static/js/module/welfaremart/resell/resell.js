$(document).ready(function () {
	Resell.init();
});
var flag = true;
var Resell = {
	init:function(){
		$('#resellNum').focus();
		Resell.loadSendCode();
		Resell.initEvent();
	},
	loadSendCode : function () {//页面一加载就判断获取验证码按钮是否可用
		var resellNum = $.trim($("#resellNum").val());
		if($("#div").hasClass('weui_choose')){
			if (resellNum != null && resellNum != '' && parseInt(resellNum) > 0 ) {
				$('#sendPhoneCode').removeAttr("disabled");
				document.getElementsByClassName('phone_code')[0].className = 'phone_code';
			} else {
				$('#sendPhoneCode').attr("disabled",true); 
				$(".phone_code").addClass("grey_button");
				/*jfShowTips.toastShow({'text' : '请输入有效的转让张数'});*/
				$('#resellNum').focus();
			}
			var bankName = $("#div").parent(".checkBank").find("div.bank_name").text();
			var bankNum = $("#div").parent(".checkBank").find("span.bankNum").text();
			var bankNo = $("#div").parent(".checkBank").find("input.bankNo").val();
			$('#bankNumber').val(bankNo);
			$("#checkBankNo").text(bankName+" "+bankNum);
		} else {
			$('#sendPhoneCode').attr("disabled",true); 
			$(".phone_code").addClass("grey_button");
		}
	},
	initEvent: function () {
		$('#resellNum').on('keyup', Resell.changeNum);
		$('#sendPhoneCode').on('click', Resell.sendPhoneSMS);
		$('.checkBank').on("click", Resell.checkBank);
		$('#addBank').on("click", Resell.addBank);
		$('#resellCommit').on("click", Resell.resellCommit);
	},

	changeNum:function(){
		var resellNum = $.trim($("#resellNum").val());
		var num = $("#num").text();
		var amount = $('#amount').val();
		var fee = "1";
		if($("#div").hasClass('weui_choose')){
			if (resellNum != null && resellNum != '' && parseInt(resellNum) > 0) {
				$('#sendPhoneCode').removeAttr("disabled");
				document.getElementsByClassName('phone_code')[0].className = 'phone_code';
			} else {
				$('#sendPhoneCode').attr("disabled",true); 
				$(".phone_code").addClass("grey_button");
				/*jfShowTips.toastShow({'text' : '请输入有效的转让张数'});*/
				$('#resellNum').focus();
			}
		} else {
			$('#sendPhoneCode').attr("disabled",true); 
			$(".phone_code").addClass("grey_button");
		}
		if (!resellNum) {
			$("#gainAmount").html("0.00");
			$("#unusedAmount").html("0.00");
			/*jfShowTips.toastShow({'text' : '请输入有效的转让张数'});*/
			$('#resellNum').focus();
			return;
		} else {
			amount = parseFloat(amount) * 100;
			var totalFee = parseInt(parseInt(amount) * parseInt(fee) * parseInt(resellNum) / 100);
			var loseAmount = (parseInt(amount) * parseInt(resellNum)) - parseInt(totalFee);
			var unusedAmount = parseInt(amount) * parseInt(resellNum);
			if (parseInt(resellNum) > parseInt(num)) {
				jfShowTips.toastShow({'text' : '卡券转让张数不能超过'+num+'张'});
				$("#resellNum").val(num);
				totalFee = parseInt(parseInt(amount) * parseInt(fee) * parseInt(num) / 100);
				loseAmount = (parseInt(amount) * parseInt(num)) - parseInt(totalFee);
				unusedAmount = parseInt(amount) * parseInt(num);
			} else if (parseInt(resellNum) > 100) {
				jfShowTips.toastShow({'text' : '卡券转让张数不能超过 100 张'});
				$("#resellNum").val("100");
				totalFee = parseInt(parseInt(amount) * parseInt(fee) * 100 / 100);
				loseAmount = (parseInt(amount) * 100) - parseInt(totalFee);
				unusedAmount = parseInt(amount) * 100;
			} else if (parseInt(unusedAmount / 100) > 50000) {
				jfShowTips.toastShow({'text' : '卡券转让总额度不能超过 五万 元'});
			}
			$('#gainAmount').text(parseFloat(loseAmount / 100));
			$('#unusedAmount').text(parseFloat(unusedAmount / 100));
			return;
		}
	},
	sendPhoneSMS : function() {
		var phoneNumber = $('#mobile').val();
		var mobile = phoneNumber.substring(0,3)+"****"+phoneNumber.substring(7,11); 
		var that = $('#sendPhoneCode');
		var bizCode = '04';
		$.ajax({
			url : CONETXT_PATH + '/welfareMart/welfareSendPhoneCode.html',
			type : 'post',
			dataType : "json",
			data : {
				bizCode : bizCode,
				phoneNumber : phoneNumber
			},
			success : function(data) {
				if (data == null || data == ''){
					var msg = "网络异常，请稍后再试";
					jfShowTips.toastShow({'text' : msg});
					return;
				}
				if (data) {
					var msg = "短信验证码已成功发至 " + mobile;
					jfShowTips.toastShow({'text' : msg});
					return;
				}
				if (!data) {
					var msg = "网络异常，请稍后再试";
					jfShowTips.toastShow({'text' : msg});
					return;
				}
			}
		});
	},
	checkBank:function(){
		var bankNo = $.trim($(this).attr("data"));
		$('#bankNumber').val(bankNo);
		var resellNum = $.trim($("#resellNum").val());

		if ($('.weui_choose').length <= 0) {
			$(this).append('<div id="div" class="weui_choose">已选</div>');
		} else {
			$('.weui_choose').remove();
			$(this).append('<div id="div" class="weui_choose">已选</div>');
		}
		if($("#div").hasClass('weui_choose')){
			var bankName = $($(this).find('div.bank_name')).html();
			var bankNum = $($(this).find('span.card_number')).text();
			$("#checkBankNo").text(bankName+" "+bankNum);
			if (resellNum != null && resellNum != '' && parseInt(resellNum) > 0 ) {
				$('#sendPhoneCode').removeAttr("disabled");
				document.getElementsByClassName('phone_code')[0].className = 'phone_code';
			} else {
				/*jfShowTips.toastShow({'text' : '请输入有效的转让张数'});*/
				$('#resellNum').focus();
			}
		} else {
			$("#checkBankNo").text("选择银行卡");
			$("#sendPhoneCode").removeClass("right_code_orange");
			$('#sendPhoneCode').attr("disabled",true); 
			$(".phone_code").addClass("grey_button");
		}
	},
	addBank:function(){
		var productCode = $('#productCode').val();
		var num = $('#num').html();
		var check = "2";
		location.href = CONETXT_PATH + '/welfareMart/toWelfareAddBank.html?productCode='+productCode+'&num='+num+'&check='+check;
	},
	resellCommit:function(){
		$.ajax({
			url : CONETXT_PATH + '/welfareMart/isWithdrawBlacklistInf.html',
			type : 'post',
			dataType : "json",
			data : {},
			success : function(data) {
				if (data) {
					var phoneCode = $.trim($('#phoneCode').val());
					var productCode = $('#productCode').val();
					var gainAmount = $('#gainAmount').html();
					var resellNum = $.trim($('#resellNum').val());
					var bankNo = $.trim($('#bankNumber').val());
					var appearNum = $('#appearNum').val();
					var amount = $('#amount').val();
					var productName = $('#productName').text();
					if (!appearNum || parseInt(appearNum) == 0) {
						jfShowTips.toastShow({'text' : '您当月转让次数已用完'});
						return false;
					}
					var num = $("#num").text();
					var unusedAmount = parseFloat(amount) * parseInt(resellNum);
					if (!resellNum) {
						jfShowTips.toastShow({'text' : '请输入有效的转让张数'});
						return false;
					}
					if (parseInt(resellNum) > parseInt(num)) {
						jfShowTips.toastShow({'text' : '卡券转让张数不能超过'+num+'张'});
						$('#resellNum').val(num);
						return false;
					}
					if (parseInt(resellNum) > 100) {
						jfShowTips.toastShow({'text' : '卡券转让张数不能超过 100 张'});
						$('#resellNum').val(100);
						return false;
					}
					if (parseInt(unusedAmount) > 50000) {
						jfShowTips.toastShow({'text' : '卡券转让总额度不能超过 五万 元'});
						return false;
					}
					if (!bankNo) {
						jfShowTips.toastShow({'text' : '请选择银行卡'});
						return false;
					}
					if (!phoneCode) {
						jfShowTips.toastShow({'text' : '请输入验证码'});
						return false;
					}
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

};

