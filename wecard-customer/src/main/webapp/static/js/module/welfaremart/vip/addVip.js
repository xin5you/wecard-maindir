$(document).ready(function () {
	$('#bankNo').focus();
	AddVip.init();
});

var AddVip = {
	init:function(){
		AddVip.initEvent();
	},
	initEvent: function () {
		$('#sendPhoneCode').on('click', AddVip.sendPhoneSMS);
		$('#bankNo').on('keyup', AddVip.changeBankNo);
		$('#addBank').on("click", AddVip.addBankCommit);
		$('.add_bank_addr').on("click", AddVip.show);
	},

	show : function(){
		productAddress.show({fn:function(){
			shoppingCart.run({
				api: '../../../static/js/common/address.json',
				type: 'get',
				targetDom:document.getElementById('product_address_show'),
				fn:function() {
					productAddress.hide();
					console.log(chooseAdressId);//当前选择地址ID
				}
			})
		}
		})
	},

	sendPhoneSMS : function() {
		var phoneNumber = $('#mobile').val();
		var mobile = phoneNumber.substring(0,3)+"****"+phoneNumber.substring(7,11); 
		var that = $('#sendPhoneCode');
		var bizCode = '05';
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
				} else {
					var msg = "网络异常，请稍后再试";
					jfShowTips.toastShow({'text' : msg});
					return;
				}
			}
		});
	},
	changeBankNo:function(){
		var bankNo = $.trim($("#bankNo").val());
		if (!bankNo) {
			$('#accountBank').val('');
			$('#bankType').val('');
			$('#accountBankName').html('');
			$('#bankTypeName').html('');
			return;
		}
		var strBin = "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
		if(bankNo.length < 16 || bankNo.length > 19) {
			$('#accountBank').val('');
			$('#bankType').val('');
			$('#accountBankName').html('');
			$('#bankTypeName').html('');
			return;
		}
		//开头6位
		if(strBin.indexOf(bankNo.substring(0, 2)) == -1) {
			$('#accountBank').val('');
			$('#bankType').val('');
			$('#accountBankName').html('');
			$('#bankTypeName').html('');
			jfShowTips.toastShow({'text' : '银行卡号开头2位不符合规范'});
			return;
		}
		$.ajax({
			url : CONETXT_PATH + '/welfareMart/welfareBankNoValid.html',
			type : 'post',
			dataType : "json",
			data : {
				bankNo : bankNo
			},
			success : function(data) {
				if (data != null) {
					$('#accountBank').val(data.accountBank);
					$('#bankType').val(data.bankType);
					$('#accountBankName').html(data.bankName);
					$('#bankTypeName').html(data.bankTypeName);
					if (data.check == "1") {
						jfShowTips.toastShow({'text' : '该银行卡号已被绑定，请重新输入'});
						$("#bankNo").focus();
					} else if (data.check == "2") {
						jfShowTips.toastShow({'text' : '请输入正确的银行卡号'});
						$("#bankNo").focus();
					} else if (data.check == "3") {
						jfShowTips.toastShow({'text' : '暂不支持信用卡，请重新输入'});
						$("#bankNo").focus();
					}
				} else {
					jfShowTips.toastShow({'text' : '网络异常，请稍后再试'});
				}
			}
		});
	},
	addBankCommit:function(){
		$.ajax({
			url : CONETXT_PATH + '/welfareMart/isWithdrawBlacklistInf.html',
			type : 'post',
			dataType : "json",
			data : {},
			success : function(data) {
				if (data) {
					var productCode = $("#productCode").val();
					var num = $("#num").val();
					var check = $("#check").val();

					var bankNo = $.trim($("#bankNo").val());
					var addr = $("#product_address_show").html();
					$("#accountBankAddr").val(addr);
					var accountBankAddr = $("#accountBankAddr").val();
					var accountBranch = $("#accountBranch").val();
					var mobile = $("#mobile").val();
					var phoneCode = $.trim($("#phoneCode").val());
					var isdefault = '1';
					if ($('#isdefault').prop('checked')) {
						isdefault = '0';
					}

					if (!bankNo || bankNo == "") {
						jfShowTips.toastShow({'text' : '请输入银行卡号'});
						return;
					}
					if (!accountBank || accountBank == "") {
						jfShowTips.toastShow({'text' : '请输入正确的银行卡号'});
						return;
					}
					if (!accountBankAddr || accountBankAddr == "") {
						jfShowTips.toastShow({'text' : '请选择所在省市'});
						return;
					}
					var tag=',';
					if(accountBankAddr.indexOf(tag) == -1){
						jfShowTips.toastShow({'text' : '请选择所在省市'});
						return;
					}
					if (accountBankAddr == '请输入地址') {
						jfShowTips.toastShow({'text' : '请选择所在省市'});
						return;
					}
					if (!phoneCode || phoneCode == "") {
						jfShowTips.toastShow({'text' : '请输入验证码'});
						return;
					}
					$.ajax({
						url : CONETXT_PATH + '/welfareMart/welfareAddBankCommit.html',
						type : 'post',
						dataType : "json",
						data : {
							check : check,
							bankNo : bankNo,
							accountBankAddr : accountBankAddr,
							accountBranch : accountBranch,
							isdefault : isdefault,
							mobile : mobile,
							phoneCode : phoneCode
						},
						success : function(data) {
							if (data.status) {
								if (data.check == "1") {
									location.href = CONETXT_PATH + '/welfareMart/toBankCardList.html';
								} else if (data.check == "2") {
									location.href = CONETXT_PATH + '/welfareMart/toWelfareResellCard.html?productCode='+productCode+'&num='+num;
								} else {
									jfShowTips.toastShow({'text' : '网络异常，请稍后再试'});
								}
							} else {
								var msg = "";
								if (data.msg == null || data.msg == '') {
									msg = "网络异常，请稍后再试";
								} else {
									msg = data.msg;
								}
								jfShowTips.toastShow({'text' : msg});
							}
						}
					});
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

