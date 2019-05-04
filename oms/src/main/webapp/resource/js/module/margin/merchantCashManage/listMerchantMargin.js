$(document).ready(function() {
	listMchntMargin.init();
})

var listMchntMargin = {

	init : function() {
		listMchntMargin.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-edit').on('click', listMchntMargin.intoEditMerchantMargin);
		$('#intoAddMerchantMargin').on('click', listMchntMargin.intoAddMerchantMargin);
		$('.btn-delete').on('click', listMchntMargin.deleteMerchantMarginfCommit);
		$('.btn-view').on('click', listMchntMargin.intoViewMchntMargin);
	},
	intoViewMchntMargin:function(){
		var marginListId = $(this).attr('marginListId');
		var url = Helper.getRootPath()+"/margin/mchntCashManage/intoViewMchntMargin.do?marginListId="+marginListId;
		location.href=url;
	},
	intoAddMerchantMargin:function(){
		var chashId=$('#chashId').val();
		var url = Helper.getRootPath()+"/margin/mchntCashManage/intoAddMerchantMargin.do?chashId="+chashId;
		location.href=url;
	},
	intoEditMerchantMargin:function(){
		var marginListId = $(this).attr('marginListId');
		var url = Helper.getRootPath()+"/margin/mchntCashManage/intoEditMerchantMargin.do?marginListId="+marginListId;
		location.href=url;
	},
	deleteMerchantMarginfCommit:function(){
		var marginListId = $(this).attr('marginListId');
		var chashId=$(this).attr('chashId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/margin/mchntCashManage/delMerchantMarginCommit.do?marginListId='+marginListId,
	            type: 'post',
	            dataType : "json",
	            data: {
	                "marginListId": marginListId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/margin/mchntCashManage/listMerchantMargin.do?operStatus=4&chashId='+chashId;
	            	}else{
	            		Helper.alter(result.msg);
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统超时，请稍微再试试");
	            }
	      });
		});
	
	}
}
