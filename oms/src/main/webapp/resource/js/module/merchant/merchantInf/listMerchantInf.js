$(document).ready(function() {
	listMerchantInf.init();
})

var listMerchantInf = {

	init : function() {
		listMerchantInf.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-edit').on('click', listMerchantInf.intoEditMerchantInf);
		$('.btn-delete').on('click', listMerchantInf.deleteMerchantInfCommit);
		$('.btn-add').on('click', listMerchantInf.intoAddMerchantInfUser);
		$('.btn-view').on('click', listMerchantInf.intoViewMerchantInf);
		$('.btn-reset').on('click', listMerchantInf.searchReset);
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/merchant/merchantInf/listMerchantInf.do';
	},
	intoAddMerchantInfUser:function(){
		var url = Helper.getRootPath()+"/merchant/merchantInf/intoAddMerchantInf.do";
		location.href=url;
	},
	intoEditMerchantInf:function(){
		var mchntId = $(this).attr('mchntId');
		var url = Helper.getRootPath()+"/merchant/merchantInf/intoEditMerchantInf.do?mchntId="+mchntId;
		location.href=url;
	},
	intoViewMerchantInf:function(){
		var mchntId = $(this).attr('mchntId');
		var url = Helper.getRootPath()+"/merchant/merchantInf/intoViewMerchantInf.do?mchntId="+mchntId;
		location.href=url;
	},
	deleteMerchantInfCommit:function(){
		var mchntId = $(this).attr('mchntId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/merchant/merchantInf/deleteMerchantInfCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "mchntId": mchntId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/merchant/merchantInf/listMerchantInf.do?operStatus=4';
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
