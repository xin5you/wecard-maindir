$(document).ready(function () {
    draw.init();
});
var flag = true;
var draw = {
	init:function(){
		$('#resellNum').focus();
        draw.loadSendCode();
        draw.initEvent();
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
		$('#sendPhoneCode').on('click', draw.sendPhoneSMS);
		$('.checkBank').on("click", draw.checkBank);
		$('#addBank').on("click", draw.addBank);
		$('#resellCommit').on("click", draw.resellCommit);
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
		var drawAmount = $.trim($("#drawAmount").val());

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
			if (drawAmount != null && drawAmount != '' && parseInt(drawAmount) > 0 ) {
				$('#sendPhoneCode').removeAttr("disabled");
				document.getElementsByClassName('phone_code')[0].className = 'phone_code';
			} else {
				/*jfShowTips.toastShow({'text' : '请输入有效的转让张数'});*/
				$('#drawAmount').focus();
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
					var drawAmount = $.trim($('#drawAmount').val());
					var bankNo = $.trim($('#bankNumber').val());


					if (parseInt(drawAmount) > 50000) {
						jfShowTips.toastShow({'text' : '支取总额度不能超过 五万 元'});
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
						"mainText": "您将转支取工资余额："+drawAmount+"元",
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
                            window.location.href = CONETXT_PATH + '/welfareMart/welfareBalanceDrawCommit.html?phoneCode='+phoneCode+'&transAmt='+drawAmount+'&bankNo='+bankNo;
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

