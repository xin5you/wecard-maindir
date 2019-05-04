$(document).ready(function () {
    approveMerchantMargin.init();
})

var approveMerchantMargin = {
	applyStat:30,
    init: function () {
    	$('#submitApplyPassBtn').on('click', approveMerchantMargin.addMerchntMarginCommit40);
    	$('#submitApplyNoPassBtn').on('click', approveMerchantMargin.addMerchntMarginCommit30);
    	$('#backBtn').on('click', approveMerchantMargin.goBack);
    },
    goBack:function(){
    	location.href=Helper.getRootPath() + '/margin/mchntCashManage/listMchntMarginApprove.do';
    },
    addMerchntMarginCommit30:function(){
    	approveMerchantMargin.applyStat=30;
    	approveMerchantMargin.approveMerchantMarginCommit();
    },
    addMerchntMarginCommit40:function(){
    	approveMerchantMargin.applyStat=40;
    	approveMerchantMargin.approveMerchantMarginCommit();
    },
    approveMerchantMarginCommit:function(){
    	var marginListId=$("#marginListId").val();
    	var chashId=$("#chashId").val();
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/margin/mchntCashManage/approveMerchantMarginCommit.do',
            data: {
            		"marginListId":marginListId,
            		"approveStat":approveMerchantMargin.applyStat
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	$(".btn-submit").removeAttr('disabled');
                if(data.status) {
					location.href=Helper.getRootPath() + '/margin/mchntCashManage/listMchntMarginApprove.do';
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};

