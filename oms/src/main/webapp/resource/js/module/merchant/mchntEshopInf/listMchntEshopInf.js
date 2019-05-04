$(document).ready(function() {
	listMchntEshopInf.init();
})

var listMchntEshopInf = {

	init : function() {
		listMchntEshopInf.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
	
		$('.btn-edit').on('click', listMchntEshopInf.intoEditMchntEshopInf);
		$('.btn-delete').on('click', listMchntEshopInf.deleteMchntEshopInfCommit);
		$('.btn-add').on('click', listMchntEshopInf.intoAddMchntEshopInf);
		$('.btn-view').on('click', listMchntEshopInf.intoViewMchntEshopInf);
		$('.btn-search').on('click', listMchntEshopInf.searchData);
		$('.btn-reset').on('click', listMchntEshopInf.searchReset);
	},
	searchData:function(){
		document.forms['searchForm'].submit();
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/mchnteshop/eShopInf/listMchntEshopInf.do';
	},
	intoAddMchntEshopInf:function(){
		var url = Helper.getRootPath()+"/mchnteshop/eShopInf/intoAddMchntEshopInf.do";
		location.href=url;
	},
	intoEditMchntEshopInf:function(){
		var eShopId = $(this).attr('eShopId');
		var url = Helper.getRootPath()+"/mchnteshop/eShopInf/intoEditMchntEshopInf.do?eShopId="+eShopId;
		location.href=url;
	},
	intoViewMchntEshopInf:function(){
		var eShopId = $(this).attr('eShopId');
		var url = Helper.getRootPath()+"/mchnteshop/eShopInf/intoViewMchntEshopInf.do?eShopId="+eShopId;
		location.href=url;
	},
	deleteMchntEshopInfCommit:function(){
		var eShopId = $(this).attr('eShopId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/mchnteshop/eShopInf/deleteMchntEshopInfCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "eShopId": eShopId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/mchnteshop/eShopInf/listMchntEshopInf.do?operStatus=4';
	            	}else{
	            		Helper.alter(result.msg);
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
	      });
		});
	}
}
