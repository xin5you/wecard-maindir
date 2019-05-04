$(document).ready(function () {
    mchntCashConfirm.init();
})

var mchntCashConfirm = {

    init: function () {
    	$('#submitApplyPassBtn').on('click', mchntCashConfirm.mchntCashConfirmCommit);
    	$('#backBtn').on('click', mchntCashConfirm.goBack);
    },
    goBack:function(){
    	var chashId=$("#chashId").val();
    	location.href=Helper.getRootPath() + '/margin/mchntCashManage/listMerchantCashConfirm.do';
    },
    mchntCashConfirmCommit:function(){
    	var marginListId=$("#marginListId").val();
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/margin/mchntCashManage/mchntCashConfirmCommit.do',
            data: {
            		"marginListId":marginListId
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	$(".btn-submit").removeAttr('disabled');
                if(data.status) {
					location.href=Helper.getRootPath() + '/margin/mchntCashManage/listMerchantCashConfirm.do';
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};

