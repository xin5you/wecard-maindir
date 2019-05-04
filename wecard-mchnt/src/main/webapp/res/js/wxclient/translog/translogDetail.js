$(document).ready(function() {
	translogDetail.initEvent();
});

var router = new Router({
	container : '#container',
	enterTimeout : 50,
	leaveTimeout : 50
});

var refundPage = {
	url : '/refundPage',
	className : 'msg',
	render : function() {
		return $('#tpl_refundPage').html();
	}
};

var msg = {
	url : '/msg',
	className : 'msg',
	render : function() {
		return $('#tpl_msg').html();
	}
};
router.push(refundPage).push(msg);

var translogDetail = {
	initEvent : function() {
		$('#translogRefundBtn').on('click', translogDetail.translogRefund);
	},
	closeWindow : function() {
		wx.closeWindow();
	},
	translogRefund : function() {
		router.go('/refundPage');
		$('#sendPhoneCode').on('click', translogDetail.sendMchntPhoneSMS);
		$('#translogRefundCommitBtn').on('click', translogDetail.translogRefundCommit);
	},
	sendMchntPhoneSMS : function() {
		var that = $('#sendPhoneCode');
		WecardCommon.sendMsgAgain(that);
		WecardCommon.sendMchntPhoneSMS('03');
	},
	translogRefundCommit : function() {
		$that = $('.payback_commit');
		var txnPrimaryKey = $('#txnPrimaryKey').val();
		var phoneCode = $('#phoneCode').val();
		if ($.trim(phoneCode) == '' || phoneCode == null) {
			$('#weui_cells_tips').html('请输入动态口令');
			return false;
		} else {
			$('#weui_cells_tips').html('');
		}
		$that.hide();
		$.showLoading('退款中');
		$.ajax({
			type : 'POST',
			data : {
				'txnPrimaryKey' : txnPrimaryKey,
				'phoneCode' : phoneCode
			},
			url : CONETXT_PATH + '/wxclient/account/translogRefundCommit.html',
			dataType : 'json',
			success : function(data) {
				$that.show();
				$.hideLoading();
				if (data.status) {
					router.go('/msg');
					return;
				} else {
					$('#weui_cells_tips').html(data.msg)
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$that.show();
				$.hideLoading();
			}
		});
	}
};