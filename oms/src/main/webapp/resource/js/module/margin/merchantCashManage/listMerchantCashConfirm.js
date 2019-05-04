$(document).ready(function() {
	listMerchantCashconfirm.init();
})

var listMerchantCashconfirm = {

	init : function() {
		listMerchantCashconfirm.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-approve').on('click', listMerchantCashconfirm.intoCashMcerchantMargin);
		$('.btn-view').on('click', listMerchantCashconfirm.intoViewMchntMargin);
		$('.btn-reset').on('click', listMerchantCashconfirm.searchReset);
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/margin/mchntCashManage/listMerchantCashConfirm.do';
	},
	intoCashMcerchantMargin:function(){
		var marginListId = $(this).attr('marginListId');
		var url = Helper.getRootPath()+"/margin/mchntCashManage/intoCashMcerchantMargin.do?marginListId="+marginListId;
		location.href=url;
	},
	intoViewMchntMargin:function(){
		var marginListId = $(this).attr('marginListId');
		var url = Helper.getRootPath()+"/margin/mchntCashManage/intoViewMchntMargin.do?marginListId="+marginListId;
		location.href=url;
	}
}
