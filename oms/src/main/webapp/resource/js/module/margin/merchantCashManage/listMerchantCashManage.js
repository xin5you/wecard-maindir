$(document).ready(function() {
	listMchntCashManage.init();
})

var listMchntCashManage = {

	init : function() {
		listMchntCashManage.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-edit').on('click', listMchntCashManage.intoEditMerchantCashManage);
		$('.btn-add').on('click', listMchntCashManage.intoAddMerchantCashManageUser);
		$('.btn-view').on('click', listMchntCashManage.intoViewMerchantCashManage);
		$('.btn-add-margin').on('click', listMchntCashManage.intoAddMerchantMargin);
		$('.btn-reset').on('click', listMchntCashManage.searchReset);
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/margin/mchntCashManage/listMerchantCashManage.do';
	},
	intoAddMerchantMargin:function(){
		var chashId=$(this).attr('chashId');
		var url = Helper.getRootPath()+"/margin/mchntCashManage/listMerchantMargin.do?chashId="+chashId;
		location.href=url;
	},
	intoAddMerchantCashManageUser:function(){
		var url = Helper.getRootPath()+"/margin/mchntCashManage/intoAddMerchantCashManage.do";
		location.href=url;
	},
//	intoEditMerchantCashManage:function(){
//		var chashId = $(this).attr('chashId');
//		var url = Helper.getRootPath()+"/margin/mchntCashManage/intoEditMerchantCashManage.do?chashId="+chashId;
//		location.href=url;
//	},
	intoViewMerchantCashManage:function(){
		var chashId = $(this).attr('chashId');
		var url = Helper.getRootPath()+"/margin/mchntCashManage/intoViewMerchantCashManage.do?chashId="+chashId;
		location.href=url;
	}
}
