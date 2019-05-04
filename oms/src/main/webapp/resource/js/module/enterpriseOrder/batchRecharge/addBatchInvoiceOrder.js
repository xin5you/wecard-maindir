$(document).ready(function() {
	addBatchInvoiceOrder.init();
})

var addBatchInvoiceOrder = {
	init : function() {
		addBatchInvoiceOrder.initEvent();
	},
	initEvent:function(){
		$('#addSubmitBtn').on('click', addBatchInvoiceOrder.addBatchInvoiceOrderCommit);
		$('.btn-reset').on('click', addBatchInvoiceOrder.resetBatchInvoiceOrder);
	},
	resetBatchInvoiceOrder:function(){
		var url = Helper.getRootPath()+"/enterpriseOrder/batchRecharge/listRecharge.do";
		location.href=url;
	},
	addBatchInvoiceOrderCommit:function(){
		var shopCode = $("#shopCode").val();
		if(shopCode == ''){
			Helper.alert("开票门店不能为空");
    		return false;
		}
		var invoiceAmt = $("#invoiceAmt").val();
		if(invoiceAmt <= 0){
			Helper.alert("当前操作可开票金额是0.00，暂不需开票");
			return false;
		}
		$.ajax({
			type:'post', // 提交方式 get/post
            url: Helper.getRootPath() + '/enterpriseOrder/batchRecharge/addBatchInvoiceOrderCommit.do', // 需要提交的 url
            dataType: 'json',
            data: {
            	"orderId":$("#orderId").val(),
            	"invoiceAmt":$("#invoiceAmt").val(),
            	"mchntCode":$("#mchntCode").val(),
            	"shopCode":shopCode,
            	"invoiceInfo":$("#invoiceInfo").val(),
			},
			success: function(data){
				if(data.status) {
                	location.href=Helper.getRootPath() + '/enterpriseOrder/batchRecharge/listRecharge.do';
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
			}
		});
	}
}
