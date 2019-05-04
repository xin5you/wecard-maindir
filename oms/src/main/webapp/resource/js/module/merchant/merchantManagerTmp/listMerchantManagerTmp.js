$(document).ready(function() {
	listMerchantManagerTmp.init();
})

var listMerchantManagerTmp = {

	init : function() {
		listMerchantManagerTmp.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
	
		$('.btn-edit').on('click', listMerchantManagerTmp.intoEditmanagerTmp);
		$('.btn-delete').on('click', listMerchantManagerTmp.deletemanagerTmpCommit);
		$('.btn-add').on('click', listMerchantManagerTmp.intoAddmanagerTmp);
		$('.btn-view').on('click', listMerchantManagerTmp.intoViewmanagerTmp);
		$('.btn-reset').on('click', listMerchantManagerTmp.searchReset);
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/merchant/managerTmp/listMerchantManagerTmp.do';
	},
	intoAddmanagerTmp:function(){
		var url = Helper.getRootPath()+"/merchant/managerTmp/intoAddMerchantManagerTmp.do";
		location.href=url;
	},
	intoEditmanagerTmp:function(){
		var mangerId = $(this).attr('mangerId');
		var url = Helper.getRootPath()+"/merchant/managerTmp/intoEditMerchantManagerTmp.do?mangerId="+mangerId;
		location.href=url;
	},
	intoViewmanagerTmp:function(){
		var shopId = $(this).attr('shopId');
		var url = Helper.getRootPath()+"/merchant/managerTmp/intoViewMerchantManagerTmp.do?shopId="+shopId;
		location.href=url;
	},
	deletemanagerTmpCommit:function(){
		var mangerId = $(this).attr('mangerId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/merchant/managerTmp/deleteMerchantManagerTmpCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "mangerId": mangerId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/merchant/managerTmp/listMerchantManagerTmp.do?operStatus=4';
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
