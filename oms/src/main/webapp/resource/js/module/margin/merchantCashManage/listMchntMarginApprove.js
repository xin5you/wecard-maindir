$(document).ready(function() {
	listMchntMarginApprove.init();
})

var listMchntMarginApprove = {

	init : function() {
		listMchntMarginApprove.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-approve').on('click', listMchntMarginApprove.intoApproveMcerchantMargin);
		$('.btn-view').on('click', listMchntMarginApprove.intoViewMchntMargin);
		$('.btn-reset').on('click', listMchntMarginApprove.searchReset);
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/margin/mchntCashManage/listMchntMarginApprove.do';
	},
	intoApproveMcerchantMargin:function(){
		var marginListId = $(this).attr('marginListId');
		var url = Helper.getRootPath()+"/margin/mchntCashManage/intoApproveMcerchantMargin.do?marginListId="+marginListId;
		location.href=url;
	},
	intoViewMchntMargin:function(){
		var marginListId = $(this).attr('marginListId');
		var url = Helper.getRootPath()+"/margin/mchntCashManage/intoViewMchntMargin.do?marginListId="+marginListId;
		location.href=url;
	}
}
