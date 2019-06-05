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

			var bankName = $("#div").parent(".checkBank").find("div.bank_name").text();
			var bankNum = $("#div").parent(".checkBank").find("span.bankNum").text();
			var bankNo = $("#div").parent(".checkBank").find("input.bankNo").val();
			$('#bankNumber').val(bankNo);
			$("#checkBankNo").text(bankName+" "+bankNum);
		}
	},
	initEvent: function () {

		$('.checkBank').on("click", draw.checkBank);
		$('#addBank').on("click", draw.addBank);
		$('#resellCommit').on("click", draw.resellCommit);
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

		} else {
			$("#checkBankNo").text("选择银行卡");
		}
	},
	addBank:function(){
		var check = "2";
		location.href = CONETXT_PATH + '/welfareMart/toWelfareAddBank.html?num=&check='+check;
	},
	resellCommit:function(){
		$.ajax({
			url : CONETXT_PATH + '/welfareMart/isWithdrawBlacklistInf.html',
			type : 'post',
			dataType : "json",
			data : {},
			success : function(data) {
				if (data) {
					var drawAmount = $.trim($('#drawAmount').val());
					var bankNo = $.trim($('#bankNumber').val());
                    var accBal = $("#accBal").val();
                    if (!drawAmount){
                        jfShowTips.toastShow({'text' : '请输入提现金额'});
                        return false;
					}
                    if (!isNaN(drawAmount) || drawAmount <= 0){
                        jfShowTips.toastShow({'text' : '请输入正确的提现金额'});
                        return false;
					}
                    if (parseFloat(accBal) < parseFloat(drawAmount)){
                        jfShowTips.toastShow({'text' : '提现金额超过工资余额'});
                        return false;
                    }
					if (parseInt(drawAmount) > 50000) {
						jfShowTips.toastShow({'text' : '提现金额不能超过五万元'});
						return false;
					}
					if (!bankNo) {
						jfShowTips.toastShow({'text' : '请选择银行卡'});
						return false;
					}
					jfShowTips.dialogShow({
						"mainText": "您将提现工资："+drawAmount+"元",
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
                            window.location.href = CONETXT_PATH + '/welfareMart/welfareBalanceDrawCommit.html?transAmt='+drawAmount+'&bankNo='+bankNo;
						}
					});
				} else {
					jfShowTips.toastShow({'text' : '对不起，您暂时无法提现'});
					return false;
				}
			},
			error : function () {
				jfShowTips.toastShow({'text' : '对不起，您暂时无法提现'});
				return false;
			}
		});
	}

};

